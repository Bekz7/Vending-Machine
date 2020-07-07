package pl.bekz.vendingmachine.machine.facades


import org.springframework.data.domain.PageRequest
import pl.bekz.vendingmachine.infrastructure.exceptions.ItemNotFound
import pl.bekz.vendingmachine.infrastructure.exceptions.ProductSoldOut
import pl.bekz.vendingmachine.infrastructure.SampleProducts
import pl.bekz.vendingmachine.machine.domain.VendingMachineConfiguration
import pl.bekz.vendingmachine.machine.dto.ProductDto
import spock.lang.Specification

import static pl.bekz.vendingmachine.machine.domain.Drinks.PEPSI
import static pl.bekz.vendingmachine.machine.domain.Drinks.REDBULL

class ProductFacadeTest extends Specification implements SampleProducts {
    ProductFacade facade

    void setup() {
        facade = new VendingMachineConfiguration().productFacade()
    }

    private void supplyMachine(ProductDto... products){
        products.each { ProductDto product -> facade.add(product)
        }
    }

    def "As a Vendor I want add the product"(){
        when:
            facade.add(pepsiSamples)
        then:
        pepsiSamples == facade.show(PEPSI.name())
    }

    def "As a Machine I want to change the product amount"(){
        given:
            facade.add(redbulSamples)
            final int amountToChange = -2
            final def productBeforeChange = facade.show(REDBULL.name())
        when:
            final def decreasedProduct = facade.changeAmount(REDBULL.name(), amountToChange)
        then:
            decreasedProduct.amount == productBeforeChange.amount + amountToChange
    }

    def "As a Customer I want to see all available products"(){
        when:
            supplyMachine(pepsiSamples, redbulSamples, cocaColaSamples)
        then:
            !facade.findAllProducts(PageRequest.of(0, 10)).empty
    }

    def "As a Customer I want to be inform that the selected product is unavailable"(){
        given:
            supplyMachine(cocaColaSamples, pepsiSamples)
        when:
            facade.checkIsProductAvailable(REDBULL.name())
        then:
            thrown(ProductSoldOut)
    }

    def "AS a Customer I want to be inform that I pass choose wrong product"(){
        when:
            facade.show("asd")
        then:
            thrown(ItemNotFound)
    }
}
