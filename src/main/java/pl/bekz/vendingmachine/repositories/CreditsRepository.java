package pl.bekz.vendingmachine.repositories;

import org.springframework.data.repository.Repository;
import pl.bekz.vendingmachine.model.Money;
import pl.bekz.vendingmachine.model.entities.Credit;

import java.util.Map;

public interface CreditsRepository extends Repository<Credit, String>, GenericRepository<Credit> {

    void deleteAll();

    Map<String, Credit> getCredits();

    default Credit findOrCreate(Money coin){
        return Optional.ofNullable(findById(coin.getCoinName())).orElse(Credit.builder()
                .coinName(coin.getCoinName())
                .coinsValue(coin.getValue())
                .coinsNumber(1)
                .build());
    }
}
