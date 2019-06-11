package pl.bekz.vendingmachine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bekz.vendingmachine.model.Money;
import pl.bekz.vendingmachine.services.CustomerBalanceService;

import java.math.BigDecimal;

@RestController
public class CustomerController {
  private final CustomerBalanceService service;

  @Autowired
  public CustomerController(CustomerBalanceService service) {
    this.service = service;
  }

  @GetMapping("/credit")
  public ResponseEntity<BigDecimal> getCredit() {
    final BigDecimal credit = service.getCostumerBalance();
    return new ResponseEntity<>(credit, HttpStatus.OK);
  }

  @PutMapping("/insert/{money}")
  public ResponseEntity insertCoin(@PathVariable final Money money) {
    service.insertCoin(money);
    return ResponseEntity.accepted().build();
  }

  @PutMapping("/return")
  public ResponseEntity returnCoin() {
    service.returnCoins();
    return ResponseEntity.ok().build();
  }

  @GetMapping("/buy/{productId}")
  public ResponseEntity buyProduct(@PathVariable final Long productId) {
    service.buyProduct(productId);
    return ResponseEntity.accepted().build();
  }
}
