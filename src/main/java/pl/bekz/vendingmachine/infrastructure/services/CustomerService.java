package pl.bekz.vendingmachine.infrastructure.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.bekz.vendingmachine.infrastructure.exceptions.*;
import pl.bekz.vendingmachine.machine.domain.Money;
import pl.bekz.vendingmachine.machine.dto.CreditDto;
import pl.bekz.vendingmachine.machine.dto.ProductDto;
import pl.bekz.vendingmachine.machine.facades.CreditFacade;
import pl.bekz.vendingmachine.machine.facades.ProductFacade;

import java.math.BigDecimal;

@Slf4j
@Service
public class CustomerService {

  private final CreditFacade creditFacade;
  private final ProductFacade productFacade;

  public CustomerService(CreditFacade creditFacade, ProductFacade productFacade) {
    this.creditFacade = creditFacade;
    this.productFacade = productFacade;
  }

  public void insertCoin(String coinName) {
    try {
      Money coin = Money.valueOf(coinName.toUpperCase());
      creditFacade.add(increaseCoinAmount(coin.name()));
      creditFacade.increaseCustomerBalance(coin);

      log.debug("Insert the coin with customer balance: {} , and machine balance: {}",
              creditFacade.checkCustomerBalance(),
              creditFacade.checkMachineCoinBalance());

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

    logTransactionInfo(productName, "before");

    conditionToSold(productName);

    decreaseProductAmount(productName);
    creditFacade.decreesCustomerBalance(selectedProductPrice);
    creditFacade.decreesMachineBalance();

    logTransactionInfo(productName, "after");

    reimburseMoneyToTheCustomer();

    log.debug("Customer balance after transaction {}", customerBalance());

    return true;
  }

  private void conditionToSold(String productName){
    final BigDecimal selectedProductPrice = productPrice(productName);

    if (productFacade.productAvailable.negate().test(productName)){
      throw new ProductSoldOut(productName);
    }

    if (creditFacade.enoughCredit.negate().test(selectedProductPrice)){
      throw new NotEnoughCoins();
    }

    if (creditFacade.exactChange.test(customerBalance().subtract(selectedProductPrice))){
      throw new ExactChangeOnly();
    }
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

  private void logTransactionInfo(String productName, String transactionTime) {
    log.debug(transactionTime + "buying the product customer balance: {} , and machine balance: {}",
            creditFacade.checkCustomerBalance(),
            creditFacade.checkMachineCoinBalance());

    log.debug("Product amount " + transactionTime +" buying: {}", productFacade.show(productName).getAmount());
  }
}
