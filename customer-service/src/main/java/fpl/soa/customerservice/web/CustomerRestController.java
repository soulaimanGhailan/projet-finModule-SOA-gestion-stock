package fpl.soa.customerservice.web;

import fpl.soa.customerservice.entities.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestController {
    @GetMapping("{customerId}")
    public Customer getCustomerById(@PathVariable String customerId) {
        return Customer.builder()
                .email("soulaiman8ghailan@gmail.com")
                .customerId("550e8400-e29b-41d4-a716-446655440000")
                .firstname("soulaiman")
                .lastname("ghailan")
                .phoneNumber("4004805843058")
                .shippingAddress("larache lot el lllll 2000").build();
    }
}
