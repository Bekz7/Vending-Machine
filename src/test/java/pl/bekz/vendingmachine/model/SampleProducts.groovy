package pl.bekz.vendingmachine.model

import pl.bekz.vendingmachine.model.dto.ProductDto
import groovy.transform.CompileStatic

@CompileStatic
trait SampleProducts {
    ProductDto pepsi = createProductDto("Pepsi", 7, 1.1)
    ProductDto cocaCola = createProductDto("Coca-cola", 2, BigDecimal.ONE)
    ProductDto cocoRise = createProductDto("Coco-rise", 5, BigDecimal.ZERO)

    ProductDto createProductDto(String name, Integer amount, BigDecimal price) {
        return ProductDto.builder()
                .name(name)
                .amount(amount)
                .price(price)
                .build()
    }
}
