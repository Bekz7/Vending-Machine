package pl.bekz.vendingmachine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Credits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ConcurrentHashMap<Money, Integer> credits;
    private BigDecimal balance;

}
