package pl.bekz.vendingmachine.model

import pl.bekz.vendingmachine.model.dto.ProductDto
import spock.lang.Specification

class ProductFacadeTest extends Specification {

    private ProductFacade facade = new ProductFacade()
    void setup() {
    }

    void cleanup() {
    }

    def "As a Producer I want add new product"(){
        given:"New product"
        final String cocaCola = "Coca Cola"
        final int amount = 1
        final Money price = Money.DOLLAR
        ProductDto cola = new ProductDto(cocaCola, amount, price.value)
        when:
        facade.add(cola)
        then:"Should "

    }


}
