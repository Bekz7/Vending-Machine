package pl.bekz.vendingmachine.repositories

import pl.bekz.vendingmachine.model.Money
import spock.lang.Specification

class InMemoryCustomerCreditsRepositoryTest extends Specification {


    def "InsertCoin"() {
        given:
        InMemoryCustomerCreditsRepository creditsRepository = new InMemoryCustomerCreditsRepository()
        when:
        creditsRepository.insertCoin(Money.DOLLAR)
        creditsRepository.insertCoin(Money.DOLLAR)
        creditsRepository.insertCoin(Money.DOLLAR)
        creditsRepository.insertCoin(Money.DOLLAR)
        then:
        println (creditsRepository.getCustomerCredits().size())
        creditsRepository.getCustomerCredits().size() > 0
    }

    def "CheckCoinsBalance"() {
    }

    def "ReturnCoins"() {
    }
}
