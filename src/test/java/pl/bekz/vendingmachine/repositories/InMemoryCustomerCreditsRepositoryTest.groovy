package pl.bekz.vendingmachine.repositories

import pl.bekz.vendingmachine.model.Money
import spock.lang.Specification

class InMemoryCustomerCreditsRepositoryTest extends Specification {


    def "InsertCoin"() {
        given:
        InMemoryCustomerCreditsRepository creditsRepository = new InMemoryCustomerCreditsRepository()
        when:
        creditsRepository.insertCoin(Money.DOLLAR)
        then:
        creditsRepository.getCustomerCredits().get(Money.DOLLAR) == 1
    }

    def "CheckCoinsBalance"() {
        given:
        InMemoryCustomerCreditsRepository creditsRepository = new InMemoryCustomerCreditsRepository()
        creditsRepository.customerCredits.put(Money.QUARTER, 3)
        creditsRepository.checkCoinsBalance()
        println (creditsRepository.balance)
        creditsRepository.customerCredits.put(Money.DOLLAR, 3)
        creditsRepository.checkCoinsBalance()
        println (creditsRepository.balance)
        expect:
        println (creditsRepository.checkCoinsBalance())
        println (creditsRepository.checkCoinsBalance())
        creditsRepository.checkCoinsBalance()
    }

    def "ReturnCoins"() {
    }
}
