package pl.bekz.vendingmachine.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import pl.bekz.vendingmachine.model.entities.Product;

public interface ProductRepository extends Repository<Product, String>, GenericRepository<Product> {

    Page<Product> findAll(Pageable pageable);
}
