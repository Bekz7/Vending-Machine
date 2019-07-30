package pl.bekz.vendingmachine.repositories;

import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

public interface CreditsRepository {

  ConcurrentHashMap getAllCredits();
  BigDecimal checkBalance();

  void persistCoins(Money coin,  int coinsNumber);

  void clearCoinsBalance();
}
