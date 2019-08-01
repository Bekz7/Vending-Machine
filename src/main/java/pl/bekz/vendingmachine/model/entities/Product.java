package pl.bekz.vendingmachine.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import pl.bekz.vendingmachine.model.dto.ProductDto;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

  @Id
  private String name;
  private Integer amount;
  private BigDecimal price;

  public ProductDto productDto(){
    return ProductDto.builder()
            .name(name)
            .amount(amount)
            .price(price)
            .build();
  }

}
