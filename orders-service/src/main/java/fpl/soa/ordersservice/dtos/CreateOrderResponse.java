package fpl.soa.ordersservice.dtos;


import fpl.soa.common.types.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderResponse {
    private String orderId;
    private String customerId;
    private Long productId;
    private Integer productQuantity;
    private OrderStatus status;
}
