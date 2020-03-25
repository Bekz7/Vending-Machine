package pl.bekz.vendingmachine;

import pl.bekz.vendingmachine.model.entities.Credit;

import static pl.bekz.vendingmachine.model.Money.*;

public class CreditFactory {

  private CreditFactory() {}

  public static Credit dollar() {
    return new Credit(DOLLAR.toString(), DOLLAR.getValue(), 0);
  }

  public static Credit quarter() {
    return new Credit(QUARTER.toString(), QUARTER.getValue(), 0);
  }

  public static Credit dime() {
    return new Credit(DIME.toString(), DIME.getValue(), 0);
  }

  public static Credit nickel() {
    return new Credit(NICKEL.toString(), NICKEL.getValue(), 0);
  }
}
