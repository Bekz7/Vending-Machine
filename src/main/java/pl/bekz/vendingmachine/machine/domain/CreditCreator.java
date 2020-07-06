package pl.bekz.vendingmachine.machine.domain;

import pl.bekz.vendingmachine.machine.dto.CreditDto;
import pl.bekz.vendingmachine.machine.domain.entities.Credit;

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
