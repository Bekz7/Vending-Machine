package pl.bekz.vendingmachine.repositories;

import org.springframework.stereotype.Repository;
import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

public interface CreditsRepository extends Repository {

  ConcurrentHashMap getAllCredits();
  BigDecimal checkBalance();

  void persistCoins(Money coin,  int coinsNumber);

  void clearCoinsBalance();
}
