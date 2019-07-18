package pl.bekz.vendingmachine.repositories;

import lombok.Getter;
import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMachineCreditsRepository implements CreditsRepository {

  @Getter private ConcurrentHashMap<Money, Integer> machineCredits = new ConcurrentHashMap<>();
  private BigDecimal balance;

  @Override
  public Integer updateCoinBalance(Money coin, int coinsNumber) {
    return machineCredits.replace(coin, coinsNumber);
  }

  @Override
  public void persistCoins(BigDecimal credits) {
    machineCredits.entrySet().stream()
        .sorted(ConcurrentHashMap.Entry.comparingByKey())
        .forEach((money) -> putDenominationCoins(money.getKey(), credits));
  }

  private void putDenominationCoins(Money coin, BigDecimal credits) {

    BigDecimal denomination = roundingCoinsNumbers(credits, coin.getValue());
    if (isContain(denomination)) {
      persistCoinsNumbers(coin, denomination.intValue());
    }
  }

  private BigDecimal roundingCoinsNumbers(BigDecimal credits, BigDecimal coins) {
    BigDecimal creditsLeft = credits.subtract(checkCoinsBalance());
    return creditsLeft.divide(coins, 0, RoundingMode.DOWN);
  }

  private boolean isContain(BigDecimal credits) {
    return credits.compareTo(BigDecimal.ZERO) > 0;
  }

  private void persistCoinsNumbers(Money coin, int coinsNumber) {
    machineCredits.put(coin, coinsNumber);
  }

  @Override
  public BigDecimal checkCoinsBalance() {
    balance = BigDecimal.ZERO;
    machineCredits.forEach(
        ((money, values) ->
            balance = balance.add(money.getValue().multiply(BigDecimal.valueOf(values)))));
    return balance;
  }

  @Override
  public void clearCoinsBalance() {
    machineCredits.clear();
  }
}
