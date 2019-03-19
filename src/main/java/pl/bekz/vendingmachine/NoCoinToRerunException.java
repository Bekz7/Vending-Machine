package pl.bekz.vendingmachine;

public class NoCoinToRerunException extends RuntimeException {

  public NoCoinToRerunException(String message) {
    super(message);
  }
}
