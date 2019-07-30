package pl.bekz.vendingmachine.repositories

import pl.bekz.vendingmachine.model.Money
import spock.lang.Specification

import java.util.concurrent.ConcurrentHashMap

class InMemoryCustomerCreditsRepositoryTest extends Specification {

    private InMemoryCustomerCreditsRepository creditsRepository = new InMemoryCustomerCreditsRepository()

    def "InsertCoin"() {
        given: "As a Customer I want to insert a coin"
        creditsRepository.insertCoin(Money.DOLLAR)
        expect: "As result We have a dollar"
        creditsRepository.checkBalance() == 1
    }

    def "CheckCoinsBalance"() {
        given: "As a Customer when We paste money"
        creditsRepository.updateCoinBalance(Money.QUARTER, 4)
        expect: "We want our allCredits equals to dollar"
        creditsRepository.checkBalance() == BigDecimal.ONE
    }

    def "ReturnCoins"() {
        given: "As Customer putting money"
        creditsRepository.updateCoinBalance(Money.QUARTER, 4)
        when: "When We trying return ours money"
        creditsRepository.clearCoinsBalance()
        then: "We want theirs return"
        creditsRepository.checkBalance() == 0
    }

    void cleanup() {
        creditsRepository = null
    }

    def "checkIfCorrectMapping"() {
        given: "Some allCredits added"
        BigDecimal credits = 2.90

        when: "We want map ours allCredits to coins"
        creditsRepository.persistCoins(credits)

        then: "Should get map with ours coins equals to inputted allCredits"
        creditsRepository.getAllCredits() == fulfillCreditsMap()
    }

    private static final def fulfillCreditsMap(){
        ConcurrentHashMap<Money, Integer> result = new ConcurrentHashMap<>()
        result.put(Money.DOLLAR, 2)
        result.put(Money.QUARTER, 3)
        result.put(Money.DIME, 1)
        result.put(Money.NICKEL, 1)

        return result
    }
}
