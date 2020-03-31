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
import java.util.ArrayList;
import java.util.Comparator;
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
    final int amountToChange = credit.creditsDto().getCoinsAmount() + amount;
    final BigDecimal coinValue = credit.creditsDto().getCoinValue();
    credit =
        Credit.builder().coinName(name).coinAmount(amountToChange).coinsValue(coinValue).build();
    return credit.creditsDto();
  }

  public void decreesMachineBalance() {
    decreesCreditsList().forEach(this::add);
  }

  //    private CreditDto findGreatestCredit(BigDecimal balanceToCheck) {
  //        return creditsRepository.getCredits().values().stream()
  //                .map(Credit::creditsDto)
  //                 .filter(dto ->
  // (dto.getCoinValue().compareTo(getMostValueCoinToReturn(balanceToCheck)) == 0))
  //                 .findFirst()
  //                .orElseThrow(() -> new
  // CreditNotFound(getMostValueCoinToReturn(balanceToCheck)));
  //    }

  private List<CreditDto> decreesCreditsList() {
    return findCoinsToReturn(coinsListToReturn(checkCustomerBalance())).stream()
        .map(this::decreesCredit)
        .collect(Collectors.toList());
  }

  private CreditDto decreesCredit(CreditDto dto) {
    requireNonNull(dto);
    return changeAmount(dto.getCoinName(), -1);
  }

//  private List<CreditDto> findCoinsToReturn(List<BigDecimal> coinsToReturn) {
//    return creditsRepository.getCredits().values().stream()
//        .map(Credit::creditsDto)
//        .filter(
//            dto ->
//                coinsToReturn.stream()
//                    .anyMatch(coinValue -> coinValue.compareTo(dto.getCoinValue()) == 0))
//        .collect(Collectors.toList());
//  }

  private List<CreditDto> findCoinsToReturn(List<BigDecimal> coinsToReturn) {
    return creditsRepository.getCredits().values().stream()
            .map(Credit::creditsDto)
            .filter(
                    dto ->
                            coinsToReturn.stream()
                                    .anyMatch(coinValue -> coinValue.compareTo(dto.getCoinValue()) == 0))
            .collect(Collectors.toList());
  }

  private BigDecimal getMostValueCoinToReturn(BigDecimal balanceToCheck) {
    return creditsRepository.getCredits().values().stream()
        .map(credit -> credit.creditsDto().getCoinValue())
        .filter(coin -> coin.compareTo(balanceToCheck) <= 0)
        .max(Comparator.naturalOrder())
        .orElse(BigDecimal.ZERO);
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
    final BigDecimal coinsValue =
        coinsListToReturn(balanceToCheck).stream().reduce(BigDecimal.ZERO, BigDecimal::add);
    return coinsValue.compareTo(balanceToCheck) != 0;
  }

  private List<BigDecimal> coinsListToReturn(BigDecimal balanceToCheck) {
    requireNonNull(balanceToCheck);
    List<BigDecimal> coins = new ArrayList<>();
    while (balanceToCheck.compareTo(BigDecimal.ZERO) != 0) {
      BigDecimal mostValueCoinToReturn = getMostValueCoinToReturn(balanceToCheck);
      balanceToCheck = balanceToCheck.subtract(mostValueCoinToReturn);
      coins.add(mostValueCoinToReturn);
    }
    return coins;
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
        .getCredits()
        .forEach(
            (key, value) ->
                machineBalance[0] =
                    machineBalance[0].add(
                        value
                            .creditsDto()
                            .getCoinValue()
                            .multiply(BigDecimal.valueOf(value.creditsDto().getCoinsAmount()))));

    return machineBalance[0];
  }

  public BigDecimal resetCustomerBalance() {
    transaction.setCustomerBalance(BigDecimal.ZERO);
    return transaction.getCustomerBalance();
  }

  public void WithdrawMachineDeposit() {
    creditsRepository.deleteAll();
  }
}
