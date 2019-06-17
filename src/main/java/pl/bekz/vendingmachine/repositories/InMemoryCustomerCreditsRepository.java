package pl.bekz.vendingmachine.repositories;

import pl.bekz.vendingmachine.model.Credits;
import pl.bekz.vendingmachine.model.Money;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCustomerCreditsRepository implements CustomerCreditsRepository {

    private List<Credits> customerCredits = new ArrayList<>();

    @Override
    public Credits insertCoin(Money coin) {
        return null;
    }

    @Override
    public Credits checkCoinsBalance(Money coins) {
        return null;
    }

    @Override
    public Credits returnCoins(Money o) {
        return null;
    }
}
