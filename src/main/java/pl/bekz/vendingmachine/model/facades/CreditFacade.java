package pl.bekz.vendingmachine.model.facades;

import pl.bekz.vendingmachine.model.CreditCreator;
import pl.bekz.vendingmachine.model.dto.CreditDto;
import pl.bekz.vendingmachine.model.entities.Credit;
import pl.bekz.vendingmachine.repositories.CreditsRepository;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class CreditFacade implements VendingMachineFacade<CreditDto> {
    private CreditCreator creditCreator;
    private CreditsRepository creditsRepository;

    public CreditFacade(CreditCreator creditCreator, CreditsRepository creditsRepository) {
        this.creditCreator = creditCreator;
        this.creditsRepository = creditsRepository;
    }

    @Override
    public CreditDto add(CreditDto dto) {
        requireNonNull(dto);
        Credit credit = creditCreator.from(dto);
        creditsRepository.save(credit);
        return credit.creditsDto();
    }


    @Override
    public CreditDto show(String dto) {
        requireNonNull(dto);
        final Credit credit = creditsRepository.findOneOrThrow(dto);
        return credit.creditsDto();
    }

    @Override
    public CreditDto changeAmount(String name, int amount) {
        requireNonNull(name);
        Credit credit = creditsRepository.findOneOrThrow(name);
        final int amountToChange = credit.creditsDto().getCoinsNumber() + amount;
        credit = Credit.builder().coinName(name).coinsNumber(amountToChange).build();
        return credit.creditsDto();
    }

    public BigDecimal checkMachineCoinBalance() {
        BigDecimal[] machineBalance = {BigDecimal.ZERO};
        creditsRepository
                .getCredits()
                .forEach((key, value) -> machineBalance[0] = machineBalance[0].add(
                        value
                                .creditsDto()
                                .getCoinValue()
                                .multiply(BigDecimal.valueOf(value.creditsDto().getCoinsNumber()))));

        return machineBalance[0];
    }

    private void dupa() {
        final List<CreditDto> collect = creditsRepository.getCredits().values().stream()
                .map(Credit::creditsDto).filter(dto -> (dto.getCoinValue().compareTo(getMostValueCoinToReturn()) == 0))
                .collect(Collectors.toList());
    }

    private BigDecimal getMostValueCoinToReturn(BigDecimal ) {
        return creditsRepository.getCredits().values().stream()
                .map(credit -> credit.creditsDto().getCoinValue())
                .filter(coin -> coin.compareTo(transaction.getTransactionBalance()) <= 0)
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
    }

}
