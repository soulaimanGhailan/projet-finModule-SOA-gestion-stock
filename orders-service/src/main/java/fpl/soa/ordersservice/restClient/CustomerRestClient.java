package fpl.soa.ordersservice.restClient;

import fpl.soa.ordersservice.models.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "CUSTOMER-SERVICE" , url = "${customer.service.url}")
public interface CustomerRestClient {
    @GetMapping("/api/v1/customers/{customerId}")
    Customer getCustomer(@PathVariable("customerId") String customerId , @RequestHeader(value = "Authorization", required = true) String authorizationHeader);
}
