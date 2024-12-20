package fpl.soa.ordersservice.restClient;

import fpl.soa.ordersservice.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "products-service" , url = "${products.service.url}")
public interface ProductRestClient {
    @GetMapping("/api/v1/products/id/{id}")
    Product getProduct(@PathVariable("id") Long id);
}
