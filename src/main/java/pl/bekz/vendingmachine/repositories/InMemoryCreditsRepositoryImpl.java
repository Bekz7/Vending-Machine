package pl.bekz.vendingmachine.repositories;

import lombok.Getter;
import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCreditsRepositoryImpl implements CreditsRepository {

  private ConcurrentHashMap<Money, Integer> allCredits;
  @Getter private BigDecimal balance;

  InMemoryCreditsRepositoryImpl() {
    this.allCredits = initializeMapWithKeys();
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
  public ConcurrentHashMap getAllCredits() {
    return allCredits;
  }

  @Override
  public BigDecimal checkBalance() {
    balance = BigDecimal.ZERO;
    allCredits.forEach(
        (money, v) -> balance = balance.add(money.getValue().multiply(BigDecimal.valueOf(v))));
    return balance;
  }

  @Override
  public void persistCoins(Money coin, int coinsNumber) {
    allCredits.put(coin, coinsNumber);
  }

  @Override
  public void clearCoinsBalance() {
    allCredits.clear();
  }
}
