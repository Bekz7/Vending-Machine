package pl.bekz.vendingmachine.infrastructure.repositories;

import org.springframework.data.repository.Repository;
import pl.bekz.vendingmachine.infrastructure.exceptions.ItemNotFound;
import pl.bekz.vendingmachine.machine.domain.entities.Credit;

import java.util.List;
import java.util.Optional;

public interface CreditsRepository extends Repository<Credit, String> {

    Credit save(Credit credit);

    Credit findById(String name);

    default Credit findOneOrThrow(String name) {
        return Optional.ofNullable(findById(name)).orElseThrow(() -> new ItemNotFound(name));
    }

    void deleteAll();

    List<Credit> findAll();
}