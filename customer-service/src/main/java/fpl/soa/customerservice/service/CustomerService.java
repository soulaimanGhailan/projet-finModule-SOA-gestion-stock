package fpl.soa.customerservice.service;


import fpl.soa.customerservice.entities.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(Customer customer) ;
    Customer getCustomer(String customerId);
    List<Customer> getAllCustomers();
    void syncKeycloakUsers();

}
