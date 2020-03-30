package pl.bekz.vendingmachine.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.bekz.vendingmachine.model.entities.Product;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;
import static pl.bekz.vendingmachine.ProductFactory.*;
import static pl.bekz.vendingmachine.model.Drinks.*;

public class InMemoryProductRepository implements ProductRepository {

  private ConcurrentHashMap<String, Product> map = new ConcurrentHashMap<>();

  public InMemoryProductRepository(){
    map.put(PEPSI.name(), pepsi(3));
    map.put(COCA_COLA.name(), cocaCola(2));
    map.put(REDBULL.name(), redbull(0));
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

    public ConcurrentHashMap<String, Product> getMap() {
        return this.map;
    }
}
