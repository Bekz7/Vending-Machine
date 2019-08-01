package pl.bekz.vendingmachine.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import pl.bekz.vendingmachine.exceptions.ProductNotFound;
import pl.bekz.vendingmachine.model.entities.Product;

public interface ProductRepository extends Repository<Product, String> {

    Product addNewProduct(Product product);
    Product changeProductAmount(String name, int amount);
    Product findById(String name);
    Page<Product> findAll(Pageable pageable);

    default Product findOneOrThrow(String name){
        Product product = findById(name);
        if (product == null){
            throw new ProductNotFound(name);
        }
        return product;
    }
}
