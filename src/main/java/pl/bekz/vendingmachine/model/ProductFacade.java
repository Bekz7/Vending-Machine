package pl.bekz.vendingmachine.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.bekz.vendingmachine.model.dto.ProductDto;
import pl.bekz.vendingmachine.repositories.CustomerCreditsRepository;
import pl.bekz.vendingmachine.repositories.MachineCreditsRepository;
import pl.bekz.vendingmachine.repositories.ProductRepository;

import java.math.BigDecimal;

import static java.util.Objects.requireNonNull;

public class ProductFacade {
  ProductCreator productCreator;
  CustomerCreditsRepository customerCreditsRepository;
  MachineCreditsRepository machineCreditsRepository;
  ProductRepository productRepository;

  public ProductFacade(
      ProductCreator productCreator,
      CustomerCreditsRepository customerCreditsRepository,
      MachineCreditsRepository machineCreditsRepository,
      ProductRepository productRepository) {
    this.productCreator = productCreator;
    this.customerCreditsRepository = customerCreditsRepository;
    this.machineCreditsRepository = machineCreditsRepository;
    this.productRepository = productRepository;
  }

  public ProductDto add(ProductDto productDto){
      requireNonNull(productDto);
      Product product = productCreator.from(productDto);
      product = productRepository.addNewProduct(product);
      return product.productDto();
  }

  public ProductDto show(String productDto){
      requireNonNull(productDto);
      Product product = productRepository.findOneOrThrow(productDto);
      return product.productDto();
  }

  public Page<ProductDto> findAll(Pageable pageable){
      requireNonNull(pageable);
      return productRepository
              .findAll(pageable)
              .map(Product::productDto);
  }

  public Product refill(String productDto){
      requireNonNull(productDto);
      Product product = productRepository.findById(productDto);
      return productRepository.refill(product.productDto().getName());
  }

  public Integer insertCoin(Money coin){
      requireNonNull(coin);
      return customerCreditsRepository.insertCoin(coin);
  }

  public BigDecimal checkbalance(){
      return customerCreditsRepository.checkCoinsBalance();
  }

  public void returnCoins(){
      customerCreditsRepository.clearCoinsBalance();
  }



}
