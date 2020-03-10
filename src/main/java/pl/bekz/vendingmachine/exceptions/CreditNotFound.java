package pl.bekz.vendingmachine.exceptions;

import java.math.BigDecimal;

public class CreditNotFound extends RuntimeException {

    public CreditNotFound(BigDecimal CreditValue) {
        super("Credit with value " + CreditValue + " not found", null, false , false);
    }
}
