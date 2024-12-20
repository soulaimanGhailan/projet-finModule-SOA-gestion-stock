package fpl.soa.ordersservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Product {
    private Long id;
    private Integer quantity;
    private String name;
    private BigDecimal price ;
    private String originLocation ;
}
