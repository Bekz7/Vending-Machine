package pl.bekz.vendingmachine.model

import groovy.transform.CompileStatic
import pl.bekz.vendingmachine.model.Money
import pl.bekz.vendingmachine.model.dto.CreditDto

@CompileStatic
trait SampleCoins {


    CreditDto dollarSamples = createCoin(Money.DOLLAR, 2)
    CreditDto dimeSamples = createCoin(Money.DIME, 4)

    private static CreditDto createCoin(Money money, int amount) {
        return CreditDto.builder()
                .coinName(money.name())
                .coinsAmount(amount)
                .coinValue(money.value)
                .build()
    }
}
