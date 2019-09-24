package pl.bekz.vendingmachine.repositories

import pl.bekz.vendingmachine.model.Money
import spock.lang.Specification

class InMemoryCreditRepositoryTest extends Specification {

    private InMemoryCreditRepository creditRepository = new InMemoryCreditRepository()

    void setup() {
    }

    void cleanup() {
    }

    def "test"(){
        expect: "nie wiem"
        creditRepository.checkBalance()
    }


//    def "InsertCoin"(){
//        given: "As a Customer I want to insert a coin"
//        expect: "Dollar added"
//        println (credit.CreditsDto.builder().coins(Money.DOLLAR).coinsNumber(1).build());
////        creditRepository.addNewCredits(credit.CreditsDto.builder().coins(Money.DOLLAR).coinsNumber(1).build())
//    }

}
