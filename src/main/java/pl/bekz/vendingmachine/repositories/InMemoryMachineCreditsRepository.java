package pl.bekz.vendingmachine.repositories;

import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;

public class InMemoryMachineCreditsRepository implements MachineCreditsRepository {

  private InMemoryCreditsRepositoryImpl creditsRepository = new InMemoryCreditsRepositoryImpl();

  public void persistCoins(BigDecimal credits) {
    creditsRepository.persistCoins(credits);
  }

  public BigDecimal checkCoinsBalance() {
    return creditsRepository.checkCoinsBalance();
  }

  public void updateCoinBalance(Money coin, int coinAmount) {
    creditsRepository.updateCoinBalance(coin, coinAmount);
  }

  public void clearCoinsBalance() {
    creditsRepository.clearCoinsBalance();
  }
}
