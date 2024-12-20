package fpl.soa.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor @Data
public class PaymentProcessedEvent {
    private String orderId;
    private Long paymentId;
}
