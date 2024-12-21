package fpl.soa.stockservice.web;

import fpl.soa.stockservice.entities.PageInfo;
import fpl.soa.stockservice.entities.Product;
import fpl.soa.stockservice.service.ProductService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {
    private ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }



    @GetMapping("{size}")
    public PageInfo getProductsPageInfo(@PathVariable int size){
        return this.productService.getProductPageInfo(size);
    }

    @GetMapping("find/{productId}")
    public Product getProductById(@PathVariable String productId){
        return this.productService.getProductById(productId);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product){
        return this.productService.createProduct(product);
    }
    @PutMapping
    public Product update(@RequestBody Product product){
        return this.productService.updateProduct(product);
    }
}
