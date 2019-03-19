package pl.bekz.vendingmachine;

public class NotEnoughCoins extends RuntimeException {
    public NotEnoughCoins(String message) {
        super(message);
    }
}
