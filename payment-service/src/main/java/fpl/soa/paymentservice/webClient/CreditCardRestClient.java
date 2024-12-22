package fpl.soa.paymentservice.webClient;

import fpl.soa.paymentservice.models.CreditCard;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "credit-card-service")
public interface CreditCardRestClient {
    @GetMapping("/api/v1/creditCard/{customerId}")
    CreditCard getCreditCard(@PathVariable("customerId") String customerId);
}
