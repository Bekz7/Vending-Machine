package pl.bekz.vendingmachine.repositories;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import pl.bekz.vendingmachine.model.Money;
import pl.bekz.vendingmachine.model.entities.Credit;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class InMemoryCreditRepository implements CreditsRepository {

    @Getter
    private Map<Money, Credit> map = new HashMap<>();

    @Override
    public Credit addNewCredits(Credit credit) {
        requireNonNull(credit);
        map.put(credit.creditsDto().getCoin(), credit);
        return credit;
    }

    @Override
    public void returnCredits(BigDecimal cashToReturn) {

    }

    @Override
    public Credit findById(Money coin) {
        return map.get(coin);
    }

    public BigDecimal checkBalance() {
        final BigDecimal[] balance = {BigDecimal.ZERO};
        map.forEach((coinsType, coins) -> balance[0] = balance[0].add(coinsType.getValue().multiply(BigDecimal.valueOf(coins.creditsDto().getCoinsNumber()))));
        return balance[0];
    }

    @Override
    public Page<Credit> findAll(Pageable pageable) {
        return new PageImpl<>(new ArrayList<>(map.values()), pageable, map.size());
    }

    @Override
    public void clearCoinsBalance() {
        map.clear();
    }

}
