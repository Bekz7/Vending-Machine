package pl.bekz.vendingmachine.repositories;

import org.springframework.stereotype.Component;
import pl.bekz.vendingmachine.model.CostumerBalance;
import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;

@Component
public class CostumerBalanceDAO implements DAO {

  private CostumerBalance costumerBalance = new CostumerBalance();

  @Override
  public BigDecimal getBalance() {
    return costumerBalance.getBalance();
  }

  @Override
  public void addCoin(Money money) {
    costumerBalance.setBalance(money.getValue());
  }

  @Override
  public void updateCoinsBalance(BigDecimal productCost) {
    costumerBalance.setBalance(costumerBalance.getBalance().subtract(productCost));
  }
}
