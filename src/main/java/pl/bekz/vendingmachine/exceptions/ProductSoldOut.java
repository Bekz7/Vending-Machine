package pl.bekz.vendingmachine.exceptions;

public class ProductSoldOut extends RuntimeException {

    public ProductSoldOut(String message) {
        super(message);
    }
}
