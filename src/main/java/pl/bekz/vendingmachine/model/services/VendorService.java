package pl.bekz.vendingmachine.model.services;

import org.springframework.stereotype.Service;
import pl.bekz.vendingmachine.model.dto.ProductDto;
import pl.bekz.vendingmachine.model.facades.CreditFacade;
import pl.bekz.vendingmachine.model.facades.ProductFacade;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

@Service
public class VendorService {
  private CreditFacade creditFacade;
  private ProductFacade productFacade;

  public VendorService(CreditFacade creditFacade, ProductFacade productFacade) {
    this.creditFacade = creditFacade;
    this.productFacade = productFacade;
  }

  public void increaseProductAmount(String productName) {
    requireNonNull(productName);
    productFacade.add(increaseAmount(productName));
  }

  private ProductDto increaseAmount(String productName) {
    return productFacade.changeAmount(productName, 1);
  }

  public BigDecimal machineBalance(){
      return creditFacade.checkMachineCoinBalance();
  }

  public void WithdrawMachineCoins(){
    creditFacade.WithdrawMachineDeposit();
  }
}
