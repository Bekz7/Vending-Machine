package pl.bekz.vendingmachine.repositories;

import org.springframework.stereotype.Repository;
import pl.bekz.vendingmachine.model.CostumerBalance;
import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;

@Repository
public interface CostumerCreditRepository {

  BigDecimal getCostumerCredits();

  CostumerBalance addCredit(Money money);

  BigDecimal returnCredits(BigDecimal coinsToReturn);
}
