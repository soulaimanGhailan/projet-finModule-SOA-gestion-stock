package fpl.soa.stockservice.entities;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "products")
@Entity @AllArgsConstructor @NoArgsConstructor @Data
public class Product {
    @Id
    private UUID id;
    private Integer quantity;
    private String name;
    private BigDecimal price ;
}
