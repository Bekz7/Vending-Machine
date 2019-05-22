package pl.bekz.vendingmachine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bekz.vendingmachine.model.Money;
import pl.bekz.vendingmachine.repositories.CostumerCreditRepository;

import java.math.BigDecimal;

@Service
public class CostumerBalanceService {

    private CostumerCreditRepository repository;

    @Autowired
    public CostumerBalanceService(CostumerCreditRepository repository) {
        this.repository = repository;
    }

    BigDecimal getCostumerBalance(){
        return repository.getCostumerCredits();
    }

    void addCredits(Money money){
        repository.addCredit(money);
    }

    BigDecimal retunCoins(BigDecimal coinToReturn){
        return repository.returnCredits(coinToReturn);
    }
}
