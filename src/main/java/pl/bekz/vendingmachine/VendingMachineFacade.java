package pl.bekz.vendingmachine;

import org.springframework.beans.factory.annotation.Autowired;
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


}
