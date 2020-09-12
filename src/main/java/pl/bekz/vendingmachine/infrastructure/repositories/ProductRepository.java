package pl.bekz.vendingmachine.infrastructure.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import pl.bekz.vendingmachine.infrastructure.exceptions.ItemNotFound;
import pl.bekz.vendingmachine.machine.domain.entities.Product;

import java.util.Optional;

public interface ProductRepository extends Repository<Product, String> {

    Product save(Product product);

    Product findById(String name);

    default Product findOneOrThrow(String name) {
        return Optional.ofNullable(findById(name)).orElseThrow(() -> new ItemNotFound(name));
    }

    Page<Product> findAll(Pageable pageable);
}
