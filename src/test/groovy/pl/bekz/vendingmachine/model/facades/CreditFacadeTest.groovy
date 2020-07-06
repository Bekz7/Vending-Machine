package pl.bekz.vendingmachine.model.facades

import pl.bekz.vendingmachine.infrastructure.exceptions.ExactChangeOnly
import pl.bekz.vendingmachine.infrastructure.exceptions.NotEnoughCoins
import pl.bekz.vendingmachine.machine.domain.Money
import pl.bekz.vendingmachine.machine.facades.CreditFacade
import pl.bekz.vendingmachine.model.SampleCoins
import pl.bekz.vendingmachine.machine.domain.VendingMachineConfiguration
import spock.lang.Specification

import static pl.bekz.vendingmachine.machine.domain.Money.DIME
import static pl.bekz.vendingmachine.machine.domain.Money.DOLLAR

class CreditFacadeTest extends Specification implements SampleCoins {
    CreditFacade creditFacade


    void setup() {
        creditFacade = new VendingMachineConfiguration().creditFacade()
    }

    private void addCreditsToCustomer(Money... coins){
        coins.each {coin -> creditFacade.increaseCustomerBalance(coin)}
    }

    def "As a Machine I want spend the rest to the Customer"(){
        given:
            creditFacade.add(dimeSamples)
            creditFacade.add(dollarSamples)
            addCreditsToCustomer(DOLLAR, DOLLAR, DIME, DIME, DIME, DIME)
        when:
            creditFacade.decreesMachineBalance()
        then:
            creditFacade.checkMachineCoinBalance() == BigDecimal.ZERO
    }

    def "As a Machine, I want to check if Customer has enough money to make the transaction"(){
        given:
            addCreditsToCustomer(DIME)
        when:
            creditFacade.checkIfCoinEnough(DOLLAR.value)
        then:
            thrown(NotEnoughCoins)
    }

    def "As a Machine I want to change coins amount"(){
        given:
            creditFacade.add(dimeSamples)
            final coinAmountToChange = -2
        when:
            def coinsWithChangedAmount = creditFacade
                    .changeAmount(dimeSamples.name, coinAmountToChange)
            creditFacade.add(coinsWithChangedAmount)
        then:
            creditFacade.show(dimeSamples.name).amount == dimeSamples.amount +
                    coinAmountToChange
    }

    def "As a Vendor I want check the Machine balance"(){
        when:
            creditFacade.add(dollarSamples)
        then:
        2.0 == creditFacade.checkMachineCoinBalance()
    }

    def "As a Customer I want to be inform of the exact change only"(){
        given:
            creditFacade.add(dimeSamples)
            addCreditsToCustomer(DOLLAR)
        when:
            creditFacade.checkIfExactChangeOnly(creditFacade.checkCustomerBalance())
        then:
            thrown(ExactChangeOnly)
    }

    def "As a Machine I want to save a Customer money after transaction"(){
        given:
            addCreditsToCustomer(DOLLAR, DOLLAR)
        when:
            creditFacade.decreesCustomerBalance(DOLLAR.value)
        then:
            creditFacade.checkCustomerBalance() == DOLLAR.value
    }


    def "As a Machine I want to withdraw the balance to the Customer"(){
        given:
            addCreditsToCustomer(DOLLAR, DOLLAR)
        when:
            creditFacade.resetCustomerBalance()
        then:
            creditFacade.checkCustomerBalance() == BigDecimal.ZERO
    }

    def "As a Machine I want to withdraw the balance"(){
        given:
            creditFacade.add(dollarSamples)
        when:
            creditFacade.withdrawMachineDeposit()
        then:
            creditFacade.checkMachineCoinBalance() == BigDecimal.ZERO
    }
}
