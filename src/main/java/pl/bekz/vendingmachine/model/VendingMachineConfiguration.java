package pl.bekz.vendingmachine.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.bekz.vendingmachine.repositories.*;
/*
@Configuration
public class VendingMachineConfiguration {

    @Bean
    VendingMachineFacade vendingMachineFacade() {
        return vendingMachineFacade(
                new InMemoryCreditRepository(),
                new InMemoryProductRepository());
    }

    private VendingMachineFacade vendingMachineFacade(
            CreditsRepository creditsRepository,
            ProductRepository productRepository) {
        ProductCreator productCreator = new ProductCreator();
        return new VendingMachineFacade( productCreator,
                creditsRepository,
                productRepository );
    }
}
*/