package pl.bekz.vendingmachine.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import pl.bekz.vendingmachine.exceptions.ExactChangeOnly;
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
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
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

  public ProductDto refill(String productName) {
    requireNonNull(productName);
    return changeProductAmount(productName, 1);
  }

  public void decreaseProductAmount(String productName) {
    requireNonNull(productName);
    changeProductAmount(productName, -1);
  }

  private ProductDto changeProductAmount(String name, int amount) {
    requireNonNull(name);
    Product product = productRepository.findOneOrThrow(name);
    final int amountToChange = product.productDto().getAmount() + amount;
    product = Product.builder().name(name).amount(amountToChange).build();
    return productRepository.saveProduct(product).productDto();
  }

  public void insertCoin(Money coin) {
    requireNonNull(coin);
    CreditDto creditDto = addCoinNewCoin(coin);
    Credit insertedCoin = creditCreator.from(creditDto);
    creditsRepository.save(insertedCoin);
    transaction.setTransactionBalance(coin.getValue().add(transaction.getTransactionBalance()));
  }

  private CreditDto changeCoinAmount(Money coin, int coinNumberToChange){
    CreditDto creditDto = creditsRepository.findOrCreate(coin).creditsDto();
    return Optional.ofNullable(creditDto)
            .map(dto -> CreditDto.builder().coinsNumber(dto.getCoinsNumber() + coinNumberToChange).build())
            .get();
  }

  private CreditDto decreesCoins(Money coin){
    final int coinDecrees = -1;
    return changeCoinAmount(coin, coinDecrees);
  }

  private CreditDto addCoinNewCoin(Money coin) {
    final int coinIncest = 1;
    return changeCoinAmount(coin, coinIncest);
  }

  public BigDecimal checkCustomerBalance() {
    return transaction.getTransactionBalance();
  }

  public void returnCustomerCoins() {
    transaction.setTransactionBalance(BigDecimal.ZERO);
  }

  public void buyProduct(String productName) {
    requireNonNull(productName);

    checkIsProductAvailable(productName);
    checkIfCoinEnough(productName);
     checkIfExactChangeOnly(productName);
    decreaseProductAmount(productName);
    transaction.setTransactionBalance(clientBalanceAfterBuying(productName));
  }

  private BigDecimal clientBalanceAfterBuying(String productId) {
    BigDecimal clientBalance = transaction.getTransactionBalance();
    BigDecimal price = show(productId).getPrice();
    return clientBalance.subtract(price);
  }

  private void checkIsProductAvailable(String productId) {
    if (!isProductInStock(productId)) {
      throw new ProductSoldOut(productId);
    }
  }

  private void checkIfCoinEnough(String productId) {
    if (!haveEnoughCredit(productId)) {
      throw new NotEnoughCoins();
    }
  }

  private boolean haveEnoughCredit(String productId) {
    return transaction.getTransactionBalance().compareTo(show(productId).getPrice()) > 0;
  }

  private boolean isProductInStock(String productId) {
    return show(productId).getAmount() > 0;
  }

  private void checkIfExactChangeOnly(String productName) {
    if (exactChangeOnly(productName)) {
      throw new ExactChangeOnly();
    }
  }

  public boolean exactChangeOnly(String productName) {
    return BigDecimal.ZERO.compareTo(returnRestMoney(clientBalanceAfterBuying(productName))) != 0;
  }

  //TODO how to remember deceased coins from map?
  private BigDecimal returnRestMoney(BigDecimal restAfterBuying) {
    Map<String, Integer> temporaryCoinToReturn = new ConcurrentHashMap<>();
    while (BigDecimal.ZERO.compareTo(restAfterBuying) > 0) {

      restAfterBuying = restAfterBuying.subtract(getMostValueCoinTReturn());
    }
    return restAfterBuying;
  }

  private String getMostValueCoinNameToReturn(){
    return creditsRepository.getCredits().values().stream()
            .map(credit -> credit.creditsDto().getCoinName())
            .filter(s -> )
  }
  private BigDecimal getMostValueCoinTReturn() {
    return creditsRepository.getCredits().values().stream()
        .map(credit -> credit.creditsDto().getCoinValue())
        .filter(coin -> coin.compareTo(transaction.getTransactionBalance()) <= 0)
        .max(Comparator.naturalOrder())
        .get();
  }

  public BigDecimal checkMachineCoinBalance() {
    BigDecimal[] machineBalance = {BigDecimal.ZERO};
    creditsRepository
        .getCredits()
        .forEach(
            (key, value) ->
                machineBalance[0] =
                    machineBalance[0].add(
                        value
                            .creditsDto()
                            .getCoinValue()
                            .multiply(BigDecimal.valueOf(value.creditsDto().getCoinsNumber()))));

    return machineBalance[0];
  }

  public void WithdrawMachineDeposit() {
    creditsRepository.deleteAll();
  }
}
