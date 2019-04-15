package pl.bekz.vendingmachine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bekz.vendingmachine.exceptions.NotEnoughCoins;
import pl.bekz.vendingmachine.model.Money;
import pl.bekz.vendingmachine.model.OrderBalance;
import pl.bekz.vendingmachine.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

  private OrderBalance orderBalance;
  private List<Product> products;
  private int productNumber;

  @Autowired
  public OrderService(OrderBalance orderBalance) {
    this.orderBalance = orderBalance;
  }

  public void buyTheProduct() throws NotEnoughCoins {
    Product product = chooseProduct(productNumber);
    if (checkIsAvailableForPurchase()) {
      product.setAmount(product.getAmount() - 1);
      spendRest();
      return;
    }
    final String message = "You dont have enough money " + orderBalance.getBalance();
    throw new NotEnoughCoins(message);
  }

  private boolean checkIsAvailableForPurchase() {
    Product product = chooseProduct(productNumber);
    return product.getPrice().compareTo(orderBalance.getBalance()) >= 0 && product.getAmount() > 0;
  }

  private void spendRest() {
    Optional.of(orderBalance.getBalance()).ifPresent(bigDecimal -> orderBalance.coinsReturn());
  }

  private Product chooseProduct(int productNumber) {
    return products.get(productNumber);
  }

  public final BigDecimal insertDollar() {
    return orderBalance.insertCoins(Money.DOLLAR.getValue());
  }

  public final BigDecimal insertQuater(){
    return orderBalance.insertCoins(Money.QUARTER.getValue());
  }

  public final BigDecimal insertDime(){
    return orderBalance.insertCoins(Money.DIME.getValue());
  }

  public final BigDecimal insertNikel(){
    return orderBalance.insertCoins(Money.NICKEL.getValue());
  }

}
