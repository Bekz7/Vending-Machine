package pl.bekz.vendingmachine.model.entities;

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

  private String productName = "";
  private BigDecimal customerBalance = BigDecimal.ZERO;
}