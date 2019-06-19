package pl.bekz.vendingmachine.repositories;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.bekz.vendingmachine.model.Money;
import pl.bekz.vendingmachine.model.Product;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryProductRepository implements ProductRepository {

    @Getter
    private ConcurrentHashMap<String, Product> map = new ConcurrentHashMap<>();

    //TODO repair
    @Override
    public Product addNewProduct(String name, int amount, Money price) {
        Product product = findById(name);
//        product.productDto().;
        map.put(product.productDto().getName(), product);
        return product;
    }

    @Override
    public Product refill(String name) {
        Product product = findById(name);
        int refill = product.productDto().getAmount() + 1;
        product.productDto().setAmount(refill);
        return product;
    }

    @Override
    public Product findById(String name) {
        return map.get(name);
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return new PageImpl<>(new ArrayList<>(map.values()), pageable, map.size());
    }
}
