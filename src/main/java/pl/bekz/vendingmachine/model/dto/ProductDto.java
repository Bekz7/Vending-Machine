package pl.bekz.vendingmachine.model.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
@EqualsAndHashCode
public class ProductDto {

  private String name;
  private Integer amount;
  private BigDecimal price;
}
