package pl.bekz.vendingmachine.repositories;

import lombok.Getter;
import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCustomerCreditsRepository implements CustomerCreditsRepository {

  @Getter private ConcurrentHashMap<Money, Integer> customerCredits = new ConcurrentHashMap<>();
  private int coinsNumber;

  @Override
  public Integer insertCoin(Money coin) {
    if (hasCoin(coin)) {
      coinsNumber += customerCredits.get(coin);
    }else{
        coinsNumber = 1;
    }
    customerCredits.put(coin, coinsNumber);
    return coinsNumber;
  }

  @Override
  public BigDecimal checkCoinsBalance() {
    Enumeration<Money> coins = customerCredits.keys();
    BigDecimal balance = coins.nextElement().getValue();

    for (int i = 0; i <= customerCredits.size(); i++) {
      //          balance = balance * customerCredits.get(customerCredits.values());
    }
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
