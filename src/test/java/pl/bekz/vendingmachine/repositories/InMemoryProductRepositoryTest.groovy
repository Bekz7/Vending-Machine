package pl.bekz.vendingmachine.repositories

import spock.lang.Specification

class InMemoryProductRepositoryTest extends Specification {

    private InMemoryProductRepository productRepository = InMemoryProductRepository();

    void cleanup() {
    }

    def "AddNewProduct"() {
        given:
        String cocaCola = "Coca Cola"
        when:
        productRepository.addNewProduct(cocaCola)
        then:
        productRepository.findById(cocaCola).equals("Coca Cola")
    }

    def "Refill"() {
    }

    def "FindAll"() {
    }
}
