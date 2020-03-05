package pl.bekz.vendingmachine.model.facades;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.bekz.vendingmachine.exceptions.ProductSoldOut;
import pl.bekz.vendingmachine.model.ProductCreator;
import pl.bekz.vendingmachine.model.dto.ProductDto;
import pl.bekz.vendingmachine.model.entities.Product;
import pl.bekz.vendingmachine.repositories.ProductRepository;

import static java.util.Objects.requireNonNull;

public class ProductFacade implements VendingMachineFacade<ProductDto> {
    private ProductCreator productCreator;
    private ProductRepository productRepository;

    public ProductFacade(
            ProductCreator productCreator,
            ProductRepository productRepository) {
        this.productCreator = productCreator;
        this.productRepository = productRepository;
    }


    @Override
    public ProductDto add(ProductDto productDto) {
        requireNonNull(productDto);
        Product product = productCreator.from(productDto);
        product = productRepository.save(product);
        return product.productDto();
    }

    @Override
    public ProductDto show(String dto) {
        requireNonNull(dto);
        Product product = productRepository.findOneOrThrow(dto);
        return product.productDto();
    }

    @Override
    public ProductDto changeAmount(String name, int amount) {
        requireNonNull(name);
        Product product = productRepository.findOneOrThrow(name);
        final int amountToChange = product.productDto().getAmount() + amount;
        product = Product.builder().name(name).amount(amountToChange).build();
        return product.productDto();
    }

    public Page<ProductDto> findAllProducts(Pageable pageable) {
        requireNonNull(pageable);
        return productRepository.findAll(pageable).map(Product::productDto);
    }

    public void checkIsProductAvailable(String productId) {
        if (!isProductInStock(productId)) {
            throw new ProductSoldOut(productId);
        }
    }

    private boolean isProductInStock(String productId) {
        return show(productId).getAmount() > 0;
    }
}
