package pl.bekz.vendingmachine.infrastructure.services;

import org.springframework.stereotype.Service;
import pl.bekz.vendingmachine.machine.dto.ProductDto;
import pl.bekz.vendingmachine.machine.facades.CreditFacade;
import pl.bekz.vendingmachine.machine.facades.ProductFacade;

import java.math.BigDecimal;

@Service
public class VendorService {
  private CreditFacade creditFacade;
  private ProductFacade productFacade;

  public VendorService(CreditFacade creditFacade, ProductFacade productFacade) {
    this.creditFacade = creditFacade;
    this.productFacade = productFacade;
  }

  public ProductDto increaseProductAmount(String productName) {
    return productFacade.add(increaseAmount(productName));
  }

  private ProductDto increaseAmount(String productName) {
    return productFacade.changeAmount(productName, 1);
  }

  public BigDecimal machineBalance() {
    return creditFacade.checkMachineCoinBalance();
  }

  public void withdrawMachineCoins() {
    creditFacade.withdrawMachineDeposit();
  }
}
