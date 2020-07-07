package pl.bekz.vendingmachine.infrastructure.services

import pl.bekz.vendingmachine.infrastructure.exceptions.CreditNotFound
import pl.bekz.vendingmachine.infrastructure.exceptions.ExactChangeOnly
import pl.bekz.vendingmachine.infrastructure.exceptions.NotEnoughCoins
import pl.bekz.vendingmachine.infrastructure.exceptions.ProductSoldOut
import pl.bekz.vendingmachine.machine.domain.Money
import pl.bekz.vendingmachine.machine.domain.VendingMachineConfiguration
import spock.lang.Specification

import static pl.bekz.vendingmachine.machine.domain.Money.*
import static pl.bekz.vendingmachine.machine.domain.Drinks.*

class CustomerServiceTest extends Specification {
    private CustomerService service

    void setup() {
        service = new VendingMachineConfiguration().customerService()
    }

    private void insertFewCoins(Money... coins){
        coins.each {Money coin -> service.insertCoin(coin.name())}
    }

    def "As a Customer I want to check my balance"() {
        given:
        insertFewCoins(DOLLAR, DIME)

        when:
        def customerBalance = service.customerBalance()

        then:
        0 == (customerBalance <=> 1.1)
    }

    def "As a Customer, I want to be informed about the coins the machine accepts"(){
        when:
        service.insertCoin("wrong")
        then:
        thrown(CreditNotFound)
    }

    def "As a Customer should't buy a product with small amount of cash"() {
        given:
        service.insertCoin(DOLLAR.name())

        when:
        service.buyProduct(PEPSI.name())
        then:
        thrown(NotEnoughCoins)
    }

    def "As a Customer should be informed of the exact change only"() {
        given:
        insertFewCoins(DOLLAR, QUARTER)

        when:
        service.buyProduct(PEPSI.name())

        then:
        thrown(ExactChangeOnly)
    }

    def "As a Customer should be informed of the product out of stock"() {
        given:
        insertFewCoins(DOLLAR, QUARTER)

        when:
        service.buyProduct(REDBULL.name())

        then:
        thrown(ProductSoldOut)
    }

    def "As a Customer should buy a product"() {
        given:
        insertFewCoins(DOLLAR, DIME)
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
}
