package pl.bekz.vendingmachine.infrastructure.repositories;

import pl.bekz.vendingmachine.machine.domain.entities.Credit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;
import static pl.bekz.vendingmachine.infrastructure.CreditFactory.*;
import static pl.bekz.vendingmachine.machine.domain.Money.*;

public class InMemoryCreditRepository implements CreditsRepository {

  private Map<String, Credit> map = new ConcurrentHashMap<>();

  public InMemoryCreditRepository() {
    map.put(DOLLAR.toString(), dollar());
    map.put(QUARTER.toString(), quarter());
    map.put(DIME.toString(), dime());
    map.put(NICKEL.toString(), nickel());
  }

  @Override
  public Credit save(Credit credit) {
    requireNonNull(credit);
    map.put(credit.creditsDto().getName(), credit);
    return credit;
  }

  @Override
  public Credit findById(String coinName) {
    return map.get(coinName);
  }

  @Override
  public Map<String, Credit> findAll() {
    return this.map;
  }

  @Override
  public void deleteAll() {
    map.clear();
  }
}
