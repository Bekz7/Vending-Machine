package pl.bekz.vendingmachine.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import pl.bekz.vendingmachine.exceptions.NotEnoughCoins;
import pl.bekz.vendingmachine.exceptions.ProductSoldOut;
import pl.bekz.vendingmachine.model.dto.ProductDto;
import pl.bekz.vendingmachine.repositories.CustomerCreditsRepository;
import pl.bekz.vendingmachine.repositories.CreditsRepository;
import pl.bekz.vendingmachine.repositories.ProductRepository;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

@Transactional
public class ProductFacade {
  ProductCreator productCreator;
  CustomerCreditsRepository customerCreditsRepository;
  CreditsRepository creditsRepository;
  ProductRepository productRepository;

  public ProductFacade(
      ProductCreator productCreator,
      CustomerCreditsRepository customerCreditsRepository,
      CreditsRepository creditsRepository,
      ProductRepository productRepository) {
    this.productCreator = productCreator;
    this.customerCreditsRepository = customerCreditsRepository;
    this.creditsRepository = creditsRepository;
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

  public Integer insertCoin(Money coin) {
    requireNonNull(coin);
    return customerCreditsRepository.insertCoin(coin);
  }

  public BigDecimal checkCustomerBalance() {
    return customerCreditsRepository.checkCoinsBalance();
  }

  public void returnCoins() {
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

    customerCredit = customerCredit.subtract(product.getPrice());
    customerCreditsRepository.creditMapper(customerCredit);

  }

  private boolean haveEnoughCredit(BigDecimal customerCredit, ProductDto product) {
    return customerCredit.compareTo(product.getPrice()) > 0;
  }

  private boolean isProductOnStock(String productId) {
    return show(productId).getAmount() > 0;
  }
}
