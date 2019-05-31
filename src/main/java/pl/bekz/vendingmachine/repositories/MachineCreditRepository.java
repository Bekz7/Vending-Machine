package pl.bekz.vendingmachine.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bekz.vendingmachine.model.MachineBalance;

import java.math.BigDecimal;

@Repository
public interface MachineCreditRepository extends JpaRepository<MachineBalance, Long> {

    BigDecimal getMachineCredits();

    BigDecimal saveCredits(BigDecimal newCredits);

    //Todo check this in Spring data module
    void findFirstByOrderByPublicationDateDesc();

}
