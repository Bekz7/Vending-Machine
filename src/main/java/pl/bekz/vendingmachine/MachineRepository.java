package pl.bekz.vendingmachine;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bekz.vendingmachine.Product;

public interface MachineRepository extends JpaRepository<Product, Long> {
}
