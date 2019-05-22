package pl.bekz.vendingmachine.services;

import org.springframework.stereotype.Service;
import pl.bekz.vendingmachine.exceptions.ProductNotFound;
import pl.bekz.vendingmachine.model.Product;
import pl.bekz.vendingmachine.repositories.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProductsList(){
        return productRepository.findAll();
    }

    public Product addNewProduct(Product product){
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productToChange){
        Product product =  findProductById(id);
        product.setName(productToChange.getName());
        product.setAmount(productToChange.getAmount());
        product.setPrice(productToChange.getPrice());

        return productRepository.save(product);
    }

    public Product refillLProductOnly(Long id, Product productToRefill){
        Product product = findProductById(id);
        product.setAmount(productToRefill.getAmount());

        return productRepository.save(product);
    }

    public void deleteProductById(Long id){
        Product productToDelete = findProductById(id);

        productRepository.delete(productToDelete);
    }

    private Product findProductById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFound("Can' find product by id: " + id));
    }
}
