package pl.bekz.vendingmachine.repositories;

import org.springframework.data.repository.Repository;
import pl.bekz.vendingmachine.model.Money;
import pl.bekz.vendingmachine.model.entities.Credit;

import java.math.BigDecimal;

public interface CreditsRepository extends Repository<Credit, Integer> {

  Credit saveCredit(Credit credit);

  Credit findById(int id);

  void deleteAll();


  long count();

}
