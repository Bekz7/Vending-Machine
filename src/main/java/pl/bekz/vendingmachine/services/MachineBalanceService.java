package pl.bekz.vendingmachine.services;

import org.springframework.stereotype.Service;
import pl.bekz.vendingmachine.VendingMachineFacade;


@Service
public class MachineBalanceService {

  private VendingMachineFacade vendingMachineFacade;

  public MachineBalanceService(VendingMachineFacade vendingMachineFacade) {
    this.vendingMachineFacade = vendingMachineFacade;
  }

  void checkMachineCoinsBalance() {
    vendingMachineFacade.checkMachineCoinBalance();
  }

  void getAllCoinsFromMachine() {
    vendingMachineFacade.getAllCoinsFromMachine();
  }

}
