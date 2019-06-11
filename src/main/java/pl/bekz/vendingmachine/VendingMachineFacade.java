package pl.bekz.vendingmachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bekz.vendingmachine.exceptions.ExactChangeOnly;
import pl.bekz.vendingmachine.exceptions.NotEnoughCoins;
import pl.bekz.vendingmachine.exceptions.ProductNotFound;
import pl.bekz.vendingmachine.exceptions.ProductSoldOut;
import pl.bekz.vendingmachine.model.MachineBalance;
import pl.bekz.vendingmachine.model.Money;
import pl.bekz.vendingmachine.model.Product;
import pl.bekz.vendingmachine.repositories.CustomerBalanceDAO;
import pl.bekz.vendingmachine.repositories.MachineCreditRepository;
import pl.bekz.vendingmachine.repositories.ProductRepository;

import java.math.BigDecimal;

@Component
public class VendingMachineFacade {

  private ProductRepository productRepository;
  private MachineCreditRepository machineCreditRepository;
  private CustomerBalanceDAO customerBalanceDAO;

  @Autowired
  public VendingMachineFacade(
      ProductRepository productRepository,
      MachineCreditRepository machineCreditRepository,
      CustomerBalanceDAO customerBalanceDAO) {
    this.productRepository = productRepository;
    this.machineCreditRepository = machineCreditRepository;
    this.customerBalanceDAO = customerBalanceDAO;
  }

  public void insertCoin(Money money) {
    customerBalanceDAO.insertCoin(money);
  }

  public void buyProduct(Long productId) {
    final Product product = selectProduct(productId);
    exactChangeOnly(product);

    if (!productSoldOut(product) && !notEnoughMoney(product)) {
      saveCreditsInMachine(product);
      product.setAmount(product.getAmount() - 1);

      if (!exactChangeOnly(product)) {
        returnMoneyToCostumerAfterBuying(product.getPrice());
      }
    }
  }

  private Product selectProduct(Long id) {
    return productRepository
        .findById(id)
        .orElseThrow(() -> new ProductNotFound("Can' find product by id: " + id));
  }

  private boolean productSoldOut(Product product) {
    if (product.getAmount() < 1) {
      throw new ProductSoldOut("Product was sold out");
    }
    return false;
  }

  private boolean notEnoughMoney(Product product) {
    if ((getCostumerBalance()).compareTo(product.getPrice()) < 0) {
      throw new NotEnoughCoins("You don't have enough money to buy selected product");
    }
    return false;
  }

  public void refillProduct(Long id, Integer amount) {
    Product product = selectProduct(id);
    product.setAmount(amount);
    productRepository.save(product);
  }

  private void saveCreditsInMachine(Product product) {
    final BigDecimal productPrice = product.getPrice();
    final MachineBalance machineBalance =
        machineCreditRepository
            .findTopByOrderByIdDesc();

    machineBalance.setMachineBalance(productPrice);
  }

  private boolean exactChangeOnly(Product product) {
    if ((checkMachineCoinBalance()).compareTo(product.getPrice()) < 0) {
      throw new ExactChangeOnly("Exact change only");
    }
    return false;
  }

  private void returnMoneyToCostumerAfterBuying(BigDecimal productCost) {
    customerBalanceDAO.updateCoinsBalance(productCost);
  }

  public void returnCoin() {
    customerBalanceDAO.updateCoinsBalance(BigDecimal.valueOf(0));
  }

  public BigDecimal getCostumerBalance() {
    return customerBalanceDAO.getBalance();
  }

  public BigDecimal checkMachineCoinBalance() {
    return machineCreditRepository.findTopByOrderByIdDesc().getMachineBalance();
  }

  public void getAllCoinsFromMachine() {
    machineCreditRepository.deleteAll();
  }
}
