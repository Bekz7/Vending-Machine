package pl.bekz.vendingmachine.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import pl.bekz.vendingmachine.exceptions.NotEnoughCoins;
import pl.bekz.vendingmachine.exceptions.ProductSoldOut;
import pl.bekz.vendingmachine.model.dto.CreditDto;
import pl.bekz.vendingmachine.model.dto.ProductDto;
import pl.bekz.vendingmachine.model.entities.Credit;
import pl.bekz.vendingmachine.model.entities.Product;
import pl.bekz.vendingmachine.model.entities.Transaction;
import pl.bekz.vendingmachine.repositories.CreditsRepository;
import pl.bekz.vendingmachine.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

@Transactional
public class VendingMachineFacade {
  private ProductCreator productCreator;
  private CreditCreator creditCreator;
  private Transaction transaction;
  private CreditsRepository creditsRepository;
  private ProductRepository productRepository;

  public VendingMachineFacade(
      ProductCreator productCreator,
      CreditCreator creditCreator,
      Transaction transaction,
      CreditsRepository creditsRepository,
      ProductRepository productRepository) {
    this.productCreator = productCreator;
    this.creditCreator = creditCreator;
    this.transaction = transaction;
    this.creditsRepository = creditsRepository;
    this.productRepository = productRepository;
  }

  public ProductDto add(ProductDto productDto) {
    requireNonNull(productDto);
    Product product = productCreator.from(productDto);
    product = productRepository.saveProduct(product);
    return product.productDto();
  }

  public ProductDto show(String productDto) {
    requireNonNull(productDto);
    Product product = productRepository.findOneOrThrow(productDto);
    return product.productDto();
  }

  public Page<ProductDto> findAllProducts(Pageable pageable) {
    requireNonNull(pageable);
    return productRepository.findAll(pageable).map(Product::productDto);
  }

  public Product refill(String productName) {
    requireNonNull(productName);
    return changeProductAmount(productName, 1);
  }

  public Product decreaseProductAmount(String productName) {
    requireNonNull(productName);
    return changeProductAmount(productName, -1);
  }

  private Product changeProductAmount(String name, int amount) {
    requireNonNull(name);
    Product product = productRepository.findById(name);
    final int amountToChange = product.productDto().getAmount() + amount;
    product = Product.builder().name(name).amount(amountToChange).build();
    return productRepository.saveProduct(product);
  }

  public void insertCoin(Money coin) {
    requireNonNull(coin);
    CreditDto creditDto =
        CreditDto.builder()
            .coinName(coin.getCoinName())
            .coinValue(coin.getValue())
            .coinsNumber(1)
            .build();
    Credit credit = creditCreator.from(creditDto);
    creditsRepository.saveCredit(credit);
    transaction.setTransactionBalance(coin.getValue().add(transaction.getTransactionBalance()));
  }

  public BigDecimal checkCustomerBalance() {
    return transaction.getTransactionBalance();
  }

  public void returnCustomerCoins() {
    transaction.setTransactionBalance(BigDecimal.ZERO);
  }

  public void buyProduct(String productId) {
    requireNonNull(productId);
  }

  private void returnRestCoins(BigDecimal coinsToReturn) {
    for (int i=0; i<creditsRepository.count(); i++){

    }
  }

  private void productOutOfStock(String productId) {
    if (!isProductInStock(productId)) {
      throw new ProductSoldOut(productId);
    }
  }

  private void notEnoughCoins(BigDecimal customerCredit, ProductDto product) {
    if (!haveEnoughCredit(customerCredit, product)) {
      throw new NotEnoughCoins();
    }
  }

  private boolean haveEnoughCredit(BigDecimal customerCredit, ProductDto product) {
    return customerCredit.compareTo(product.getPrice()) > 0;
  }

  private boolean isProductInStock(String productId) {
    return show(productId).getAmount() > 0;
  }

  private boolean exactChangeOnly() {
    BigDecimal restAfterBuying = transaction.getTransactionBalance();
    while (!BigDecimal.ZERO.equals(restAfterBuying)){
//      restAfterBuying = restAfterBuying.subtract()
    }
    return restAfterBuying.intValue() < 0;
  }

  private Map<BigDecimal, Integer> transactionMachineBalance(){
    Map<BigDecimal, Integer> temp = new ConcurrentHashMap<>();

    for (int i = 0; i < creditsRepository.count(); i++) {
      BigDecimal coinValue = creditsRepository.findById(i).creditsDto().getCoinValue();
      Integer coinsNumber = creditsRepository.findById(i).creditsDto().getCoinsNumber();
      temp.put(coinValue, coinsNumber);
    }
    return temp;
  }

  //TODO Make this right
  private BigDecimal getMostValueCoin(){
    return BigDecimal.ZERO;
  }
//TODO check is this work properly
  public BigDecimal checkMachineCoinBalance() {
    final BigDecimal[] machineBalance = {BigDecimal.ZERO};
    transactionMachineBalance().forEach((key, value) -> machineBalance[0] = machineBalance[0].add(key.multiply(BigDecimal.valueOf(value))));

    return machineBalance[0];
  }

  public void WithdrawMachineDeposit() {
    creditsRepository.deleteAll();
  }
}
