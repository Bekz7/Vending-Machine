package pl.bekz.vendingmachine;

import org.springframework.beans.factory.annotation.Autowired;
import pl.bekz.vendingmachine.exceptions.ProductNotFound;
import pl.bekz.vendingmachine.exceptions.ProductSoldOut;
import pl.bekz.vendingmachine.model.Money;
import pl.bekz.vendingmachine.model.Product;
import pl.bekz.vendingmachine.repositories.CostumerCreditRepository;
import pl.bekz.vendingmachine.repositories.MachineCreditRepository;
import pl.bekz.vendingmachine.repositories.ProductRepository;

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

  void buyProduct(Long productId){
    Product product = selectProduct(productId);

    if (!productSoldOut(product)){

    }
  }

  private boolean productSoldOut(Product product) {
    if (product.getAmount() < 1) {
      throw new ProductSoldOut("Product was sold out");
    }
    return false;
  }

  private Product selectProduct(Long id){
   return productRepository
           .findById(id)
           .orElseThrow(() -> new ProductNotFound("Can' find product by id: " + id));
  }

  private void refilProduct(Long id, Integer amount){
    Product product = selectProduct(id);
    product.setAmount(amount);
    productRepository.save(product);
  }
}
