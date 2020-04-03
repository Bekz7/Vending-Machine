package pl.bekz.vendingmachine.exceptions;

import pl.bekz.vendingmachine.model.Money;

import java.util.Arrays;
import java.util.List;

public class CreditNotFound extends RuntimeException {

  public CreditNotFound(String creditName) {
    super("Credit with value " + creditName + " not found. ", null, false, false);

    showAcceptanceCoins();
  }

  private void showAcceptanceCoins() {
    System.out.println("Select one of: ");
    final List<Money> coins = Arrays.asList(Money.values());
    coins.forEach(System.out::println);
  }
}
