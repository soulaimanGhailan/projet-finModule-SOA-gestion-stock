package fpl.soa.customerservice.service;

import fpl.soa.customerservice.entities.Customer;
import fpl.soa.customerservice.entities.ShoppingCart;
import fpl.soa.customerservice.repos.CustomerRepo;
import fpl.soa.customerservice.repos.ShoppingCartRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepo customerRepo ;
    private ShoppingCartRepo shoppingCartRepo ;

    public CustomerServiceImpl(CustomerRepo customerRepo, ShoppingCartRepo shoppingCartRepo) {
        this.customerRepo = customerRepo;
        this.shoppingCartRepo = shoppingCartRepo;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        ShoppingCart cart = ShoppingCart.builder()
                .id(UUID.randomUUID().toString())
                .items(new ArrayList<>())
                .customerId(customer.getCustomerId()).build();
        ShoppingCart insertedCart = this.shoppingCartRepo.insert(cart);
        customer.setShoppingCart(insertedCart);
        return  this.customerRepo.insert(customer);
    }



    @Override
    public Customer getCustomer(String customerId) {
        System.out.println(customerId);
        Customer customer = customerRepo.findById(customerId).orElse(null);
        System.out.println(customer);
        return customer;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }
}
