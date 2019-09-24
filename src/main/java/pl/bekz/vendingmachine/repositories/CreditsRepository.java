package pl.bekz.vendingmachine.repositories;

import org.springframework.data.repository.Repository;
import pl.bekz.vendingmachine.model.Money;
import pl.bekz.vendingmachine.model.entities.Credit;

public interface CreditsRepository extends Repository<Credit, Money> {

  Credit saveCredit(Credit credit);

  long count();

  void deleteAll();

  Credit findById(String coinName);

}
