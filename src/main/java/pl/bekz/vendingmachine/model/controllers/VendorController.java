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

import java.math.BigDecimal;

@RestController
public class VendorController {
    private VendorService service;

    @Autowired
    public VendorController(VendorService service) {
        this.service = service;
    }

    @PutMapping("/vendor/refill/{productName}")
    public ResponseEntity<ProductDto> refill(@PathVariable String productName){
        return new ResponseEntity<>(service.increaseProductAmount(productName), HttpStatus.ACCEPTED);
    }

    @GetMapping("/vendor/balance")
    public ResponseEntity<BigDecimal> balance(){
        return new ResponseEntity<>(service.machineBalance(), HttpStatus.OK);
    }

    @GetMapping("/vendor/payout")
    public ResponseEntity<BigDecimal> payOut(){
        final BigDecimal founds = service.machineBalance();
        service.withdrawMachineCoins();
        return new ResponseEntity<>(founds, HttpStatus.OK);
    }
}
