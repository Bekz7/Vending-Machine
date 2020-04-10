package pl.bekz.vendingmachine.model.facades;

import pl.bekz.vendingmachine.exceptions.ExactChangeOnly;
import pl.bekz.vendingmachine.exceptions.NotEnoughCoins;
import pl.bekz.vendingmachine.model.CreditCreator;
import pl.bekz.vendingmachine.model.Money;
import pl.bekz.vendingmachine.model.dto.CreditDto;
import pl.bekz.vendingmachine.model.entities.Credit;
import pl.bekz.vendingmachine.model.entities.Transaction;
import pl.bekz.vendingmachine.repositories.CreditsRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class CreditFacade implements VendingMachineFacade<CreditDto> {
  private CreditCreator creditCreator;
  private CreditsRepository creditsRepository;
  private Transaction transaction;

  public CreditFacade(
      CreditCreator creditCreator, CreditsRepository creditsRepository, Transaction transaction) {
    this.creditCreator = creditCreator;
    this.creditsRepository = creditsRepository;
    this.transaction = transaction;
  }

  @Override
  public CreditDto add(CreditDto dto) {
    requireNonNull(dto);
    Credit credit = creditCreator.from(dto);
    creditsRepository.save(credit);
    return credit.creditsDto();
  }

  @Override
  public CreditDto show(String dto) {
    requireNonNull(dto);
    final Credit credit = creditsRepository.findOneOrThrow(dto);
    return credit.creditsDto();
  }

  @Override
  public CreditDto changeAmount(String name, int amount) {
    requireNonNull(name);
    Credit credit = creditsRepository.findOneOrThrow(name);
    final int amountToChange = credit.creditsDto().getAmount() + amount;
    final BigDecimal coinValue = credit.creditsDto().getValue();
    credit =
        Credit.builder().name(name).amount(amountToChange).value(coinValue).build();
    return credit.creditsDto();
  }

  public void decreesMachineBalance() {
    decreesCreditsList().forEach(this::add);
  }

  private List<CreditDto> decreesCreditsList() {
    return getListCoinsToReturn(checkCustomerBalance()).stream()
        .map(this::decreesCredit)
        .collect(Collectors.toList());
  }

  private CreditDto decreesCredit(CreditDto dto) {
    requireNonNull(dto);
    final int coinToDecrees = -dto.getAmount();
    return changeAmount(dto.getName(), coinToDecrees);
  }

  private List<CreditDto> getListCoinsToReturn(BigDecimal balanceToCheck) {
    return creditsRepository.findAll().values().stream()
            .map(Credit::creditsDto)
            .filter(creditDto -> creditDto.getAmount() > 0)
            .filter(creditDto -> creditDto.getValue().compareTo(balanceToCheck) <= 0)
            .collect(Collectors.toList());
  }

  public void checkIfCoinEnough(BigDecimal productCost) {
    requireNonNull(productCost);
    if (!haveEnoughCredit(productCost)) {
      throw new NotEnoughCoins();
    }
  }

  private boolean haveEnoughCredit(BigDecimal productCost) {
    return transaction.getCustomerBalance().compareTo(productCost) >= 0;
  }

  public void checkIfExactChangeOnly(BigDecimal balanceToCheck) {
    requireNonNull(balanceToCheck);
    if (exactChangeOnly(balanceToCheck)) {
      throw new ExactChangeOnly();
    }
  }

  private boolean exactChangeOnly(BigDecimal balanceToCheck) {
    final List<BigDecimal> allCoinsByValue =
        getListCoinsToReturn(balanceToCheck).stream()
            .map(CreditDto::getValue)
            .collect(Collectors.toList());
    BigDecimal coinsValue = allCoinsByValue.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    return coinsValue.compareTo(balanceToCheck) != 0;
  }

  public BigDecimal checkCustomerBalance() {
    return transaction.getCustomerBalance();
  }

  public void increaseCustomerBalance(Money coin) {
    requireNonNull(coin);
    final BigDecimal currentCustomerBalance = transaction.getCustomerBalance();
    transaction.setCustomerBalance(currentCustomerBalance.add(coin.getValue()));
  }

  public void decreesCustomerBalance(BigDecimal productPrice) {
    requireNonNull(productPrice);
    final BigDecimal restAfterTransaction = checkCustomerBalance().subtract(productPrice);
    transaction.setCustomerBalance(restAfterTransaction);
  }

  public BigDecimal checkMachineCoinBalance() {
    BigDecimal[] machineBalance = {BigDecimal.ZERO};
    creditsRepository
            .findAll()
            .forEach(
                    (key, value) ->
                            machineBalance[0] =
                                    machineBalance[0].add(
                                            value
                                                    .creditsDto()
                                                    .getValue()
                                                    .multiply(BigDecimal.valueOf(value.creditsDto().getAmount()))));
    return machineBalance[0];
  }

  public BigDecimal resetCustomerBalance() {
    transaction.setCustomerBalance(BigDecimal.ZERO);
    return transaction.getCustomerBalance();
  }

  public void withdrawMachineDeposit() {
    creditsRepository.deleteAll();
  }
}
