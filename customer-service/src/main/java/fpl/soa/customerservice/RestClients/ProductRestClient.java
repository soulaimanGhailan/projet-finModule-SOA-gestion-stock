package fpl.soa.customerservice.RestClients;


import fpl.soa.customerservice.model.Price;
import fpl.soa.customerservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductRestClient {
    @GetMapping("products/{id}")
    Product getProduct(@PathVariable String id);
    @GetMapping("products/{productId}/productPrice")
    Price getProductPrice(@PathVariable String productId) ;
}
