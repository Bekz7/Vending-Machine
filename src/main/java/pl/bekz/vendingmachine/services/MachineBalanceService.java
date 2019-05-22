package pl.bekz.vendingmachine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.bekz.vendingmachine.repositories.MachineCreditRepository;

import java.math.BigDecimal;

@Service
public class MachineBalanceService {

    private MachineCreditRepository machineCreditRepository;

    @Autowired
    public MachineBalanceService(MachineCreditRepository machineCreditRepository) {
        this.machineCreditRepository = machineCreditRepository;
    }

    BigDecimal getMachineCredits(){
        return machineCreditRepository.getMachineCredits();
    }

    BigDecimal sevaCredits(BigDecimal credits){
        return machineCreditRepository.saveCredits(credits);
    }


}
