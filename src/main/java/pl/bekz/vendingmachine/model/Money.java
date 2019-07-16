package pl.bekz.vendingmachine.model;

import java.math.BigDecimal;
import java.util.EnumMap;

public enum Money {
  DOLLAR(BigDecimal.ONE),
  QUARTER(BigDecimal.valueOf(0.25)),
  DIME(BigDecimal.valueOf(0.1)),
  NICKEL(BigDecimal.valueOf(0.05));

  Money(BigDecimal value) {
    this.value = value;
  }

  private final BigDecimal value;
  private final EnumMap<Money, Integer> emptyCreditsMap = initializeCreditsMap();

  public BigDecimal getValue() {
    return value;
  }

  public EnumMap<Money, Integer> getEmptyCreditsMap() {
    return emptyCreditsMap;
  }

  private EnumMap<Money, Integer> initializeCreditsMap() {
    EnumMap<Money, Integer> moneyIntegerEnumMap = new EnumMap<>(Money.class);
    moneyIntegerEnumMap.put(Money.DOLLAR, 0);
    moneyIntegerEnumMap.put(Money.QUARTER, 0);
    moneyIntegerEnumMap.put(Money.DIME, 0);
    moneyIntegerEnumMap.put(Money.NICKEL, 0);
    return moneyIntegerEnumMap;
  }
}
