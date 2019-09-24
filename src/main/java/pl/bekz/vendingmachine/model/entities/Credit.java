package pl.bekz.vendingmachine.model.entities;

import lombok.*;
import pl.bekz.vendingmachine.model.Money;
import pl.bekz.vendingmachine.model.dto.CreditDto;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Credit {

    @Id
    @Column(unique = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private Money coins;
    @Column(nullable = false)
    private Integer coinsNumber;

    public CreditDto creditsDto(){
        return CreditDto.builder()
                .coin( coins )
                .coinsNumber( coinsNumber )
                .build();
    }
}
