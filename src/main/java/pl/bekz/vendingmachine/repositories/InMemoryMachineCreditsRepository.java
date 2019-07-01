package pl.bekz.vendingmachine.repositories;

import lombok.Getter;
import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMachineCreditsRepository implements MachineCreditsRepository {

  @Getter private ConcurrentHashMap<Money, Integer> machineCredits = new ConcurrentHashMap<>();
  private BigDecimal balance;

  @Override
  public Integer persistCoins(Money coin, int coinsNumber) {
    return machineCredits.put(coin, coinsNumber);
  }

  @Override
  public Integer updateCoinsBalance(Money coin, int coinsNumber) {
      machineCredits.put(coin, coinsNumber);
    return null;
  }

  @Override
  public BigDecimal checkCoinsBalance() {
    balance = BigDecimal.ZERO;
    machineCredits.forEach(
        ((money, values) ->
            balance = balance.add(money.getValue().multiply(BigDecimal.valueOf(values)))));
    return balance;
  }

  @Override
  public void clearCoinsBalance() {
      machineCredits.clear();
  }
}
