package fpl.soa.stockservice.entities;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "products")
@Entity @AllArgsConstructor @NoArgsConstructor @Data
@Builder
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private String name;
    private BigDecimal price ;
    private String originLocation ;
}
