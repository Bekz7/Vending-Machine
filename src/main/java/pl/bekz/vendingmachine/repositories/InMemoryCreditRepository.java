package pl.bekz.vendingmachine.repositories;

import pl.bekz.vendingmachine.model.entities.Credit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

public class InMemoryCreditRepository implements CreditsRepository {

  private Map<String, Credit> map = new ConcurrentHashMap<>();

  @Override
  public void save(Credit credit) {
    requireNonNull(credit);
    map.put(credit.creditsDto().getCoinName(), credit);
  }

  @Override
  public long count() {
    return getCredits().size();
  }

  @Override
  public Credit findById(String coinName) {
    return map.get(coinName);
  }

  @Override
  public Map<String, Credit> getCredits() {
    return this.map;
  }

  @Override
  public void deleteAll() {
    map.clear();
  }
}
