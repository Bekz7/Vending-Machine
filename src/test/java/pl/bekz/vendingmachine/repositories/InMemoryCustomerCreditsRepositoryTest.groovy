package pl.bekz.vendingmachine.repositories

import pl.bekz.vendingmachine.model.Money
import spock.lang.Specification

class InMemoryCustomerCreditsRepositoryTest extends Specification {

    private InMemoryCustomerCreditsRepository creditsRepository = new InMemoryCustomerCreditsRepository()

    def "InsertCoin"() {
        given: "As a Customer I want to insert a coin"
            creditsRepository.insertCoin(Money.DOLLAR)
        expect: "As result We have a dollar"
            creditsRepository.getCustomerCredits().get(Money.DOLLAR) == 1
    }

    def "CheckCoinsBalance"() {
        given: "As a Customer when We paste money"
        creditsRepository.customerCredits.put(Money.QUARTER, 4)
        expect: "We want our balance equals to dollar"
        creditsRepository.checkCoinsBalance() == BigDecimal.ONE
    }

    def "ReturnCoins"() {
        given: "As Customer putting money"
        creditsRepository.customerCredits.put(Money.QUARTER, 4)
        when: "When We trying return ours money"
        creditsRepository.clearCoinsBalance()
        then: "We want theirs return"
        creditsRepository.customerCredits.size() == 0
    }
    void cleanup() {
        creditsRepository = null
    }
}
