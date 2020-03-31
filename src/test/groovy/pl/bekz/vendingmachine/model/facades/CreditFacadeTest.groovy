package pl.bekz.vendingmachine.model.facades

import pl.bekz.vendingmachine.model.Money
import pl.bekz.vendingmachine.model.SampleCoins
import pl.bekz.vendingmachine.model.VendingMachineConfiguration
import spock.lang.Specification

import java.util.stream.Stream

import static pl.bekz.vendingmachine.model.Money.DIME
import static pl.bekz.vendingmachine.model.Money.DOLLAR

class CreditFacadeTest extends Specification implements SampleCoins {
    CreditFacade creditFacade


    void setup() {
        creditFacade = new VendingMachineConfiguration().creditFacade()
    }

    private void addCreditsToCustomer(Money... coins){
//        Stream.of(coins).forEach(creditFacade.increaseCustomerBalance(coins))
        for (Money coin: coins){
            creditFacade.increaseCustomerBalance(coin)
        }

    }

    def "As a Machine I want spend the rest to client"(){
        given:
            creditFacade.add(dimeSamples)
            creditFacade.add(dollarSamples)
            addCreditsToCustomer(DOLLAR, DOLLAR, DIME, DIME, DIME, DIME)
        when:
            creditFacade.decreesMachineBalance()
        then:
            creditFacade.checkMachineCoinBalance() == BigDecimal.ZERO

    }

    def "As a Machine I want to change coins amount"(){
        given:
            creditFacade.add(dimeSamples)
            final coinAmountToChange = -2
        when:
            def coinsWithChangedAmount = creditFacade
                    .changeAmount(dimeSamples.coinName, coinAmountToChange)
            creditFacade.add(coinsWithChangedAmount)
        then:
            creditFacade.show(dimeSamples.coinName).coinsAmount == dimeSamples.coinsAmount +
                    coinAmountToChange
    }

    def "As a Vendor I want check Machine balance"(){
        when:
            creditFacade.add(dollarSamples)
        then:
        2.0 == creditFacade.checkMachineCoinBalance()
    }
}
