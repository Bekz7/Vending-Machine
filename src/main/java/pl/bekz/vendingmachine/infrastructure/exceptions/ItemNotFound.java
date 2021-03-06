package pl.bekz.vendingmachine.infrastructure.exceptions;

public class ItemNotFound extends RuntimeException {

  public ItemNotFound(String name) {
    super("No item of name " + name + " found", null, false, false);
  }
}
