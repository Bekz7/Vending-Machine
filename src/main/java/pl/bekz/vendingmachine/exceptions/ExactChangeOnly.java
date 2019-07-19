package pl.bekz.vendingmachine.exceptions;

public class ExactChangeOnly extends RuntimeException {

  public ExactChangeOnly() {
    super("Exact change only");
  }

  public ExactChangeOnly(String message) {
    super(message);
  }
}
