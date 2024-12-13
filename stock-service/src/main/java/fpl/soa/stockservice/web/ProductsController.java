package fpl.soa.stockservice.web;

import fpl.soa.stockservice.entities.Product;
import fpl.soa.stockservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


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
    public Product save(@RequestBody @Valid Product request) {
       return productService.save(request);
    }
}
