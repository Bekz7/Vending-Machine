package pl.bekz.vendingmachine.model.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.bekz.vendingmachine.exceptions.CreditNotFound;
import pl.bekz.vendingmachine.model.Money;
import pl.bekz.vendingmachine.model.dto.CreditDto;
import pl.bekz.vendingmachine.model.dto.ProductDto;
import pl.bekz.vendingmachine.model.facades.CreditFacade;
import pl.bekz.vendingmachine.model.facades.ProductFacade;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

@Service
public class CustomerService {

  private CreditFacade creditFacade;
  private ProductFacade productFacade;

  public CustomerService(CreditFacade creditFacade, ProductFacade productFacade) {
    this.creditFacade = creditFacade;
    this.productFacade = productFacade;
  }

  public void insertCoin(String coinName) {
    try {
      Money coin = Money.valueOf(coinName.toUpperCase());
      creditFacade.add(increaseCoinAmount(coin.name()));
      creditFacade.increaseCustomerBalance(coin);
    } catch (IllegalArgumentException e) {
      throw new CreditNotFound(coinName);
    }
  }

  public Page<ProductDto> showAllAvailableProduct() {
    return productFacade.findAllProducts(PageRequest.of(0, 10));
  }

  public String buyProduct(String productName) {
    requireNonNull(productName);
    final BigDecimal selectedProductPrice = productPrice(productName);

    productFacade.checkIsProductAvailable(productName);
    creditFacade.checkIfCoinEnough(selectedProductPrice);
    creditFacade.checkIfExactChangeOnly(customerBalance().subtract(selectedProductPrice));

    decreaseProductAmount(productName);
    creditFacade.decreesCustomerBalance(selectedProductPrice);
    creditFacade.decreesMachineBalance();
    reimburseMoneyToTheCustomer();
    return productName;
  }

  public BigDecimal reimburseMoneyToTheCustomer() {
    return creditFacade.resetCustomerBalance();
  }

  private BigDecimal productPrice(String productId) {
    return productFacade.show(productId).getPrice();
  }

  private CreditDto increaseCoinAmount(String coin) {
    return creditFacade.changeAmount(coin, 1);
  }

  public BigDecimal customerBalance() {
    return creditFacade.checkCustomerBalance();
  }

  private void decreaseProductAmount(String productId) {
    productFacade.changeAmount(productId, -1);
  }
}
