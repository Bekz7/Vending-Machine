package pl.bekz.vendingmachine.repositories;

import pl.bekz.vendingmachine.model.Credits;
import pl.bekz.vendingmachine.model.Money;

public interface CustomerCreditsRepository {

    Credits insertCoin(Money coin);

    Credits checkCoinsBalance(Money coins);

    Credits returnCoins(Money money);
}
