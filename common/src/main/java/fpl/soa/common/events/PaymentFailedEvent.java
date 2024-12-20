package fpl.soa.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @AllArgsConstructor @NoArgsConstructor
public class PaymentFailedEvent {
    private String orderId;
    private Long productId;
    private Integer productQuantity;
}
