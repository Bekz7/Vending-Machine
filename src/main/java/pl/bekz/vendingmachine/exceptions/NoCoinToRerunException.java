package pl.bekz.vendingmachine.exceptions;

public class NoCoinToRerunException extends RuntimeException {

  public NoCoinToRerunException(String message) {
    super(message);
  }
}
