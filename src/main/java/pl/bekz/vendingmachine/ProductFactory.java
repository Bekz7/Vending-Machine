package pl.bekz.vendingmachine;

import pl.bekz.vendingmachine.model.entities.Product;

import java.math.BigDecimal;

import static pl.bekz.vendingmachine.model.Products.*;


public class ProductFactory {

  private ProductFactory() {}

  public static Product pepsi(Integer amount) {
    return new Product(PEPSI.toString(), amount, BigDecimal.valueOf(1.1));
  }

  public static Product cocaCola(int amount) {
    return new Product(COCA_COLA.toString(), amount, BigDecimal.valueOf(1.0));
  }

  public static Product redbull(int amount) {
    return new Product(REDBULL.toString(), amount, BigDecimal.valueOf(1.6));
  }
}
