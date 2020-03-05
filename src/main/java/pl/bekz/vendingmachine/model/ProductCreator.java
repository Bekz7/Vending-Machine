package pl.bekz.vendingmachine.model;

import pl.bekz.vendingmachine.model.dto.ProductDto;
import pl.bekz.vendingmachine.model.entities.Product;

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
