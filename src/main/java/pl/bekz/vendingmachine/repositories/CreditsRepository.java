package pl.bekz.vendingmachine.repositories;

import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

public interface CreditsRepository {

    void updateCoinBalance(ConcurrentHashMap coinMap);
    BigDecimal checkBalance();
    void persistCoins(BigDecimal credits);
    void clearCoinsBalance();
}
