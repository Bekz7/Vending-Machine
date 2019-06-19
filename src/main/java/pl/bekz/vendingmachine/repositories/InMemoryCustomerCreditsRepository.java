package pl.bekz.vendingmachine.repositories;

import lombok.Getter;
import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCustomerCreditsRepository implements CustomerCreditsRepository {

  @Getter public ConcurrentHashMap<Money, Integer> customerCredits = new ConcurrentHashMap<>();
  public BigDecimal balance = BigDecimal.ZERO;

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
    customerCredits.forEach((money, v) -> balance = money.getValue().multiply(BigDecimal.valueOf(v)));
    return balance;
  }

  @Override
  public void returnCoins(Money coins) {
    customerCredits.clear();
  }

  private boolean hasCoin(Money coin) {
    return customerCredits.containsKey(coin);
  }
}
