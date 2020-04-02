package pl.bekz.vendingmachine.model.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bekz.vendingmachine.model.Money;
import pl.bekz.vendingmachine.model.entities.Product;
import pl.bekz.vendingmachine.model.services.CustomerService;

import java.math.BigDecimal;

@RestController
public class CustomerController {
    private final CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PutMapping("/insert")
    public ResponseEntity<BigDecimal> insertCoin(@RequestBody Money coin){
        service.insertCoin(coin);
        final BigDecimal credit = service.customerBalance();
        return new ResponseEntity<>(credit, HttpStatus.ACCEPTED);
    }

    @GetMapping("/products")
    public ResponseEntity<Page> getProducts(){
        return new ResponseEntity(service.showAllAvailableProduct(), HttpStatus.OK);
    }

    @PutMapping("/buy/{productName}")
    public ResponseEntity<Product> buyProduct(@PathVariable String productName){
        return new ResponseEntity<>(service.buyProduct(productName), HttpStatus.ACCEPTED)
    }

}
