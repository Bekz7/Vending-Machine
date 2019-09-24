package pl.bekz.vendingmachine.model;

import java.math.BigDecimal;

public enum Money {
  DOLLAR(BigDecimal.ONE, "Dollar"),
  QUARTER(BigDecimal.valueOf(0.25), "Quarter"),
  DIME(BigDecimal.valueOf(0.1), "Dime"),
  NICKEL(BigDecimal.valueOf(0.05), "Nickel");

  Money(BigDecimal value, String coinName) {
    this.value = value;
    this.coinName = coinName;
  }

  private final BigDecimal value;
  private String coinName;

  public String getCoinName() {
    return coinName;
  }

  public BigDecimal getValue() {
    return value;
  }
}
