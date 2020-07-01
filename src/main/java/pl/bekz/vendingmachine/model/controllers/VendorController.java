package pl.bekz.vendingmachine.model.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bekz.vendingmachine.model.dto.ProductDto;
import pl.bekz.vendingmachine.model.services.VendorService;

import static java.util.Objects.requireNonNull;
import java.math.BigDecimal;

@RestController
public class VendorController {
    private VendorService service;
    private final ResponseBody responseBody = new ResponseBody();

    @Autowired
    public VendorController(VendorService service) {
        this.service = service;
    }

    @PutMapping("/vendor/refill/{productName}")
    public ResponseEntity<ProductDto> refill(@PathVariable String productName){
        requireNonNull(productName);

        return responseBody.with(service.increaseProductAmount(productName.toUpperCase()), HttpStatus.ACCEPTED);
    }

    @GetMapping("/vendor/balance")
    public ResponseEntity<BigDecimal> balance(){
        return responseBody.withOkStatus(service.machineBalance());
    }

    @GetMapping("/vendor/payout")
    public ResponseEntity<BigDecimal> payOut(){
        final BigDecimal founds = service.machineBalance();
        service.withdrawMachineCoins();
        return responseBody.withOkStatus(founds);
    }
}
