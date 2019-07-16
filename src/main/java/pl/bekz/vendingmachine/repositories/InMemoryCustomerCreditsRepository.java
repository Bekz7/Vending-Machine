package pl.bekz.vendingmachine.repositories;

import lombok.Getter;
import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCustomerCreditsRepository implements CustomerCreditsRepository {

  @Getter private ConcurrentHashMap<Money, Integer> customerCredits = new ConcurrentHashMap<>();
  private BigDecimal balance;

  @Override
  public Integer insertCoin(Money coin) {
    int coinsNumber = 0;

    if (hasCoin(coin)) {
      coinsNumber++;
    } else {
      coinsNumber = 1;
    }
    return customerCredits.put(coin, coinsNumber);
  }

  @Override
  public BigDecimal checkCoinsBalance() {
    balance = BigDecimal.ZERO;
    customerCredits.forEach(
        (money, v) -> balance = balance.add(money.getValue().multiply(BigDecimal.valueOf(v))));
    return balance;
  }

  @Override
  public void updateCredits(BigDecimal value) {
    balance = value;

  }
//TODO Refactor this method
  public void creditMapper(BigDecimal credits){
    customerCredits.put(Money.DOLLAR, 0);
    customerCredits.put(Money.QUARTER, 0);
    customerCredits.put(Money.DIME, 0);
    customerCredits.put(Money.NICKEL, 0);

    for (ConcurrentHashMap.Entry<Money, Integer> coins : customerCredits.entrySet()){

    }
    BigDecimal dollars = countCoinsAccordingDenomination(credits, Money.DOLLAR.getValue());
    if (isContain(dollars)){
      customerCredits.put(Money.DOLLAR, dollars.intValue());
      credits = credits.subtract(dollars);
    }

    BigDecimal quarters = countCoinsAccordingDenomination(credits, Money.QUARTER.getValue());
    if (isContain(quarters)){
      customerCredits.put(Money.QUARTER, quarters.intValue());
      credits = credits.subtract(quarters.multiply(Money.QUARTER.getValue()));
    }

    BigDecimal dimes = countCoinsAccordingDenomination(credits, Money.DIME.getValue());
    if (isContain(dimes)){
      customerCredits.put(Money.DIME, dimes.intValue());
      credits = credits.subtract(dimes.multiply(Money.DIME.getValue()));
    }

    BigDecimal nickels = countCoinsAccordingDenomination(credits, Money.NICKEL.getValue());
    if (isContain(nickels)){
      customerCredits.put(Money.NICKEL, nickels.intValue());
      credits = credits.subtract(nickels.multiply(Money.NICKEL.getValue()));
    }

    if (credits.compareTo(BigDecimal.ZERO) >0 ){
      System.out.println("Something went wrong in mapping " + credits);
    }


  }

  private BigDecimal countDenomination (BigDecimal credits, Money coin){

    BigDecimal denomination = countCoinsAccordingDenomination(credits, coin.getValue());
    if (isContain(denomination)){
      customerCredits.put(coin, denomination.intValue());
      credits = credits.subtract(denomination);
    }
    return credits;
  }

  private BigDecimal countCoinsAccordingDenomination(BigDecimal credits, BigDecimal coins){
    return credits.divide(coins, 0, RoundingMode.DOWN);
  }

  private boolean isContain (BigDecimal credits){
    return credits.compareTo(BigDecimal.ZERO) > 0;
  }

  @Override
  public void clearCoinsBalance() {
    customerCredits.clear();
  }

  private boolean hasCoin(Money coin) {
    return customerCredits.containsKey(coin);
  }
}
