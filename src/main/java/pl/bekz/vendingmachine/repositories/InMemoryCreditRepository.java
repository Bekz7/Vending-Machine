package pl.bekz.vendingmachine.repositories;

import lombok.Getter;
import pl.bekz.vendingmachine.model.entities.Credit;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class InMemoryCreditRepository implements CreditsRepository {

  @Getter private Map<Integer, Credit> map = new HashMap<>();

  @Override
  public Credit saveCredit(Credit credit) {
    requireNonNull(credit);
    map.put(credit.creditsDto().getId(), credit);
    return credit;
  }

  @Override
  public Credit findById(int id) {
    return map.get(id);
  }


  @Override
  public void deleteAll() {
    map.clear();
  }

  @Override
  public long count() {
    return map.size();
  }
}
