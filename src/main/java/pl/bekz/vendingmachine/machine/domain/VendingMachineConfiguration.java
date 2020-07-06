package pl.bekz.vendingmachine.machine.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.bekz.vendingmachine.machine.domain.entities.Transaction;
import pl.bekz.vendingmachine.machine.facades.CreditFacade;
import pl.bekz.vendingmachine.machine.facades.ProductFacade;
import pl.bekz.vendingmachine.infrastructure.services.CustomerService;
import pl.bekz.vendingmachine.infrastructure.repositories.CreditsRepository;
import pl.bekz.vendingmachine.infrastructure.repositories.InMemoryCreditRepository;
import pl.bekz.vendingmachine.infrastructure.repositories.InMemoryProductRepository;
import pl.bekz.vendingmachine.infrastructure.repositories.ProductRepository;

@Configuration
public class VendingMachineConfiguration {

  public CreditFacade creditFacade() {
    return creditFacade(new InMemoryCreditRepository());
  }

  public CustomerService customerService() {
    return new CustomerService(creditFacade(), productFacade());
  }

  @Bean
  CreditFacade creditFacade(CreditsRepository creditsRepository) {
    CreditCreator creditCreator = new CreditCreator();
    Transaction transaction = new Transaction();
    return new CreditFacade(creditCreator, creditsRepository, transaction);
  }

  public ProductFacade productFacade() {
    return productFacade(new InMemoryProductRepository());
  }

  @Bean
  ProductFacade productFacade(ProductRepository productRepository) {
    ProductCreator productCreator = new ProductCreator();
    return new ProductFacade(productCreator, productRepository);
  }
}
