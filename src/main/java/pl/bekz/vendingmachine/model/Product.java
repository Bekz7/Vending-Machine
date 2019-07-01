package pl.bekz.vendingmachine.model;

import lombok.*;
import pl.bekz.vendingmachine.model.dto.ProductDto;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

  @Id
  private String name;
  private Integer amount;
  private Money price;

  public ProductDto productDto(){
    return ProductDto.builder()
            .name(name)
            .amount(amount)
            .price(price)
            .build();
  }

}
