package pl.bekz.vendingmachine.infrastructure.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.bekz.vendingmachine.infrastructure.exceptions.CreditNotFound;
import pl.bekz.vendingmachine.infrastructure.exceptions.ExactChangeOnly;
import pl.bekz.vendingmachine.infrastructure.exceptions.NotEnoughCoins;
import pl.bekz.vendingmachine.infrastructure.exceptions.ProductSoldOut;
import pl.bekz.vendingmachine.machine.domain.Money;
import pl.bekz.vendingmachine.machine.dto.CreditDto;
import pl.bekz.vendingmachine.machine.dto.ProductDto;
import pl.bekz.vendingmachine.machine.facades.CreditFacade;
import pl.bekz.vendingmachine.machine.facades.ProductFacade;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

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

  public Boolean buyProduct(String productName) {
    productName = productName.toUpperCase();
    final BigDecimal selectedProductPrice = productPrice(productName);

    conditionsToSold(productName).entrySet()
            .stream()
            .filter(conditionToCheck())
            .map(Map.Entry::getKey)
            .findFirst()
            .ifPresent(ex -> System.out.println(ex.getMessage()));

    decreaseProductAmount(productName);
    creditFacade.decreesCustomerBalance(selectedProductPrice);
    creditFacade.decreesMachineBalance();
    reimburseMoneyToTheCustomer();
    return true;
  }

  private Predicate<Map.Entry<Exception, Boolean>> conditionToCheck() {
    return entry -> entry.getValue().equals(true);
  }

  private Map<Exception, Boolean> conditionsToSold(String productName) {
    Map<Exception, Boolean> conditions = new HashMap<>();
    final BigDecimal selectedProductPrice = productPrice(productName);

    conditions.put(new ProductSoldOut(productName), productFacade.productAvailable.negate().test(productName));
    conditions.put(new NotEnoughCoins(), creditFacade.enoughCredit.negate().test(selectedProductPrice));
    conditions.put(new ExactChangeOnly(), creditFacade.exactChange.test(customerBalance().subtract(selectedProductPrice)));

    return conditions;
  }

  public BigDecimal reimburseMoneyToTheCustomer() {
    return creditFacade.resetCustomerBalance();
  }

  private BigDecimal productPrice(String productId) {
    return productFacade.show(productId).getPrice();
  }

  private CreditDto increaseCoinAmount(String coin) {
    return creditFacade.changeAmount(coin.toUpperCase(), 1);
  }

  public BigDecimal customerBalance() {
    return creditFacade.checkCustomerBalance();
  }

  private void decreaseProductAmount(String productId) {
    productFacade.changeAmount(productId, -1);
  }
}
