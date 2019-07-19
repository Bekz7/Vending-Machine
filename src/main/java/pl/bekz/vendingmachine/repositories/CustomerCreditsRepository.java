package pl.bekz.vendingmachine.repositories;

import pl.bekz.vendingmachine.model.Money;

public interface CustomerCreditsRepository extends CreditsRepository {

  void insertCoin(Money coin);
}
