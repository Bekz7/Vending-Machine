package pl.bekz.vendingmachine.model.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
@EqualsAndHashCode
public class CreditDto {
    private String name;
    private BigDecimal value;
    private Integer amount;
}
