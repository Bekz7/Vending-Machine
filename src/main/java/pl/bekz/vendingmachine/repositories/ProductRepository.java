package pl.bekz.vendingmachine.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bekz.vendingmachine.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {}
