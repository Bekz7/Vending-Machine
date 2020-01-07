package pl.bekz.vendingmachine.model

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import pl.bekz.vendingmachine.exceptions.ExactChangeOnly
import pl.bekz.vendingmachine.exceptions.NotEnoughCoins
import pl.bekz.vendingmachine.exceptions.ProductNotFound
import pl.bekz.vendingmachine.model.dto.ProductDto
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

    def "Should find all products"() {
        given: "As a Vendor I want add some product"
        facade.add(cocaCola)
        facade.add(cocoRise)
        facade.add(pepsi)

        when: "When client want to see my all products"
        Page<ProductDto> foundProduct = facade.findAllProducts(new PageRequest(0, 10))

        then: "Should see all machine products"
        foundProduct.contains(cocaCola)
        foundProduct.contains(cocoRise)
        foundProduct.contains(pepsi)
    }

    def "Should refill chosen product"() {
        given: "As a Vendor I want add product"
        facade.add(cocaCola)

        when: "I want want refill"
        facade.refill(cocaCola.getName())

        then: "Should get more amount of refiled product"
        facade.show(cocaCola.getName()).amount == 3
    }

    def "As a Customer should insert coin"() {
        given:
        facade.insertCoin(Money.DOLLAR)

        expect:
        println(facade.checkCustomerBalance())
        println(facade.checkMachineCoinBalance())

        facade.checkCustomerBalance() == Money.DOLLAR.value
    }

    def "As a Customer I want return all my coins"() {
        given:
        facade.insertCoin(Money.DOLLAR)

        when:
        facade.returnCustomerCoins()

        then:
        facade.checkCustomerBalance() == BigDecimal.ZERO
    }

    def "After buying product should decrease product amount"() {
        given:
        facade.add(cocaCola)
        facade.insertCoin(Money.DOLLAR)
        facade.insertCoin(Money.DOLLAR)

        when:
        println(facade.checkCustomerBalance())
        println(facade.checkMachineCoinBalance())
        facade.buyProduct(cocaCola.getName())

        then:
        facade.show(cocaCola.getName()).amount < cocaCola.getAmount()
        println(facade.checkCustomerBalance())
        println(facade.checkMachineCoinBalance())
    }

    def "Should throw not enough coin exception"() {
        given:
        facade.add(cocaCola)

        when:
        facade.buyProduct(cocaCola.getName())

        then:
        thrown(NotEnoughCoins)
    }

    def "Should check customer balance"() {
        given:
        facade.insertCoin(Money.DOLLAR)
        facade.insertCoin(Money.DOLLAR)

        when:
        def customerBalance = facade.checkCustomerBalance()

        then:
        customerBalance == BigDecimal.valueOf(2)
    }

    def "As Vendor should be able to check machine actual balance"(){
        given:
        facade.insertCoin(Money.DOLLAR)
        facade.insertCoin(Money.QUARTER)

        when:
        def machineBalance = facade.checkMachineCoinBalance()

        then:
        machineBalance == BigDecimal.valueOf(1.25)
    }

    def"Should check most value coin from machine balance"(){
        given:
        facade.insertCoin(Money.DOLLAR)
        facade.insertCoin(Money.QUARTER)

        when:
        def mostValueCoin = facade.mostValueCoin

        then:
        mostValueCoin == BigDecimal.ONE
    }

    def "Machine should inform exact change only"() {
        given:
        facade.add(pepsi)
        facade.insertCoin(Money.DOLLAR)
        facade.insertCoin(Money.QUARTER)
        println(facade.checkMachineCoinBalance())
        println(facade.checkCustomerBalance())

        when:
        facade.buyProduct(pepsi.getName())

        then:
        thrown(ExactChangeOnly)
    }


}
