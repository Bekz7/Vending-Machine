package pl.bekz.vendingmachine.infrastructure.exceptions;

public class ProductSoldOut extends RuntimeException {

  public ProductSoldOut(String name) {
    super("Product " + name + " sold out");
  }
}
