package pl.bekz.vendingmachine.repositories;

import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;

public interface DAO {

    BigDecimal getBalance();

    void addCoin(Money money);

    void updateCoinsBalance(BigDecimal productCost);
}