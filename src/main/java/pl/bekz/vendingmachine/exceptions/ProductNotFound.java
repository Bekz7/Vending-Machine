package pl.bekz.vendingmachine.exceptions;

public class ProductNotFound extends RuntimeException {

    public ProductNotFound(String name) {
        super("No product of name " + name + " found", null, false , false);
    }
}
