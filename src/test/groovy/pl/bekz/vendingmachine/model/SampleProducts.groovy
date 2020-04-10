package pl.bekz.vendingmachine.model

import groovy.transform.CompileStatic
import pl.bekz.vendingmachine.model.dto.ProductDto

import static pl.bekz.vendingmachine.ProductFactory.*

@CompileStatic
trait SampleProducts {
        ProductDto pepsiSamples = pepsi(3).productDto()
        ProductDto cocaColaSamples = cocaCola(2).productDto()
        ProductDto redbulSamples = redbull(0).productDto()
}
