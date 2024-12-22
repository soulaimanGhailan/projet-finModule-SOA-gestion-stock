package fpl.soa.customerservice.web;

import fpl.soa.customerservice.entities.Customer;
import fpl.soa.customerservice.entities.ShoppingCart;
import fpl.soa.customerservice.exceptions.CustomerNotFoundException;
import fpl.soa.customerservice.model.AddItemRequest;
import fpl.soa.customerservice.service.CustomerService;
import fpl.soa.customerservice.service.ShoppingCartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@CrossOrigin(origins = "*")
public class CustomerRestController {

    private ShoppingCartService cartService ;
    private CustomerService customerService ;

     public CustomerRestController(ShoppingCartService cartService, CustomerService customerService) {
        this.cartService = cartService;
         this.customerService = customerService;
     }

    @GetMapping("{customerId}")
    public Customer getCustomerById(@PathVariable String customerId) {
        return customerService.getCustomer(customerId) ;
    }

    @GetMapping("/all")
    public List<Customer> getCustomers() {
        return customerService.getAllCustomers() ;
    }



    @PostMapping
    public ShoppingCart addProductToCart(@RequestBody AddItemRequest addItemRequest){
        try {
            return this.cartService.addItemToCart(addItemRequest) ;
        } catch (CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @DeleteMapping("{customerId}/{productId}")
    public ShoppingCart deleteItemFromCart(@PathVariable String customerId , @PathVariable String productId){
        try {
            return this.cartService.removeItemFromCart(customerId , productId) ;
        } catch (CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
