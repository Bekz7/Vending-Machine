package pl.bekz.vendingmachine.model;

import pl.bekz.vendingmachine.model.dto.CreditDto;
import pl.bekz.vendingmachine.model.entities.Credit;

import static java.util.Objects.requireNonNull;

public class CreditCreator {
    public Credit from(CreditDto creditDto){
        requireNonNull(creditDto);
        return Credit.builder()
                .name(creditDto.getName())
                .value(creditDto.getValue())
                .amount(creditDto.getAmount())
                .build();
    }
}
