package pl.bekz.vendingmachine.services

import pl.bekz.vendingmachine.model.Money
import spock.lang.Specification

class CustomerBalanceServiceTest extends Specification {

    def CustomerBalanceService sut = new CustomerBalanceService()

    void setup() {
    }

    void cleanup() {
        sut.returnCoins()
    }

    def "Customer should insert a coin in Vending Machine "(){
        given: "Customer balance"
            sut.getCostumerBalance() == BigDecimal.ZERO
        when: "Customer insert a coin"
            sut.insertCoin(Money.DOLLAR)
        then: "Should have 1 dollar balance"
            sut.getCostumerBalance() == 1
    }


}
