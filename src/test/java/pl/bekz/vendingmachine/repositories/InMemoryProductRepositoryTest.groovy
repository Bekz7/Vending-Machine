package pl.bekz.vendingmachine.repositories

import pl.bekz.vendingmachine.model.Money
import pl.bekz.vendingmachine.model.Product
import pl.bekz.vendingmachine.model.dto.ProductDto
import spock.lang.Specification

import java.util.concurrent.ConcurrentHashMap

class InMemoryProductRepositoryTest extends Specification {

    private InMemoryProductRepository productRepository = new InMemoryProductRepository();

    void cleanup() {
    }

    def "AddNewProduct"() {
        given:
        final String cocaCola = "Coca Cola"
        final int amount = 1
        final Money price = Money.DOLLAR
        ProductDto cola = new ProductDto(cocaCola, amount, price.value)
        when:
        productRepository.addNewProduct(cola)
        then:
        println productRepository.findById(cocaCola)
    }

    def "Refill"() {
        given:
        Product product = productRepository.findById()
    }

    def addNewProduct(String name){
        final String cocaCola = "Coca Cola"
        productRepository.addNewProduct(cocaCola)
    }

    def "FindAll"() {
    }
}
