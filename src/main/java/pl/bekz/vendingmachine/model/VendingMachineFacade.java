package pl.bekz.vendingmachine.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import pl.bekz.vendingmachine.exceptions.ExactChangeOnly;
import pl.bekz.vendingmachine.exceptions.NotEnoughCoins;
import pl.bekz.vendingmachine.exceptions.ProductSoldOut;
import pl.bekz.vendingmachine.model.dto.CreditDto;
import pl.bekz.vendingmachine.model.dto.ProductDto;
import pl.bekz.vendingmachine.model.entities.Credit;
import pl.bekz.vendingmachine.model.entities.Product;
import pl.bekz.vendingmachine.model.entities.Transaction;
import pl.bekz.vendingmachine.repositories.CreditsRepository;
import pl.bekz.vendingmachine.repositories.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static pl.bekz.vendingmachine.model.entities.Credit.builder;

@Transactional
public class VendingMachineFacade {
    private ProductCreator productCreator;
    private CreditCreator creditCreator;
    private Transaction transaction;
    private CreditsRepository creditsRepository;
    private ProductRepository productRepository;
    private List<BigDecimal> temp = new ArrayList<>();

    public VendingMachineFacade(
            ProductCreator productCreator,
            CreditCreator creditCreator,
            Transaction transaction,
            CreditsRepository creditsRepository,
            ProductRepository productRepository) {
        this.productCreator = productCreator;
        this.creditCreator = creditCreator;
        this.transaction = transaction;
        this.creditsRepository = creditsRepository;
        this.productRepository = productRepository;
    }

    public ProductDto add(ProductDto productDto) {
        requireNonNull(productDto);
        Product product = productCreator.from(productDto);
        product = productRepository.save(product);
        return product.productDto();
    }

    public ProductDto show(String productDto) {
        requireNonNull(productDto);
        Product product = productRepository.findOneOrThrow(productDto);
        return product.productDto();
    }

    public Page<ProductDto> findAllProducts(Pageable pageable) {
        requireNonNull(pageable);
        return productRepository.findAll(pageable).map(Product::productDto);
    }

    public void saveIncreaseProductAmount(String productName) {
        requireNonNull(productName);
        add(increaseAmount(productName));
    }

    private ProductDto increaseAmount(String productName) {
        requireNonNull(productName);
        return changeProductAmount(productName, 1);
    }

    public void saveDecreasedProductAmount(String productName) {
        requireNonNull(productName);
        add(decreaseAmount(productName));
    }

    private ProductDto decreaseAmount(String productName) {
        requireNonNull(productName);
        return changeProductAmount(productName, -1);
    }

    private ProductDto changeProductAmount(String name, int amount) {
        requireNonNull(name);
        Product product = productRepository.findOneOrThrow(name);
        final int amountToChange = product.productDto().getAmount() + amount;
        product = Product.builder().name(name).amount(amountToChange).build();
        return product.productDto();
    }

    public void storeCoin(Money coin){
        add(insertCoin(coin));
        storeCustomerBalance(coin);
    }

    private CreditDto insertCoin(Money coin) {
        requireNonNull(coin);
        final int creditAmount = changeCreditAmount(coin.getCoinName(), 1);
        return builder().coinName(coin.getCoinName()).coinsValue(coin.getValue()).coinsNumber(creditAmount).build().creditsDto();
    }

    private void storeCustomerBalance(Money coin){
        transaction.setTransactionBalance(coin.getValue().add(transaction.getTransactionBalance()));
    }

    private CreditDto add(CreditDto dto) {
        requireNonNull(dto);
        Credit credit = creditCreator.from(dto);
        creditsRepository.save(credit);
        return credit.creditsDto();
    }

    public CreditDto showCredit(String creditDto) {
        requireNonNull(creditDto);
        final Credit credit = creditsRepository.findOneOrThrow(creditDto);
        return credit.creditsDto();
    }

    private int changeCreditAmount(String name, int amount) {
        requireNonNull(name);
        Credit credit = creditsRepository.findOneOrThrow(name);
        return credit.creditsDto().getCoinsNumber() + amount;
    }

    public BigDecimal checkCustomerBalance() {
        return transaction.getTransactionBalance();
    }

    public void returnCustomerCoins() {
        transaction.setTransactionBalance(BigDecimal.ZERO);
    }

    public void buyProduct(String productName) {
        requireNonNull(productName);

        checkIsProductAvailable(productName);
        checkIfCoinEnough(productName);
        checkIfExactChangeOnly(productName);
        saveDecreasedProductAmount(productName);
        transaction.setTransactionBalance(clientBalanceAfterBuying(productName));
    }


    private BigDecimal clientBalanceAfterBuying(String productId) {
        BigDecimal clientBalance = transaction.getTransactionBalance();
        BigDecimal price = show(productId).getPrice();
        return clientBalance.subtract(price);
    }

    private void checkIsProductAvailable(String productId) {
        if (!isProductInStock(productId)) {
            throw new ProductSoldOut(productId);
        }
    }

    private void checkIfCoinEnough(String productId) {
        if (!haveEnoughCredit(productId)) {
            throw new NotEnoughCoins();
        }
    }

    private boolean haveEnoughCredit(String productId) {
        return transaction.getTransactionBalance().compareTo(show(productId).getPrice()) > 0;
    }

    private boolean isProductInStock(String productId) {
        return show(productId).getAmount() > 0;
    }

    private void checkIfExactChangeOnly(String productName) {
        if (exactChangeOnly(productName)) {
            throw new ExactChangeOnly();
        }
    }

    public boolean exactChangeOnly(String productName) {
        return BigDecimal.ZERO.compareTo(returnRestMoney(clientBalanceAfterBuying(productName))) != 0;
    }

    private BigDecimal returnRestMoney(BigDecimal restAfterBuying) {
        while (BigDecimal.ZERO.compareTo(restAfterBuying) > 0) {
            restAfterBuying = restAfterBuying.subtract(getMostValueCoinTReturn());
        }
        return restAfterBuying;
    }

    private void dasd(String productName){
        final BigDecimal clientBalanceMinusProduct = transaction.getTransactionBalance().subtract(clientBalanceAfterBuying(productName));
        while (clientBalanceMinusProduct)
    }
//TODO find name for arg.
    private BigDecimal getMostValueCoinTReturn(BigDecimal ) {
        return creditsRepository.getCredits().values().stream()
                .map(credit -> credit.creditsDto().getCoinValue())
                .filter(coin -> coin.compareTo(transaction.getTransactionBalance()) <= 0)
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal checkMachineCoinBalance() {
        BigDecimal[] machineBalance = {BigDecimal.ZERO};
        creditsRepository
                .getCredits()
                .forEach(
                        (key, value) ->
                                machineBalance[0] =
                                        machineBalance[0].add(
                                                value
                                                        .creditsDto()
                                                        .getCoinValue()
                                                        .multiply(BigDecimal.valueOf(value.creditsDto().getCoinsNumber()))));

        return machineBalance[0];
    }

    public void WithdrawMachineDeposit() {
        creditsRepository.deleteAll();
    }
}
