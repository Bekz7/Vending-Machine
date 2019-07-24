package pl.bekz.vendingmachine.model

import pl.bekz.vendingmachine.exceptions.ProductNotFound
import spock.lang.Specification

class VendingMachineFacadeTest extends Specification implements SampleProducts {

    private VendingMachineFacade facade = new VendingMachineConfiguration().vendingMachineFacade()

    void setup() {
    }

    void cleanup() {
    }

    def "As a Vendor I want add new product"() {
        given:
        facade.add(cocaCola)

        expect:
        facade.show(cocaCola.name) == cocaCola
    }

    def "Should throw exception when asked for product that's not in the system"() {
        when:
        facade.show("something")

        then:
            thrown(ProductNotFound)
    }


}
