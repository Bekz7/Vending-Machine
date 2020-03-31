package pl.bekz.vendingmachine.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.bekz.vendingmachine.model.dto.CreditDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
    private Integer coinAmount;

    public CreditDto creditsDto(){
        return CreditDto.builder()
                .coinName(coinName)
                .coinValue(coinsValue)
                .coinsAmount(coinAmount)
                .build();
    }
}
