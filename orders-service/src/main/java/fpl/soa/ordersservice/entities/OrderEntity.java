package fpl.soa.ordersservice.entities;


import fpl.soa.common.types.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;


@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class OrderEntity {
    @Id
    private UUID orderId;
    private OrderStatus status;
    private UUID customerId;
    private UUID productId;
    private Integer productQuantity;
}
