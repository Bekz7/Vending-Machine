package pl.bekz.vendingmachine.model

import groovy.transform.CompileStatic
import pl.bekz.vendingmachine.machine.domain.Money
import pl.bekz.vendingmachine.model.dto.CreditDto

@CompileStatic
trait SampleCoins {

//TODO Maybe worth to think of using the CreditFactory against createCoin
    CreditDto dollarSamples = createCoin(Money.DOLLAR, 2)
    CreditDto dimeSamples = createCoin(Money.DIME, 4)

    private static CreditDto createCoin(Money money, int amount) {
        return CreditDto.builder()
                .name(money.name())
                .amount(amount)
                .value(money.value)
                .build()
    }
}
