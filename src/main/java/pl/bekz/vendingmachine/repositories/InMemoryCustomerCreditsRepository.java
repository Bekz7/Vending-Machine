package pl.bekz.vendingmachine.repositories;

import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;

public class InMemoryCustomerCreditsRepository implements CustomerCreditsRepository {

  private InMemoryCreditsRepositoryImpl creditsRepository = new InMemoryCreditsRepositoryImpl();

  public void insertCoin(Money coin) {
    int coinsNumber = creditsRepository.getAllCredits().get(coin);

    if (hasCoin(coin)) {
      coinsNumber++;
    } else {
      coinsNumber = 1;
    }
    creditsRepository.updateCoinBalance(coin, coinsNumber);
  }

  @Override
  public void updateCoinBalance(Money coin, int coinAmount) {

  }

  public BigDecimal checkCoinsBalance() {
    return creditsRepository.checkCoinsBalance();
  }

  public void persistCoins(BigDecimal credits) {
    creditsRepository.persistCoins(credits);
  }

  public void clearCoinsBalance() {
    creditsRepository.clearCoinsBalance();
  }

  private boolean hasCoin(Money coin) {
    return creditsRepository.getAllCredits().containsKey(coin);
  }
}
