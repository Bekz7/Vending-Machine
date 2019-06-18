package pl.bekz.vendingmachine.repositories;

import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;

public interface CustomerCreditsRepository {

    Integer insertCoin(Money coin);

    BigDecimal checkCoinsBalance();

    void returnCoins(Money money);
}
