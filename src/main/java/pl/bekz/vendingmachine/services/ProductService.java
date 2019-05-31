package pl.bekz.vendingmachine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bekz.vendingmachine.VendingMachineFacade;

@Service
public class ProductService {

    private VendingMachineFacade vendingMachineFacade;

    @Autowired
    public ProductService(VendingMachineFacade vendingMachineFacade) {
        this.vendingMachineFacade = vendingMachineFacade;
    }

    void refilProduct(Long productId, Integer amount){
        vendingMachineFacade.refillProduct(productId, amount);
    }
}
