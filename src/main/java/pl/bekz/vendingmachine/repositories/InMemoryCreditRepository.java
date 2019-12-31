package pl.bekz.vendingmachine.repositories;

import lombok.Getter;
import pl.bekz.vendingmachine.model.entities.Credit;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class InMemoryCreditRepository implements CreditsRepository {

  @Getter private Map<String, Credit> map = new HashMap<>();

  @Override
  public Credit saveCredit(Credit credit) {
    requireNonNull(credit);
    map.put(credit.creditsDto().getCoinName(), credit);
    return credit;
  }

  @Override
  public BigDecimal getMachineBalance() {
    BigDecimal machineBalance = BigDecimal.ZERO;
    if (map.values().iterator().hasNext()) {
      Integer coinsNumber = map.values().iterator().next().creditsDto().getCoinsNumber();
      BigDecimal coinValue = map.values().iterator().next().creditsDto().getCoinValue();
      machineBalance = machineBalance.add(coinValue.multiply(BigDecimal.valueOf(coinsNumber)));
    }
    return machineBalance;
  }

  @Override
  public Credit findById(String coinName) {
    return map.get(coinName);
  }

  @Override
  public void deleteAll() {
    map.clear();
  }
}
