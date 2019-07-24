package pl.bekz.vendingmachine.model

import pl.bekz.vendingmachine.model.dto.ProductDto
import groovy.transform.CompileStatic

@CompileStatic
trait SampleProducts {
    ProductDto pepsi = createProductDto("Pepsi", 7, BigDecimal.ONE)
    ProductDto cocaCola = createProductDto("Coca-cola", 2, BigDecimal.ONE)
    ProductDto CocoRise = createProductDto("Coco-rise", 5, 1.5)

    private static ProductDto createProductDto(String name, Integer amount, BigDecimal price) {
        return ProductDto.builder()
                .name(name)
                .amount(amount)
                .price(price)
                .build()
    }
}
