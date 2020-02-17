package pl.bekz.vendingmachine.repositories;

import org.springframework.data.repository.Repository;
import pl.bekz.vendingmachine.model.Money;
import pl.bekz.vendingmachine.model.entities.Credit;

import java.util.Map;
import java.util.Optional;

public interface CreditsRepository extends Repository<Credit, Money> {

  void save(Credit credit);

  long count();

  void deleteAll();

  Credit findById(String coinName);

  Map<String, Credit>getCredits();

  //TODO something not right to much builder
  default Credit findOrCreate(Money coin){
    return Optional.ofNullable(findById(coin.getCoinName())).orElse(Credit.builder()
            .coinName(coin.getCoinName())
            .coinsValue(coin.getValue())
            .coinsNumber(1)
            .build());
  }
}
