package pl.bekz.vendingmachine;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class Order {

    Money money;
    private BigDecimal balance;

    public BigDecimal insertCoins(Money money){
        return balance;
    }

    public BigDecimal coinsReturn(){
        return balance = BigDecimal.valueOf(0);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal insertDolar(){
        money = Money.DOLLAR;
        return balance.add(money.getValue());
    }

    public BigDecimal insertQuater(){
        money = Money.QUARTER;
        return balance.add(money.getValue());
    }

    public BigDecimal insertDime(){
        money = Money.DIME;
        return balance.add(money.getValue());
    }

    public BigDecimal insertNikel(){
        money = Money.NIKEL;
        return balance.add(money.getValue());
    }
}
