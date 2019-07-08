package pl.bekz.vendingmachine.exceptions;

public class ProductSoldOut extends RuntimeException {

    public ProductSoldOut(String name) {
        super("Product " + name + " sold out");
    }
}
