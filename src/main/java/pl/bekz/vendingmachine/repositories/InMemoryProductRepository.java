package pl.bekz.vendingmachine.repositories;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.bekz.vendingmachine.model.entities.Product;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;
import static pl.bekz.vendingmachine.ProductFactory.*;

public class InMemoryProductRepository implements ProductRepository {

  @Getter private ConcurrentHashMap<String, Product> map = new ConcurrentHashMap<>();

  public InMemoryProductRepository(){
    map.put("Pepsi", pepsi(3));
    map.put("Coca-cola", cocaCola(2));
    map.put("Redbull", redbull(0));
  }

  @Override
  public Product save(Product product) {
    requireNonNull(product);
    map.put(product.productDto().getName(), product);
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
