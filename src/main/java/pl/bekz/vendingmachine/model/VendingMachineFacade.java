package pl.bekz.vendingmachine.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import pl.bekz.vendingmachine.exceptions.ExactChangeOnly;
import pl.bekz.vendingmachine.exceptions.NotEnoughCoins;
import pl.bekz.vendingmachine.exceptions.ProductSoldOut;
import pl.bekz.vendingmachine.model.dto.ProductDto;
import pl.bekz.vendingmachine.repositories.CustomerCreditsRepository;
import pl.bekz.vendingmachine.repositories.MachineCreditsRepository;
import pl.bekz.vendingmachine.repositories.ProductRepository;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

@Transactional
public class VendingMachineFacade {
  ProductCreator productCreator;
  CustomerCreditsRepository customerCreditsRepository;
  MachineCreditsRepository machineCreditsRepository;
  ProductRepository productRepository;

  public VendingMachineFacade(
      ProductCreator productCreator,
      CustomerCreditsRepository customerCreditsRepository,
      MachineCreditsRepository machineCreditsRepository,
      ProductRepository productRepository) {
    this.productCreator = productCreator;
    this.customerCreditsRepository = customerCreditsRepository;
    this.machineCreditsRepository = machineCreditsRepository;
    this.productRepository = productRepository;
  }

  public ProductDto add(ProductDto productDto) {
    requireNonNull(productDto);
    Product product = productCreator.from(productDto);
    product = productRepository.addNewProduct(product);
    return product.productDto();
  }

  public ProductDto show(String productDto) {
    requireNonNull(productDto);
    Product product = productRepository.findOneOrThrow(productDto);
    return product.productDto();
  }

  public Page<ProductDto> findAll(Pageable pageable) {
    requireNonNull(pageable);
    return productRepository.findAll(pageable).map(Product::productDto);
  }

  public Product refill(String productDto) {
    requireNonNull(productDto);
    Product product = productRepository.findById(productDto);
    return productRepository.refill(product.productDto().getName());
  }

  public void insertCoin(Money coin) {
    requireNonNull(coin);
    customerCreditsRepository.insertCoin(coin);
  }

  public BigDecimal checkCustomerBalance() {
    return customerCreditsRepository.checkCoinsBalance();
  }

  public void returnCustomerCoins() {
    customerCreditsRepository.clearCoinsBalance();
  }

  public void buyProduct(String productId) {
    BigDecimal customerCredit = checkCustomerBalance();

    if (!isProductOnStock(productId)) {
      throw new ProductSoldOut(productId);
    }

    final ProductDto product = show(productId);

    if (!haveEnoughCredit(customerCredit, product)) {
      throw new NotEnoughCoins();
    }

    machineCreditsRepository.persistCoins(product.getPrice());

    if (exactChangeOnly(product)) {
      machineCreditsRepository.persistCoins(
          machineCreditsRepository.checkCoinsBalance().subtract(product.getPrice()));
      throw new ExactChangeOnly();
    }

    customerCredit = customerCredit.subtract(product.getPrice());
    customerCreditsRepository.persistCoins(customerCredit);
  }

  private boolean haveEnoughCredit(BigDecimal customerCredit, ProductDto product) {
    return customerCredit.compareTo(product.getPrice()) > 0;
  }

  private boolean isProductOnStock(String productId) {
    return show(productId).getAmount() > 0;
  }

  private boolean exactChangeOnly(ProductDto product) {
    return machineCreditsRepository
            .checkCoinsBalance()
            .subtract(customerCreditsRepository.checkCoinsBalance())
            .intValue()
        > 0;
  }

  public void checkMachineCoinBalance(){
    machineCreditsRepository.checkCoinsBalance();
  }

  public void WithdrawMachineDeposit(){
    machineCreditsRepository.clearCoinsBalance();
  }

}
