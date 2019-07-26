package pl.bekz.vendingmachine.repositories;

import pl.bekz.vendingmachine.model.Money;

import java.util.concurrent.ConcurrentHashMap;

public interface CustomerCreditsRepository extends CreditsRepository {

  ConcurrentHashMap getAllCreditsMap();
  void insertCoin(Money coin);
}
