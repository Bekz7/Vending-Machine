package pl.bekz.vendingmachine.repositories;

import org.springframework.data.repository.Repository;
import pl.bekz.vendingmachine.model.Money;
import pl.bekz.vendingmachine.model.entities.Credit;

import java.util.Map;

public interface CreditsRepository extends Repository<Credit, Money> {

  void save(Credit credit);

  long count();

  void deleteAll();

  Credit findById(String coinName);

  Map<String, Credit>getCredits();
}
