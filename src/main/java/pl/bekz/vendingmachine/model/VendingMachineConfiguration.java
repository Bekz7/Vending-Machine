package pl.bekz.vendingmachine.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.bekz.vendingmachine.model.entities.Transaction;
import pl.bekz.vendingmachine.repositories.*;

@Configuration
public class VendingMachineConfiguration {

  VendingMachineFacade vendingMachineFacade() {
    return vendingMachineFacade(new InMemoryCreditRepository(), new InMemoryProductRepository());
  }

  @Bean
  VendingMachineFacade vendingMachineFacade(
      CreditsRepository creditsRepository, ProductRepository productRepository) {
    ProductCreator productCreator = new ProductCreator();
    CreditCreator creditCreator = new CreditCreator();
    Transaction transaction = new Transaction();
    return new VendingMachineFacade(
        productCreator, creditCreator, transaction, creditsRepository, productRepository);
  }
}
