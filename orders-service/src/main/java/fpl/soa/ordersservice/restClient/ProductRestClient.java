package fpl.soa.ordersservice.restClient;

import fpl.soa.ordersservice.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "stock-service")
public interface ProductRestClient {
    @GetMapping("api/v1/products/find/{id}")
    Product getProduct(@PathVariable("id") String id);
}
