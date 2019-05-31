package pl.bekz.vendingmachine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bekz.vendingmachine.VendingMachineFacade;
import pl.bekz.vendingmachine.repositories.MachineCreditRepository;

import java.math.BigDecimal;

@Service
public class MachineBalanceService {

    private VendingMachineFacade vendingMachineFacade;

    public MachineBalanceService(VendingMachineFacade vendingMachineFacade) {
        this.vendingMachineFacade = vendingMachineFacade;
    }

    void checkMachineCoinsBalance(){
        vendingMachineFacade.checkMachineCoinBalance();
    }

    void getAllCoinsFromMachine(){
        vendingMachineFacade
    }
}
