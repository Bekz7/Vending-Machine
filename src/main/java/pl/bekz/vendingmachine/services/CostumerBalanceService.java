package pl.bekz.vendingmachine.services;

import org.springframework.stereotype.Service;
import pl.bekz.vendingmachine.VendingMachineFacade;
import pl.bekz.vendingmachine.model.Money;

@Service
public class CostumerBalanceService {

    private VendingMachineFacade vendingMachineFacade;

    public CostumerBalanceService(VendingMachineFacade vendingMachineFacade) {
        this.vendingMachineFacade = vendingMachineFacade;
    }

    void getCostumerBalance(){
        vendingMachineFacade.getCostumerBalance();
    }

    void addCredits(Money money){
       vendingMachineFacade.insertCoin(money);
    }

    void retunCoins(){
        vendingMachineFacade.returnCoin();
    }

    void buyProduct(Long productId, Money money ){
        vendingMachineFacade.buyProduct(productId, money);
    }
}
