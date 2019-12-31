package pl.bekz.vendingmachine.repositories

import pl.bekz.vendingmachine.model.CreditCreator
import pl.bekz.vendingmachine.model.dto.CreditDto
import pl.bekz.vendingmachine.model.entities.Credit
import spock.lang.Specification

import static pl.bekz.vendingmachine.model.Money.DOLLAR

class InMemoryCreditRepositoryTest extends Specification {

    private InMemoryCreditRepository creditRepository = new InMemoryCreditRepository()
    private CreditCreator creditCreator = new CreditCreator();

    void setup() {
    }

    void cleanup() {
        creditRepository.getMap().clear()
    }

    def "Should insert a coin"(){
        given: "Coin inserted"
        def dollar = CreditDto.builder().coinsNumber(1).coinName(DOLLAR).build()
        when: ""
        Credit credit = creditCreator.from(dollar)
        creditRepository.saveCredit(credit)
        then: ""
        println(creditRepository.findById(DOLLAR))
    }

    def"Should return coins"(){
        given: "Coin inserted"
        def dollar = CreditDto.builder().coinsNumber(1).coin(DOLLAR).build()
        when:""
        Credit credit = creditCreator.from(dollar)
        creditRepository.saveCredit(credit)
        creditRepository.saveCredit(credit)
        println(creditRepository.checkBalance())
        then:
        creditRepository.returnCredits(10)
        println(creditRepository.checkBalance())
    }
}
