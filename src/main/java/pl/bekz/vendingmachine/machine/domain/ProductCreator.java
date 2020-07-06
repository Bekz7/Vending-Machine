package pl.bekz.vendingmachine.machine.domain;

import pl.bekz.vendingmachine.machine.dto.ProductDto;
import pl.bekz.vendingmachine.machine.domain.entities.Product;

import static java.util.Objects.requireNonNull;

public class ProductCreator {
    public Product from(ProductDto productDto){
        requireNonNull(productDto);
        return Product.builder()
                .name(productDto.getName())
                .amount(productDto.getAmount())
                .price(productDto.getPrice())
                .build();
    }
}
