package pl.bekz.vendingmachine.model.entities;

import lombok.*;
import pl.bekz.vendingmachine.model.Money;

import javax.persistence.*;

@Entity
@Data
public class Credits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private Money coins;
    @Column(nullable = false)
    private Integer coinsNumber;
}
