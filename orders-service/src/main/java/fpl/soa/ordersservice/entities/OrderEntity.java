package fpl.soa.ordersservice.entities;


import fpl.soa.common.types.OrderStatus;
import fpl.soa.ordersservice.models.Customer;
import fpl.soa.ordersservice.models.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.UUID;


@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class OrderEntity {
    @Id
    private String orderId;
    private OrderStatus status;
    private String customerId;
    private String productId;
    private Integer productQuantity;
    @Transient
    private Customer customer;
    @Transient
    private Product product;
}
