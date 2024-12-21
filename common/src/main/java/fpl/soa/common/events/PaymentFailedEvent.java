package fpl.soa.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @AllArgsConstructor @NoArgsConstructor
public class PaymentFailedEvent {
    private String orderId;
    private String productId;
    private Integer productQuantity;
}
