package pl.bekz.vendingmachine.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {

    private String productName = "";
    private BigDecimal customerBalance = BigDecimal.ZERO;
    private List<String> coinToReturn;
}
