package pl.bekz.vendingmachine.repositories;

import lombok.Getter;
import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static org.springframework.data.util.Pair.toMap;

public class InMemoryCreditsRepositoryImpl implements CreditsRepository {

  @Getter private ConcurrentHashMap<Money, Integer> allCredits;
  @Getter private BigDecimal balance;

  InMemoryCreditsRepositoryImpl() {
    allCredits = initializeMapWithKeys();
  }

  private ConcurrentHashMap<Money, Integer> initializeMapWithKeys() {
    ConcurrentHashMap<Money, Integer> result = new ConcurrentHashMap<>();
    result.put(Money.DOLLAR, 0);
    result.put(Money.QUARTER, 0);
    result.put(Money.DIME, 0);
    result.put(Money.NICKEL, 0);

    return result;
  }

//TODO how to put values from one map to other
  @Override
  public void updateCoinBalance(ConcurrentHashMap coinMap) {

  }

  @Override
  public BigDecimal checkBalance() {
    balance = BigDecimal.ZERO;
    allCredits.forEach(
        (money, v) -> balance = balance.add(money.getValue().multiply(BigDecimal.valueOf(v))));
    return balance;
  }

  @Override
  public void persistCoins(BigDecimal credits) {
    allCredits.entrySet().stream()
        .sorted(ConcurrentHashMap.Entry.comparingByKey())
        .forEach((money) -> countDenomination(money.getKey(), credits));
  }

  private void countDenomination(Money coin, BigDecimal credits) {

    BigDecimal denomination = roundingCoinsNumbers(credits, coin.getValue());
    if (isContain(denomination)) {
      allCredits.put(coin, denomination.intValue());
    }
  }

  private BigDecimal roundingCoinsNumbers(BigDecimal credits, BigDecimal coins) {
    BigDecimal creditsLeft = credits.subtract(checkBalance());
    return creditsLeft.divide(coins, 0, RoundingMode.DOWN);
  }

  private boolean isContain(BigDecimal credits) {
    return credits.compareTo(BigDecimal.ZERO) > 0;
  }

  @Override
  public void clearCoinsBalance() {
    allCredits.clear();
  }
}
