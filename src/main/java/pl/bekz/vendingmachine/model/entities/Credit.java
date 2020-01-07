package pl.bekz.vendingmachine.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.bekz.vendingmachine.model.dto.CreditDto;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Credit {

    @Id
    @Column(unique = true, nullable = false)
    private String coinName;
    @Column(nullable = false)
    private BigDecimal coinsValue;
    @Column(nullable = false)
    private Integer coinsNumber;

    public CreditDto creditsDto(){
        return CreditDto.builder()
                .coinName(coinName)
                .coinValue(coinsValue)
                .coinsNumber(coinsNumber)
                .build();
    }
}
