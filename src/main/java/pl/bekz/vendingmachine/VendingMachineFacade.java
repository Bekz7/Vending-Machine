package pl.bekz.vendingmachine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.bekz.vendingmachine.exceptions.ExactChangeOnly;
import pl.bekz.vendingmachine.exceptions.NotEnoughCoins;
import pl.bekz.vendingmachine.exceptions.ProductNotFound;
import pl.bekz.vendingmachine.exceptions.ProductSoldOut;
import pl.bekz.vendingmachine.model.Money;
import pl.bekz.vendingmachine.model.Product;
import pl.bekz.vendingmachine.repositories.CostumerBalanceDAO;
import pl.bekz.vendingmachine.repositories.MachineCreditRepository;
import pl.bekz.vendingmachine.repositories.ProductRepository;

import java.math.BigDecimal;

@Component
public class VendingMachineFacade {

  private ProductRepository productRepository;
  private MachineCreditRepository machineCreditRepository;
  private CostumerBalanceDAO costumerBalanceDAO;

  @Autowired
  public VendingMachineFacade(
          ProductRepository productRepository,
          MachineCreditRepository machineCreditRepository,
          CostumerBalanceDAO costumerBalanceDAO) {
    this.productRepository = productRepository;
    this.machineCreditRepository = machineCreditRepository;
    this.costumerBalanceDAO = costumerBalanceDAO;
  }

  public void insertCoin(Money money) {
    costumerBalanceDAO.addCoin(money);
  }

  public void buyProduct(Long productId, Money money) {
    Product product = selectProduct(productId);

    exactChangeOnly(money);
    if (!productSoldOut(product) && !notEnoughMoney(money, product)) {
      saveCreditsInMachine(money);
      product.setAmount(product.getAmount() - 1);

      if (!exactChangeOnly(money)){
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

  private boolean notEnoughMoney(Money money, Product product) {
    if ((money.getValue()).compareTo(product.getPrice()) < 0) {
      throw new NotEnoughCoins("You don't have enough money to buy selected product");
    }
    return false;
  }

  public void refillProduct(Long id, Integer amount) {
    Product product = selectProduct(id);
    product.setAmount(amount);
    productRepository.save(product);
  }

  private void saveCreditsInMachine(Money money) {
    machineCreditRepository.saveCredits(money.getValue());
  }

  private boolean exactChangeOnly(Money money) {
    if ((machineCreditRepository.getMachineCredits()).compareTo(money.getValue()) < 0){
      throw new ExactChangeOnly("Exact change only");
    }
    return false;
  }

  private void returnMoneyToCostumerAfterBuying(BigDecimal productCost){
    costumerBalanceDAO.updateCoinsBalance(productCost);
  }

  public void returnCoin(){
    costumerBalanceDAO.updateCoinsBalance(BigDecimal.valueOf(0));
  }

  public void getCostumerBalance(){
    costumerBalanceDAO.getBalance();
  }

  public void checkMachineCoinBalance(){
    machineCreditRepository.findFirstByOrderByPublicationDateDesc();
  }

  public void getAllCoinsFromMachine(){
    machineCreditRepository.d
  }
}
