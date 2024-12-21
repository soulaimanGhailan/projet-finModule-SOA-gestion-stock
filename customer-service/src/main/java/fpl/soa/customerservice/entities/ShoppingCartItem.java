package fpl.soa.customerservice.entities;

import fpl.soa.customerservice.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Builder
public class ShoppingCartItem {
    private Product product;
    private int quantity;
}
