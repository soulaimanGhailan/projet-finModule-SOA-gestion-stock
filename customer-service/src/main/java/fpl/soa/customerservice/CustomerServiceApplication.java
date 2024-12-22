package fpl.soa.customerservice;

import fpl.soa.customerservice.entities.Customer;
import fpl.soa.customerservice.service.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

//    @Bean
    CommandLineRunner run(CustomerService customerService) {
        return args -> {
            Customer build = Customer.builder()
                    .customerId("soulaiman")
                    .email("soulaiman8ghailan@gmail.com")
                    .firstname("soulaiman")
                    .lastname("ghailan")
                    .phoneNumber("4004805843058")
                    .shippingAddress("larache lot el lllll 2000").build();
            customerService.createCustomer(build) ;
        } ;
    }
}
