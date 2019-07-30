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
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

@Transactional
public class VendingMachineFacade {
  private ProductCreator productCreator;
  private CustomerCreditsRepository customerCreditsRepository;
  private MachineCreditsRepository machineCreditsRepository;
  private ProductRepository productRepository;

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
    return productRepository.changeProductAmount(product.productDto().getName(), 1);
  }

  public void insertCoin(Money coin) {
    requireNonNull(coin);
    customerCreditsRepository.insertCoin(coin);
  }

  public BigDecimal checkCustomerBalance() {
    return customerCreditsRepository.checkBalance();
  }

  public void returnCustomerCoins() {
    customerCreditsRepository.clearCoinsBalance();
  }

  public void buyProduct(String productId) {
    requireNonNull(productId);
    BigDecimal customerCredit = checkCustomerBalance();

    productOutOfStock(productId);

    final ProductDto product = show(productId);

    notEnoughCoins(customerCredit, product);

    customerCredit.subtract(product.getPrice());
    persistCustomerCoins();
    if (exactChangeOnly(product)) {
      throw new ExactChangeOnly();
    }

    decreaseProductAmount(productId);
    customerCreditsRepository.clearCoinsBalance();
  }

  private void productOutOfStock(String productId) {
    if (!isProductOnStock(productId)) {
      throw new ProductSoldOut(productId);
    }
  }

  private void notEnoughCoins(BigDecimal customerCredit, ProductDto product){
    if (!haveEnoughCredit(customerCredit, product)) {
      throw new NotEnoughCoins();
    }
  }

  private boolean haveEnoughCredit(BigDecimal customerCredit, ProductDto product) {
    return customerCredit.compareTo(product.getPrice()) > 0;
  }

  private boolean isProductOnStock(String productId) {
    return show(productId).getAmount() > 0;
  }

  private boolean exactChangeOnly(ProductDto product) {
    BigDecimal restAfterBuying =
        machineCreditsRepository.checkBalance().subtract(product.getPrice());
    return restAfterBuying.intValue() < 0;
  }

  private void decreaseProductAmount(String productId) {
    productRepository.changeProductAmount(productId, -1);
  }

  public BigDecimal checkMachineCoinBalance() {
    return machineCreditsRepository.checkBalance();
  }

  public void WithdrawMachineDeposit() {
    machineCreditsRepository.clearCoinsBalance();
  }

  private void persistCustomerCoins() {
    ConcurrentHashMap<Money, Integer> machineCoins = machineCreditsRepository.getAllCredits();
    ConcurrentHashMap<Money, Integer> customerCoins = customerCreditsRepository.getAllCredits();
    machineCoins
        .entrySet()
        .forEach(entry -> entry.setValue((entry.getValue() + customerCoins.get(entry.getKey()))));
  }

  private void spendRest(BigDecimal productCost){
    persistCustomerCoins();

  }
}
