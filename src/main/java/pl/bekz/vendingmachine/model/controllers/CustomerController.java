package pl.bekz.vendingmachine.model.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bekz.vendingmachine.model.dto.ProductDto;
import pl.bekz.vendingmachine.model.services.CustomerService;

import java.math.BigDecimal;

@RestController
public class CustomerController {
    private final CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PutMapping("/insert/{coinName}")
    public ResponseEntity<BigDecimal> insertCoin(@PathVariable String coinName) {
        service.insertCoin(coinName);
        final BigDecimal credit = service.customerBalance();
        return new ResponseEntity<>(credit, HttpStatus.ACCEPTED);
    }

    @GetMapping("/products")
    public ResponseEntity<Page<ProductDto>> getProducts() {
        return new ResponseEntity<>(service.showAllAvailableProduct(), HttpStatus.OK);
    }

    @GetMapping("/buy/{productName}")
    public ResponseEntity<String> buyProduct(@PathVariable String productName) {
        return new ResponseEntity<>(service.buyProduct(productName), HttpStatus.OK);
    }

    @GetMapping("/refund")
    public ResponseEntity<BigDecimal> getMoneyBack() {
        return new ResponseEntity<>(service.reimburseMoneyToTheCustomer(), HttpStatus.OK);
    }

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> checkMyBalance() {
        return new ResponseEntity<>(service.customerBalance(), HttpStatus.OK);
    }


}
