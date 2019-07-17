package pl.bekz.vendingmachine.repositories;

import lombok.Getter;
import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCustomerCreditsRepository implements CustomerCreditsRepository {

  @Getter private ConcurrentHashMap<Money, Integer> customerCredits;
  private BigDecimal balance;

  public InMemoryCustomerCreditsRepository() {
    customerCredits = initializeMapWithKeys();
  }

  private ConcurrentHashMap<Money, Integer> initializeMapWithKeys() {
    ConcurrentHashMap<Money, Integer> result = new ConcurrentHashMap<>();
    result.put(Money.DOLLAR, 0);
    result.put(Money.QUARTER, 0);
    result.put(Money.DIME, 0);
    result.put(Money.NICKEL, 0);

    return result;
  }

  @Override
  public Integer insertCoin(Money coin) {
    int coinsNumber = 0;

    if (hasCoin(coin)) {
      coinsNumber++;
    } else {
      coinsNumber = 1;
    }
    return customerCredits.put(coin, coinsNumber);
  }

  @Override
  public BigDecimal checkCoinsBalance() {
    balance = BigDecimal.ZERO;
    customerCredits.forEach(
        (money, v) -> balance = balance.add(money.getValue().multiply(BigDecimal.valueOf(v))));
    return balance;
  }

  @Override
  public void updateCredits(BigDecimal value) {
    balance = value;
  }

  public void creditMapper(BigDecimal credits) {
    customerCredits.entrySet().stream()
        .sorted(ConcurrentHashMap.Entry.comparingByKey())
        .forEach((money) -> countDenomination(money.getKey(), credits));
  }

  private void countDenomination(Money coin, BigDecimal credits) {

    BigDecimal denomination = roundingCoinsNumbers(credits, coin.getValue());
    if (isContain(denomination)) {
      customerCredits.put(coin, denomination.intValue());
    }
  }

  private BigDecimal roundingCoinsNumbers(BigDecimal credits, BigDecimal coins) {
    BigDecimal creditsLeft = credits.subtract(checkCoinsBalance());
    return creditsLeft.divide(coins, 0, RoundingMode.DOWN);
  }

  private boolean isContain(BigDecimal credits) {
    return credits.compareTo(BigDecimal.ZERO) > 0;
  }

  @Override
  public void clearCoinsBalance() {
    customerCredits.clear();
  }

  private boolean hasCoin(Money coin) {
    return customerCredits.containsKey(coin);
  }
}
