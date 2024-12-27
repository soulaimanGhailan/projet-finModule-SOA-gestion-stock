package fpl.soa.ordersservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
@Builder
public class ShoppingCart {
    private String id;
    private String customerId ;
    private List<ShoppingCartItem> items = new ArrayList<>();
}
