package pl.bekz.vendingmachine.machine.facades;

import pl.bekz.vendingmachine.infrastructure.exceptions.ExactChangeOnly;
import pl.bekz.vendingmachine.infrastructure.exceptions.NotEnoughCoins;
import pl.bekz.vendingmachine.infrastructure.repositories.CreditsRepository;
import pl.bekz.vendingmachine.machine.domain.CreditCreator;
import pl.bekz.vendingmachine.machine.domain.Money;
import pl.bekz.vendingmachine.machine.domain.entities.Credit;
import pl.bekz.vendingmachine.machine.domain.entities.Transaction;
import pl.bekz.vendingmachine.machine.dto.CreditDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    Credit credit = creditCreator.from(dto);
    creditsRepository.save(credit);
    return credit.creditsDto();
  }

  @Override
  public CreditDto show(String dto) {
    final Credit credit = creditsRepository.findOneOrThrow(dto);
    return credit.creditsDto();
  }

  @Override
  public CreditDto changeAmount(String name, int amount) {
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
    final int coinToDecrees = -dto.getAmount();
    return changeAmount(dto.getName(), coinToDecrees);
  }

  private List<CreditDto> getListCoinsToReturn(BigDecimal balanceToCheck) {
    return creditsRepository.findAll().stream()
            .map(Credit::creditsDto)
            .filter(creditDto -> creditDto.getAmount() > 0)
            .filter(creditDto -> creditDto.getValue().compareTo(balanceToCheck) <= 0)
            .collect(Collectors.toList());
  }

  public void checkIfCoinEnough(BigDecimal productCost) {
    if (!haveEnoughCredit(productCost)) {
      throw new NotEnoughCoins();
    }
  }

  public Predicate<BigDecimal> enoughCredit = productCost -> transaction.getCustomerBalance().compareTo(productCost) >= 0;

  private boolean haveEnoughCredit(BigDecimal productCost) {
    return transaction.getCustomerBalance().compareTo(productCost) >= 0;
  }

  public void checkIfExactChangeOnly(BigDecimal balanceToCheck) {
    if (exactChangeOnly(balanceToCheck)) {
      throw new ExactChangeOnly();
    }
  }

  public Predicate<BigDecimal> exactChange = balanceToCheck -> {
    List<BigDecimal> allCoinsByValue =
            getListCoinsToReturn(balanceToCheck).stream()
                    .map(CreditDto::getValue)
                    .collect(Collectors.toList());

    BigDecimal coinsValue = allCoinsByValue.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    return coinsValue.compareTo(balanceToCheck) != 0;
  };

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
    final BigDecimal currentCustomerBalance = transaction.getCustomerBalance();
    transaction.setCustomerBalance(currentCustomerBalance.add(coin.getValue()));
  }

  public void decreesCustomerBalance(BigDecimal productPrice) {
    final BigDecimal restAfterTransaction = checkCustomerBalance().subtract(productPrice);
    transaction.setCustomerBalance(restAfterTransaction);
  }

  public BigDecimal checkMachineCoinBalance(){
    return creditsRepository.findAll().stream()
        .map(Credit::creditsDto)
        .filter(s -> s.getAmount() > 0)
        .flatMap(
            creditDto ->
                Stream.of(coinsValueByType(creditDto)))
        .reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  private static BigDecimal coinsValueByType(CreditDto creditDto) {
    return creditDto.getValue().multiply(BigDecimal.valueOf(creditDto.getAmount()));
  }

  public BigDecimal resetCustomerBalance() {
    transaction.setCustomerBalance(BigDecimal.ZERO);
    return transaction.getCustomerBalance();
  }

  public void withdrawMachineDeposit() {
    creditsRepository.deleteAll();
  }
}
