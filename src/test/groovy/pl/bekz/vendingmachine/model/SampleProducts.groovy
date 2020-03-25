package pl.bekz.vendingmachine.model

import pl.bekz.vendingmachine.model.dto.ProductDto
import groovy.transform.CompileStatic

import static pl.bekz.vendingmachine.ProductFactory.*

@CompileStatic
trait SampleProducts {


    ProductDto pepsiSample = pepsi(4).productDto()
    ProductDto cocaColaSample = cocaCola(6).productDto()
    ProductDto redbullSample = redbull(0).productDto()

}
