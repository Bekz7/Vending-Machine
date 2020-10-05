package pl.bekz.vendingmachine.infrastructure.repositories;

import pl.bekz.vendingmachine.machine.domain.entities.Credit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static pl.bekz.vendingmachine.infrastructure.CreditFactory.*;
import static pl.bekz.vendingmachine.machine.domain.Money.*;

public class InMemoryCreditRepository implements CreditsRepository {

  private Map<String, Credit> map = new ConcurrentHashMap<>();

  public InMemoryCreditRepository() {
    map.put(DOLLAR.toString(), dollar(0));
    map.put(QUARTER.toString(), quarter(0));
    map.put(DIME.toString(), dime(0));
    map.put(NICKEL.toString(), nickel(0));
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
  public List<Credit> findAll() {
    return new ArrayList<>(map.values());
  }

  @Override
  public void deleteAll() {
    map.clear();
  }
}
