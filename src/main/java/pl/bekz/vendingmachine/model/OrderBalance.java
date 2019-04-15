package pl.bekz.vendingmachine.model;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderBalance {

    private BigDecimal balance;

    public BigDecimal insertCoins(BigDecimal coin){
        return balance.add(coin);
    }

    public void coinsReturn(){
        balance = BigDecimal.valueOf(0);
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
