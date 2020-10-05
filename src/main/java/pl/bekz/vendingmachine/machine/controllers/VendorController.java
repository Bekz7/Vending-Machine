package pl.bekz.vendingmachine.machine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bekz.vendingmachine.machine.dto.ProductDto;
import pl.bekz.vendingmachine.infrastructure.services.VendorService;

import static java.util.Objects.requireNonNull;
import java.math.BigDecimal;

@RestController
@RequestMapping("/vendor")
public class VendorController {
    private VendorService service;
    private final ResponseBody responseBody = new ResponseBody();

    @Autowired
    public VendorController(VendorService service) {
        this.service = service;
    }

    @PutMapping("/refill/{productName}")
    public ResponseEntity<ProductDto> refill(@PathVariable String productName){
        requireNonNull(productName);

        return responseBody.with(service.increaseProductAmount(productName.toUpperCase()), HttpStatus.ACCEPTED);
    }

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> balance(){
        return responseBody.withOkStatus(service.machineBalance());
    }

    @GetMapping("/payout")
    public ResponseEntity<BigDecimal> payOut(){
        final BigDecimal founds = service.machineBalance();
        service.withdrawMachineCoins();
        return responseBody.withOkStatus(founds);
    }
}
