package pl.bekz.vendingmachine.machine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bekz.vendingmachine.machine.dto.ProductDto;
import pl.bekz.vendingmachine.infrastructure.services.CustomerService;

import static java.util.Objects.requireNonNull;
import java.math.BigDecimal;

@RestController
public class CustomerController {
    private final CustomerService service;
    private final ResponseBody responseBody = new ResponseBody();

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PutMapping("/insert/{coinName}")
    public ResponseEntity<BigDecimal> insertCoin(@PathVariable String coinName) {
        requireNonNull(coinName);

        service.insertCoin(coinName);
        return responseBody.with(service.customerBalance(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/products")
    public ResponseEntity<Page<ProductDto>> getProducts() {
        return responseBody.withOkStatus(service.showAllAvailableProduct());
    }

    @GetMapping("/buy/{productName}")
    public ResponseEntity<Boolean> buyProduct(@PathVariable String productName) {
        requireNonNull(productName);

        return responseBody.withOkStatus(service.buyProduct(productName));
    }

    @GetMapping("/refund")
    public ResponseEntity<BigDecimal> getMoneyBack() {
        return responseBody.withOkStatus(service.reimburseMoneyToTheCustomer());
    }

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> checkMyBalance() {
       return responseBody.withOkStatus(service.customerBalance());
    }
}
