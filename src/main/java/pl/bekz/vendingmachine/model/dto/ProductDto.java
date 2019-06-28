package pl.bekz.vendingmachine.model.dto;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@EqualsAndHashCode
public class ProductDto {

  private String name;
  private Integer amount;
  private BigDecimal price;

}
