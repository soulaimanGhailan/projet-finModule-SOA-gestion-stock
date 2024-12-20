package fpl.soa.stockservice.web;

import fpl.soa.stockservice.entities.Product;
import fpl.soa.stockservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {
    private ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> findAll() {
        return productService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product save(@RequestBody Product request) {
       return productService.save(request);
    }

    @GetMapping("/id/{id}")
    public Product findById(@PathVariable Long id) {
        return productService.getById(id) ;
    }
}
