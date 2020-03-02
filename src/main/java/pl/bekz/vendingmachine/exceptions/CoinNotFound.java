package pl.bekz.vendingmachine.exceptions;

public class CoinNotFound extends RuntimeException {

    public CoinNotFound(String name) {
        super("No coin of name " + name + " found", null, false , false);
    }
}
