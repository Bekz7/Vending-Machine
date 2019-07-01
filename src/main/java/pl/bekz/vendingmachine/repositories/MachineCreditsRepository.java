package pl.bekz.vendingmachine.repositories;

import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;

public interface MachineCreditsRepository {

    Integer persistCoins(Money coin, int coinsNumber);
    Integer updateCoinsBalance(Money coin, int coinsNumber);
    BigDecimal checkCoinsBalance();
    void clearCoinsBalance();

}
