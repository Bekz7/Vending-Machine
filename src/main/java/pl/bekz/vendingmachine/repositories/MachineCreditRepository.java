package pl.bekz.vendingmachine.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bekz.vendingmachine.model.MachineBalance;

@Repository
public interface MachineCreditRepository extends JpaRepository<MachineBalance, Long> {

    MachineBalance findTopByOrderByIdDesc();

}
