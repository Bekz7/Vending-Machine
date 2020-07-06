package pl.bekz.vendingmachine.infrastructure.exceptions;

public class ExactChangeOnly extends RuntimeException {

  public ExactChangeOnly() {
    super("Exact change only");
  }
}
