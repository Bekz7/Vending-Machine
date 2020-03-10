package pl.bekz.vendingmachine.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.bekz.vendingmachine.model.entities.Transaction;
import pl.bekz.vendingmachine.model.facades.CreditFacade;
import pl.bekz.vendingmachine.model.facades.ProductFacade;
import pl.bekz.vendingmachine.model.facades.VendingFacade;
import pl.bekz.vendingmachine.repositories.CreditsRepository;
import pl.bekz.vendingmachine.repositories.InMemoryCreditRepository;
import pl.bekz.vendingmachine.repositories.InMemoryProductRepository;
import pl.bekz.vendingmachine.repositories.ProductRepository;

@Configuration
public class VendingMachineConfiguration {

  VendingFacade vendingMachineFacade() {
    return vendingMachineFacade(new InMemoryCreditRepository(), new InMemoryProductRepository());
  }

  CreditFacade creditFacade(){
    return creditFacade(new InMemoryCreditRepository());
  }

  @Bean
  CreditFacade creditFacade(
          CreditsRepository creditsRepository){
    CreditCreator creditCreator = new CreditCreator();
    Transaction transaction = new Transaction();
    return new CreditFacade(creditCreator, creditsRepository, transaction);
  }

  ProductFacade productFacade(){
    return productFacade(new InMemoryProductRepository());
  }

  @Bean
  ProductFacade productFacade(
          ProductRepository productRepository){
    ProductCreator productCreator = new ProductCreator();
    return new ProductFacade(productCreator, productRepository);
  }

  @Bean
  VendingFacade vendingMachineFacade(
      CreditsRepository creditsRepository, ProductRepository productRepository) {
    ProductCreator productCreator = new ProductCreator();
    CreditCreator creditCreator = new CreditCreator();
    Transaction transaction = new Transaction();
    return new VendingFacade(
        productCreator, creditCreator, transaction, creditsRepository, productRepository);
  }
}
