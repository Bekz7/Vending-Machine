package pl.bekz.vendingmachine.exceptions;

public class ExactChangeOnly extends RuntimeException {

  public ExactChangeOnly(String message) {
    super(message);
  }
}
