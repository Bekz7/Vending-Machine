package pl.bekz.vendingmachine.services;

import org.springframework.stereotype.Service;
import pl.bekz.vendingmachine.VendingMachineFacade;
import pl.bekz.vendingmachine.model.Money;

import java.math.BigDecimal;

@Service
public class CustomerBalanceService {

    private VendingMachineFacade vendingMachineFacade;

    public CustomerBalanceService(VendingMachineFacade vendingMachineFacade) {
        this.vendingMachineFacade = vendingMachineFacade;
    }

    public BigDecimal getCostumerBalance(){
        return vendingMachineFacade.getCostumerBalance();
    }

    public void insertCoin(Money money){
       vendingMachineFacade.insertCoin(money);
    }

    public void returnCoins(){
        vendingMachineFacade.returnCoin();
    }

    public void buyProduct(Long productId){
        vendingMachineFacade.buyProduct(productId);
    }
}
