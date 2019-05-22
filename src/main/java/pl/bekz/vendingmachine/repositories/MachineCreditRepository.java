package pl.bekz.vendingmachine.repositories;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface MachineCreditRepository{

    BigDecimal getMachineCredits();

    BigDecimal saveCredits(BigDecimal newCredits);

}
