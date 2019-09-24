package pl.bekz.vendingmachine.model.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pl.bekz.vendingmachine.model.Money;

@Builder
@Getter
@EqualsAndHashCode
public class CreditDto {
    private Money coin;
    private Integer coinsNumber;
}
