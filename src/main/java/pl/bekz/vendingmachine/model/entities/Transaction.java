package pl.bekz.vendingmachine.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class Transaction {

    private Long id;
    private String productName;
    private BigDecimal transactionBalance;

}
