package pl.bekz.vendingmachine.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {

    private String productName = "";
    private BigDecimal transactionBalance = BigDecimal.ZERO;
    private Map<String, Integer> coinToReturn = new ConcurrentHashMap<>();

}
