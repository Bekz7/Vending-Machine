package pl.bekz.vendingmachine.repositories;

import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMachineCreditsRepository implements MachineCreditsRepository {

  private InMemoryCreditsRepositoryImpl creditsRepository = new InMemoryCreditsRepositoryImpl();

  public ConcurrentHashMap getAllCreditsMap(){
    return creditsRepository.getAllCredits();
  }

  public void persistCoins(BigDecimal credits) {
    creditsRepository.persistCoins(credits);
  }

  public BigDecimal checkBalance() {
    return creditsRepository.checkBalance();
  }

  public void updateCoinBalance(Money coin, int coinAmount) {
    creditsRepository.updateCoinBalance(coin, coinAmount);
  }

  public void clearCoinsBalance() {
    creditsRepository.clearCoinsBalance();
  }
}
