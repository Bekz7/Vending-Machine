package pl.bekz.vendingmachine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.bekz.vendingmachine.model.Product;
import pl.bekz.vendingmachine.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/service")
public class ProductManagementController {

    private ProductService productService;

    @Autowired
    public ProductManagementController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts(){
        return productService.getProductsList();
    }

    @PostMapping("/addNewProduct")
    public Product addNewProduct(Product product){
        return productService.addNewProduct(product);
    }

    @PutMapping("/updateProduct/{id}")
    public Product updateProduct(Product product, @PathVariable Long id){
        return productService.updateProduct(id, product);
    }

    @PutMapping("/refill/{id}")
    public Product refillProduct(Product product, @PathVariable Long id){
        return productService.refilLProductOnly(id, product);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProductById(id);
    }



}
