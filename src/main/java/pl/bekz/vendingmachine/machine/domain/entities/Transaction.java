package pl.bekz.vendingmachine.machine.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {

  private BigDecimal customerBalance = BigDecimal.ZERO;
}
