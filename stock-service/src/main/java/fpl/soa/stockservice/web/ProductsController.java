package fpl.soa.stockservice.web;

import fpl.soa.stockservice.entities.PageInfo;
import fpl.soa.stockservice.entities.Product;
import fpl.soa.stockservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*")
public class ProductsController {

    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get paginated product information", description = "Retrieve paginated information for products based on the specified page size.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page information retrieved successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid page size.")
    })
    @GetMapping("{size}")
    public PageInfo getProductsPageInfo(@PathVariable int size) {
        return this.productService.getProductPageInfo(size);
    }

    @Operation(summary = "Find a product by ID", description = "Retrieve detailed information about a specific product by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully."),
            @ApiResponse(responseCode = "404", description = "Product not found.")
    })
    @GetMapping("find/{productId}")
    public Product getProductById(@PathVariable String productId) {
        return this.productService.getProductById(productId);
    }

    @Operation(summary = "Create a new product", description = "Add a new product to the inventory.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid product data.")
    })
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return this.productService.createProduct(product);
    }

    @Operation(summary = "Update product information", description = "Update the details of an existing product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully."),
            @ApiResponse(responseCode = "404", description = "Product not found.")
    })
    @PutMapping
    public Product update(@RequestBody Product product) {
        return this.productService.updateProduct(product);
    }
}
