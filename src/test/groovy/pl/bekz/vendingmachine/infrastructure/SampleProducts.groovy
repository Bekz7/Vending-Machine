package pl.bekz.vendingmachine.infrastructure

import groovy.transform.CompileStatic
import pl.bekz.vendingmachine.machine.dto.ProductDto

import static pl.bekz.vendingmachine.infrastructure.ProductFactory.*

@CompileStatic
trait SampleProducts {
        ProductDto pepsiSamples = pepsi(3).productDto()
        ProductDto cocaColaSamples = cocaCola(2).productDto()
        ProductDto redbulSamples = redbull(0).productDto()
}
