package pl.bekz.vendingmachine.model.services;

import org.springframework.stereotype.Service;
import pl.bekz.vendingmachine.model.Money;
import pl.bekz.vendingmachine.model.dto.CreditDto;
import pl.bekz.vendingmachine.model.dto.ProductDto;
import pl.bekz.vendingmachine.model.facades.CreditFacade;
import pl.bekz.vendingmachine.model.facades.ProductFacade;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;
import static pl.bekz.vendingmachine.model.entities.Credit.builder;

@Service
public class CustomerService {

    private CreditFacade creditFacade;
    private ProductFacade productFacade;

    public CustomerService(CreditFacade creditFacade, ProductFacade productFacade) {
        this.creditFacade = creditFacade;
        this.productFacade = productFacade;
    }

    public void insertCoin(Money coin){
        final CreditDto dto = coinToCreditMapper(coin);
        creditFacade.add(increaseCoinAmount(dto.getCoinName()));
    }

    public void buyProduct(String productName){
        requireNonNull(productName);
        final BigDecimal selectedProductPrice = productPrice(productName);

        productFacade.checkIsProductAvailable(productName);
        creditFacade.checkIfCoinEnough(selectedProductPrice);
        creditFacade.checkIfExactChangeOnly(selectedProductPrice);

        decreaseProductAmount(productName);
        creditFacade.decreesCustomerBalance(selectedProductPrice);
        creditFacade.decreesMachineBalance();


    }

    private BigDecimal productPrice(String productId){
        return productFacade.show(productId).getPrice();
    }

    CreditDto increaseCoinAmount(String coin){
       return creditFacade.changeAmount(coin, 1);
    }

    private CreditDto coinToCreditMapper(Money coin) {
        requireNonNull(coin);
        return builder().coinName(coin.getCoinName()).coinsValue(coin.getValue()).build().creditsDto();
    }

    public BigDecimal customerBalance(){
        return creditFacade.checkCustomerBalance();
    }

    private ProductDto decreaseProductAmount(String productId){
        return productFacade.changeAmount(productId, -1);
    }


}
