package pl.bekz.vendingmachine;

import org.springframework.beans.factory.annotation.Autowired;
import pl.bekz.vendingmachine.exceptions.ExactChangeOnly;
import pl.bekz.vendingmachine.exceptions.NotEnoughCoins;
import pl.bekz.vendingmachine.exceptions.ProductNotFound;
import pl.bekz.vendingmachine.exceptions.ProductSoldOut;
import pl.bekz.vendingmachine.model.Money;
import pl.bekz.vendingmachine.model.Product;
import pl.bekz.vendingmachine.repositories.CostumerCreditRepository;
import pl.bekz.vendingmachine.repositories.MachineCreditRepository;
import pl.bekz.vendingmachine.repositories.ProductRepository;

import java.math.BigDecimal;


public class VendingMachineFacade {

  private ProductRepository productRepository;
  private MachineCreditRepository machineCreditRepository;
  private CostumerCreditRepository costumerCreditRepository;

  @Autowired
  public VendingMachineFacade(
      ProductRepository productRepository,
      MachineCreditRepository machineCreditRepository,
      CostumerCreditRepository costumerCreditRepository) {
    this.productRepository = productRepository;
    this.machineCreditRepository = machineCreditRepository;
    this.costumerCreditRepository = costumerCreditRepository;
  }

  void insertCoin(Money money) {
    costumerCreditRepository.addCredit(money);
  }

  private void buyProduct(Long productId, Money money) {
    Product product = selectProduct(productId);

    exactChangeOnly(money);
    if (!productSoldOut(product) && !notEnoughMoney(money, product)) {
      machineCreditRepository.saveCredits(money.getValue());
      product.setAmount(product.getAmount() - 1);
      BigDecimal change = (costumerCreditRepository.getCostumerCredits()).subtract(product.getPrice());

      if (!exactChangeOnly(money)){
        costumerCreditRepository.returnCredits(change);
      }
    }
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

  private Product selectProduct(Long id) {
    return productRepository
        .findById(id)
        .orElseThrow(() -> new ProductNotFound("Can' find product by id: " + id));
  }

  private void refilProduct(Long id, Integer amount) {
    Product product = selectProduct(id);
    product.setAmount(amount);
    productRepository.save(product);
  }

  private void saveCreditsInMachine(Money money) {
    machineCreditRepository.saveCredits(money.getValue());
  }

  private boolean exactChangeOnly(Money money) {
    if ((machineCreditRepository.getMachineCredits()).compareTo(money.getValue()) < 0){
      throw new ExactChangeOnly("exact change only");
    }
    return false;
  }
}
