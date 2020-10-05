package pl.bekz.vendingmachine.machine.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class ProductDto {

  private String name;
  private Integer amount;
  private BigDecimal price;
}
