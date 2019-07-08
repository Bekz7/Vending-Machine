package pl.bekz.vendingmachine.exceptions;

public class NotEnoughCoins extends RuntimeException {

    public NotEnoughCoins() {
        super("You don't have enough coins");
    }
}
