package pl.bekz.vendingmachine.repositories;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.bekz.vendingmachine.model.Product;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

public class InMemoryProductRepository implements ProductRepository {

  @Getter private ConcurrentHashMap<String, Product> map = new ConcurrentHashMap<>();

  @Override
  public Product addNewProduct(Product product) {
    requireNonNull(product);
    map.put(product.productDto().getName(), product);
    return product;
  }

  @Override
  public Product changeProductAmount(String name, int amount) {
    Product product = findById(name);
    final int refill = product.productDto().getAmount() + amount;
    product = Product.builder().amount(refill).build();
    map.put(name, product);
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
