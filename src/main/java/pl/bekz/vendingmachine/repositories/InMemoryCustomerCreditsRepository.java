package pl.bekz.vendingmachine.repositories;

import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCustomerCreditsRepository implements CustomerCreditsRepository {

  private InMemoryCreditsRepositoryImpl creditsRepository = new InMemoryCreditsRepositoryImpl();

  @Override
  public ConcurrentHashMap getAllCredits(){
    return creditsRepository.getAllCredits();
  }

  public void insertCoin(Money coin) {
    int coinsNumber = (int) creditsRepository.getAllCredits().get(coin);

    if (hasCoin(coin)) {
      coinsNumber++;
    } else {
      coinsNumber = 1;
    }
    persistCoins(coin, coinsNumber);
  }


  public BigDecimal checkBalance() {
    return creditsRepository.checkBalance();
  }

  @Override
  public void persistCoins(Money coin, int coinsNumber) {
    creditsRepository.persistCoins(coin, coinsNumber);
  }


  public void clearCoinsBalance() {
    creditsRepository.clearCoinsBalance();
  }

  private boolean hasCoin(Money coin) {
    return creditsRepository.getAllCredits().containsKey(coin);
  }
}
