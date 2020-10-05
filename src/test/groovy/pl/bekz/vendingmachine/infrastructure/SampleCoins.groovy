package pl.bekz.vendingmachine.infrastructure

import groovy.transform.CompileStatic
import pl.bekz.vendingmachine.machine.domain.entities.Credit
import pl.bekz.vendingmachine.machine.dto.CreditDto

import static pl.bekz.vendingmachine.infrastructure.CreditFactory.*

@CompileStatic
trait SampleCoins {

    CreditDto dollarSamples = dollar(1).creditsDto()
    CreditDto dimeSamples = quarter(1).creditsDto()
    CreditDto quarterSample = dime(1).creditsDto()
    CreditDto nickerSample = nickel(1).creditsDto()
}
