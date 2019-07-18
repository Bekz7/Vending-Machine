package pl.bekz.vendingmachine.repositories;

import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;

public interface CreditsRepository {

    void updateCoinBalance(Money coin, int coinAmount);

    BigDecimal checkCoinsBalance();
    void persistCoins(BigDecimal credits);
    void clearCoinsBalance();

}
