package pl.bekz.vendingmachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

  private Order order;
  private List<Product> products;
  private int productNumber;

  @Autowired
  public OrderService(Order order) {
    this.order = order;
  }

  public void buyTeProduct() {
    Product product = chooseProduct(productNumber);
      if (checkIsAvailableForPurchase()) {
        product.setAmount(product.getAmount() - 1);
        spendRest();
      }
      final String message = "You dont have enough money " + order.getBalance();
      throw new NotEnoughCoins(message);
  }

  private boolean checkIsAvailableForPurchase() {
    Product product = chooseProduct(productNumber);
    return product.getPrice().compareTo(order.getBalance()) >= 0 && product.getAmount() > 0;
  }

  private void spendRest() {
      Optional.of(order.getBalance()).ifPresent(bigDecimal -> order.coinsReturn());
  }

  private Product chooseProduct(int productNumber) {
    return products.get(productNumber);
  }

  public void insertCoin(Money money){

  }
}
