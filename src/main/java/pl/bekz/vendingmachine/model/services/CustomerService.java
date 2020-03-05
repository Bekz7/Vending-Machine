package pl.bekz.vendingmachine.model.services;

import org.springframework.stereotype.Service;
import pl.bekz.vendingmachine.model.facades.CreditFacade;
import pl.bekz.vendingmachine.model.facades.ProductFacade;

import static java.util.Objects.requireNonNull;

@Service
public class CustomerService {

    private CreditFacade creditFacade;
    private ProductFacade productFacade;

    public CustomerService(CreditFacade creditFacade, ProductFacade productFacade) {
        this.creditFacade = creditFacade;
        this.productFacade = productFacade;
    }

    public void buyProduct(String productName){
        requireNonNull(productName);

        productFacade.checkIsProductAvailable(productName);


    }
}
