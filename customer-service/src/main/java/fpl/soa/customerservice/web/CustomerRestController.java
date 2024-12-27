package fpl.soa.customerservice.web;

import fpl.soa.customerservice.entities.Customer;
import fpl.soa.customerservice.entities.ShoppingCart;
import fpl.soa.customerservice.exceptions.CustomerNotFoundException;
import fpl.soa.customerservice.model.AddItemRequest;
import fpl.soa.customerservice.service.CustomerService;
import fpl.soa.customerservice.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@CrossOrigin(origins = "*")
public class CustomerRestController {

    private final ShoppingCartService cartService;
    private final CustomerService customerService;

    public CustomerRestController(ShoppingCartService cartService, CustomerService customerService) {
        this.cartService = cartService;
        this.customerService = customerService;
    }

    @Operation(summary = "Get customer by ID", description = "Retrieve customer details by their unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found and returned successfully."),
            @ApiResponse(responseCode = "404", description = "Customer not found.")
    })
    @GetMapping("{customerId}")
    public Customer getCustomerById(@PathVariable String customerId) {
        return customerService.getCustomer(customerId);
    }

    @Operation(summary = "Get all customers", description = "Retrieve a list of all customers.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of customers returned successfully.")
    })
    @GetMapping("/all")
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers();
    }

    @Operation(summary = "Add product to cart", description = "Add a product to the customer's shopping cart.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product added to cart successfully."),
            @ApiResponse(responseCode = "404", description = "Customer not found.")
    })
    @PostMapping
    public ShoppingCart addProductToCart(@RequestBody AddItemRequest addItemRequest) {
        try {
            return this.cartService.addItemToCart(addItemRequest);
        } catch (CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Operation(summary = "Remove product from cart", description = "Remove a specific product from the customer's shopping cart.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product removed from cart successfully."),
            @ApiResponse(responseCode = "404", description = "Customer not found.")
    })
    @DeleteMapping("{customerId}/{productId}")
    public ShoppingCart deleteItemFromCart(@PathVariable String customerId, @PathVariable String productId) {
        try {
            return this.cartService.removeItemFromCart(customerId, productId);
        } catch (CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
