package pl.bekz.vendingmachine.model.services


import spock.lang.Specification

import static pl.bekz.vendingmachine.model.Money.DOLLAR

class CustomerServiceTest extends Specification {
    private CustomerService service

    void setup() {
        service = new CustomerService()
    }

    def "As a buyer should insert a coin"() {
        expect:
        BigDecimal.ONE == service.customerBalance()

        where:
        service.insertCoin(DOLLAR)


    }

    def "BuyProduct"() {
    }

    def "IncreaseCoinAmount"() {
    }
}
