package pl.bekz.vendingmachine.repositories;

import org.springframework.data.repository.Repository;
import pl.bekz.vendingmachine.exceptions.CoinNotFound;
import pl.bekz.vendingmachine.model.Money;
import pl.bekz.vendingmachine.model.entities.Credit;

import java.util.Map;
import java.util.Optional;

public interface CreditsRepository extends Repository<Credit, Money> {

  Credit save(Credit credit);

  void deleteAll();

  Credit findById(String coinName);

  Map<String, Credit>getCredits();
//todo can't be for adding and decreasing amount
  default Credit findOrThrow(String coinName){
   return Optional.ofNullable(findById(coinName)).orElseThrow(() -> new CoinNotFound(coinName));
  }
}
