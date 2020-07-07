package pl.bekz.vendingmachine

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.ResultActions
import pl.bekz.vendingmachine.machine.domain.Money
import pl.bekz.vendingmachine.infrastructure.SampleCoins
import pl.bekz.vendingmachine.infrastructure.SampleProducts
import pl.bekz.vendingmachine.model.dto.ProductDto
import pl.bekz.vendingmachine.machine.facades.CreditFacade
import pl.bekz.vendingmachine.machine.facades.ProductFacade

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AcceptanceSpec extends IntegrationSpec implements SampleProducts, SampleCoins {
    @Autowired
    ProductFacade productFacade
    @Autowired
    CreditFacade creditFacade

    private void addCreditsToCustomer(Money... coins){
        coins.each {Money coin -> creditFacade.increaseCustomerBalance(coin)}
    }

    private void supplyMachine(ProductDto... products){
        products.each { ProductDto product -> productFacade.add(product)
        }
    }

    def "positive buying scenario"(){
        given:
            supplyMachine(cocaColaSamples, pepsiSamples, redbulSamples)
        when: 'I go to /products'
            ResultActions getDrinks = mockMvc.perform(get("/products"))
        then: 'I can see all drinks'
            getDrinks.andExpect(status().isOk())
                .andExpect(content().json("""
                {
                    "content": [
                        {"name":"$cocaColaSamples.name","amount":"$cocaColaSamples.amount","price":"$cocaColaSamples.price"},
                        {"name":"$pepsiSamples.name","amount":"$pepsiSamples.amount","price":"$pepsiSamples.price"},
                        {"name":"$redbulSamples.name","amount":"$redbulSamples.amount","price":"$redbulSamples.price"}
                    ]
                }"""))
    }
}
