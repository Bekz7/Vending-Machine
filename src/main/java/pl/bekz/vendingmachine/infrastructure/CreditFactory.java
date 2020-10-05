package pl.bekz.vendingmachine.infrastructure;

import pl.bekz.vendingmachine.machine.domain.entities.Credit;

import static pl.bekz.vendingmachine.machine.domain.Money.*;

public class CreditFactory {

  private CreditFactory() {}

  public static Credit dollar(Integer amount) {
    return new Credit(DOLLAR.toString(), DOLLAR.getValue(), amount);
  }

  public static Credit quarter(Integer amount) {
    return new Credit(QUARTER.toString(), QUARTER.getValue(), amount);
  }

  public static Credit dime(Integer amount) {
    return new Credit(DIME.toString(), DIME.getValue(), amount);
  }

  public static Credit nickel(Integer amount) {
    return new Credit(NICKEL.toString(), NICKEL.getValue(), amount);
  }
}
