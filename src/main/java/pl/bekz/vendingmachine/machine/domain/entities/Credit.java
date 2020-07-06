package pl.bekz.vendingmachine.machine.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.bekz.vendingmachine.machine.dto.CreditDto;

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
    private String name;
    @Column(nullable = false)
    private BigDecimal value;
    @Column(nullable = false)
    private Integer amount;

    public CreditDto creditsDto(){
        return CreditDto.builder()
                .name(name)
                .value(value)
                .amount(amount)
                .build();
    }
}
