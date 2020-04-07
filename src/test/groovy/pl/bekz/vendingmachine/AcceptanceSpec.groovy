package pl.bekz.vendingmachine

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.ResultActions
import pl.bekz.vendingmachine.model.Money
import pl.bekz.vendingmachine.model.SampleCoins
import pl.bekz.vendingmachine.model.SampleProducts
import pl.bekz.vendingmachine.model.dto.ProductDto
import pl.bekz.vendingmachine.model.facades.CreditFacade
import pl.bekz.vendingmachine.model.facades.ProductFacade

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static pl.bekz.vendingmachine.ProductFactory.cocaCola
import static pl.bekz.vendingmachine.ProductFactory.pepsi
import static pl.bekz.vendingmachine.ProductFactory.redbull

class AcceptanceSpec extends IntegrationSpec implements SampleProducts, SampleCoins {
    @Autowired
    ProductFacade productFacade
    @Autowired
    CreditFacade creditFacade

    private void addCreditsToCustomer(Money... coins){
        coins.each {Money coin -> creditFacade.increaseCustomerBalance(coin)}
    }

    private void supplyMachine(ProductDto... products){
        products.each { ProductDto product -> facade.add(product)
        }
    }

    def "positive buying scenario"(){
        given: 'inventory has the three drinks "Coca-cola", "Pepsi" and "Redbull"'
            supplyMachine(cocaColaSamples, pepsiSamples, redbulSamples)
        when: 'I go to /products'
            ResultActions getDrinks = mockMvc.perform(get("/products"))
        then: 'I can see all drinks'
            getDrinks.andExpect(status().isOk())
                .andExpect(content().json("""
                {
                    "content": [
                        {"name":"$cocaCola.name","amount":"$cocaCola.amount","price":"$cocaCola.price"},
                        {"name":"$pepsi.name","amount":"$pepsi.amount","price":"$pepsi.price"},
                        {"name":"$redbull.name","amount":"$redbull.amount","price":"$redbull.price"}
                    ]
                }"""))
    }
}
