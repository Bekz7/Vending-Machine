package pl.bekz.vendingmachine.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import pl.bekz.vendingmachine.model.Money;
import pl.bekz.vendingmachine.model.entities.Credit;

import java.math.BigDecimal;

public interface CreditsRepository extends Repository<Credit, Money> {

    Credit addNewCredits(Credit credit);

    void returnCredits(BigDecimal cashToReturn);

    Credit findById(Money coin);

    Page<Credit> findAll(Pageable pageable);

    void clearCoinsBalance();
}
