package pl.bekz.vendingmachine

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.ResultActions
import pl.bekz.vendingmachine.infrastructure.SampleCoins
import pl.bekz.vendingmachine.infrastructure.SampleProducts
import pl.bekz.vendingmachine.machine.domain.Money
import pl.bekz.vendingmachine.machine.dto.CreditDto
import pl.bekz.vendingmachine.machine.dto.ProductDto
import pl.bekz.vendingmachine.machine.facades.CreditFacade
import pl.bekz.vendingmachine.machine.facades.ProductFacade

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static pl.bekz.vendingmachine.machine.domain.Money.*

class AcceptanceSpec extends IntegrationSpec implements SampleProducts, SampleCoins {
    @Autowired
    ProductFacade productFacade
    @Autowired
    CreditFacade creditFacade

    private void addCredits(CreditDto... coins){
        coins.each {CreditDto coin -> creditFacade.add(coin) && creditFacade.increaseCustomerBalance(Money.valueOf(coin.name))}
    }

    private void supplyMachine(ProductDto... products){
        products.each { ProductDto product -> productFacade.add(product)
        }
    }

    def "Positive buying scenario"(){
        given:
            supplyMachine(cocaColaSamples, pepsiSamples, redbulSamples)
        when: 'I go to /products'
            ResultActions getDrinks = mockMvc.perform(get("/products"))
        then: 'I can see all drinks'
            getDrinks
                    .andExpect(status().isOk())
                    .andExpect(content().contentType("application/json;charset=UTF-8"))
                    .andExpect(content().json("""
                        {"content":[
                            {"name":"$cocaColaSamples.name","amount":$cocaColaSamples.amount,"price":$cocaColaSamples.price},
                            {"name":"$redbulSamples.name","amount":$redbulSamples.amount,"price":$redbulSamples.price},
                            {"name":"$pepsiSamples.name","amount":$pepsiSamples.amount,"price":$pepsiSamples.price}
                        ],
                        "pageable":
                            {"sort":
                                {"unsorted":true,"sorted":false,"empty":true},
                                "offset":0,"pageSize":10,"pageNumber":0,"paged":true,"unpaged":false},
                                "last":true,"totalElements":3,"totalPages":1,"first":true,"numberOfElements":3,
                                "sort":{"unsorted":true,"sorted":false,"empty":true},"number":0,"size":10,"empty":false}"""))
        when: 'I insert the coin'
            ResultActions insertCoin = mockMvc.perform(put("/insert/{coinName}", DOLLAR.toString()))
        then: 'Should be accepted by machine'
            insertCoin.andExpect(status().isAccepted())
                      .andExpect(content().string(DOLLAR.value.toString()))
        when: 'I want to check my balance'
            addCredits(dimeSamples, quarterSample)
            ResultActions checkBalance = mockMvc.perform(get("/balance"))
        then: 'I should see my current balance'
            checkBalance.andExpect(status().isOk())
                        .andExpect(content().string(DOLLAR.value.add(DIME.value).add(QUARTER.value).toString()))
        when: 'I want to buy a drink'
            ResultActions buyDrink = mockMvc.perform(get("/buy/{productName}", cocaColaSamples.name))
        then: 'I should be able to do that'
            buyDrink.andExpect(status().isOk())
                    .andExpect(content().string("true"))
        when: 'I insert the coin again'
            insertCoin
            ResultActions refund = mockMvc.perform(get("/refund"))
        then: 'I want to refund if I resign to buy'
            refund.andExpect(status().isOk())
                  .andExpect(content().string("0"))
    }

    def "Vendor " (){
        when:
            ResultActions refillProduct = mockMvc.perform(put("/vendor/refill/{productName}", redbulSamples.name))
            def redbull = productFacade.show(redbulSamples.name)
        then:
            refillProduct.andExpect(status().isAccepted())
                         .andExpect(content().json("""
                            {
                                "name": "$redbull.name",
                                "amount": $redbull.amount,
                                "price": $redbull.price
                            }
                         """))
        when:
            ResultActions machineBalance = mockMvc.perform(get("/vendor/balance"))
            addCredits(nickerSample)
        then:
            machineBalance.andExpect(status().isOk())
                          .andExpect(content().string(nickerSample.value.toString()))

    }

}
