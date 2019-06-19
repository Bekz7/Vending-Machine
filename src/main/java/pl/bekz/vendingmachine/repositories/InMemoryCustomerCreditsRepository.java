package pl.bekz.vendingmachine.repositories;

import lombok.Getter;
import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCustomerCreditsRepository implements CustomerCreditsRepository {

  @Getter
  private ConcurrentHashMap<Money, Integer> customerCredits = new ConcurrentHashMap<>();
  private BigDecimal balance;

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
    customerCredits.forEach((money, v) -> balance = balance.add(money.getValue().multiply(BigDecimal.valueOf(v))));
    return balance;
  }

  @Override
  public void returnCoins() {
    customerCredits.clear();
  }

  private boolean hasCoin(Money coin) {
    return customerCredits.containsKey(coin);
  }
}
