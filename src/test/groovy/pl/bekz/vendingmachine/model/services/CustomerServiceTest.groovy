package pl.bekz.vendingmachine.model.services


import pl.bekz.vendingmachine.exceptions.ExactChangeOnly
import pl.bekz.vendingmachine.exceptions.NotEnoughCoins
import pl.bekz.vendingmachine.exceptions.ProductSoldOut
import pl.bekz.vendingmachine.model.VendingMachineConfiguration
import spock.lang.Specification

import static pl.bekz.vendingmachine.model.Money.*
import static pl.bekz.vendingmachine.model.Drinks.*

class CustomerServiceTest extends Specification {
    private CustomerService service

    void setup() {
        service = new VendingMachineConfiguration().customerService()
    }

    def "As a customer I want to check my balance"() {
        given:
        service.insertCoin(DOLLAR)
        service.insertCoin(DIME)

        when:
        def customerBalance = service.customerBalance()

        then:
        0 == (customerBalance <=> 1.1)
    }

    def "As a Customer should't buy a product with small amount of cash"() {
        given:
        service.insertCoin(DOLLAR)

        when:
        service.buyProduct(PEPSI.name())
        then:
        thrown(NotEnoughCoins)
    }

    def "As a Customer should be informed of the exact change only"() {
        given:
        service.insertCoin(DOLLAR)
        service.insertCoin(QUARTER)

        when:
        service.buyProduct(PEPSI.name())

        then:
        thrown(ExactChangeOnly)
    }

    def "As a Customer should be informed of the product out of stock"() {
        given:
        service.insertCoin(DOLLAR)
        service.insertCoin(QUARTER)

        when:
        service.buyProduct(REDBULL.name())

        then:
        thrown(ProductSoldOut)
    }

    def "As a Customer should buy a product"() {
        given:
        service.insertCoin(DOLLAR)
        service.insertCoin(DIME)
        print(service.customerBalance())

        when:
        service.buyProduct(PEPSI.name())

        then:
        service.customerBalance() == BigDecimal.ZERO
    }

    def "As a Customer I want to saw all available products"(){
        when:
        def availableProduct = service.showAllAvailableProduct()
        then:
        !availableProduct.empty
    }

    def "As a Customer I want get my coin back"(){
        when:
        service.insertCoin(DOLLAR)
        then:
        service.spendTheRestToTheCustomer() == BigDecimal.ZERO
    }
}
