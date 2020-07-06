package pl.bekz.vendingmachine.infrastructure.repositories;

import org.springframework.data.repository.Repository;
import pl.bekz.vendingmachine.machine.domain.entities.Credit;

import java.util.Map;

public interface CreditsRepository extends Repository<Credit, String>, GenericRepository<Credit> {

  void deleteAll();

  Map<String, Credit> findAll();
}