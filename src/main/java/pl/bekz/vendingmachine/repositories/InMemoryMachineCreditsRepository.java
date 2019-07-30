package pl.bekz.vendingmachine.repositories;

import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMachineCreditsRepository implements MachineCreditsRepository {

  private InMemoryCreditsRepositoryImpl creditsRepository = new InMemoryCreditsRepositoryImpl();

  @Override
  public ConcurrentHashMap getAllCredits() {
    return creditsRepository.getAllCredits();
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
}
