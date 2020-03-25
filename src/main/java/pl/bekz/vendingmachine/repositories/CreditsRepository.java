package pl.bekz.vendingmachine.repositories;

import org.springframework.data.repository.Repository;
import pl.bekz.vendingmachine.model.entities.Credit;

import java.util.Map;

public interface CreditsRepository extends Repository<Credit, String>, GenericRepository<Credit> {

  void deleteAll();

  Map<String, Credit> getCredits();
}
