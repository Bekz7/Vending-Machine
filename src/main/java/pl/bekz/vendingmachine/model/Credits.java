package pl.bekz.vendingmachine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Credits {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    private ConcurrentHashMap<Money, Integer> credits;
}
