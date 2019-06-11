package pl.bekz.vendingmachine.repositories;

import org.springframework.stereotype.Component;
import pl.bekz.vendingmachine.model.CustomerBalance;
import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;

@Component
public class CustomerBalanceDAO implements DAO {

  private CustomerBalance customerBalance = new CustomerBalance();

  @Override
  public BigDecimal getBalance() {
    return customerBalance.getBalance();
  }

  @Override
  public void insertCoin(Money money) {
    customerBalance.setBalance(money.getValue());
  }

  @Override
  public void updateCoinsBalance(BigDecimal productCost) {
    customerBalance.setBalance(customerBalance.getBalance().subtract(productCost));
  }
}
