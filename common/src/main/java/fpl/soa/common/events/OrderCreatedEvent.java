package fpl.soa.common.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor @Builder
public class OrderCreatedEvent {
    private String orderId;
    private String customerId;
    private String productId;
    private Integer productQuantity;
    private String customerEmailAddress;
    private String originatingAddress;
    private String shippingAddress;
    private String firstname;
    private String lastname;
}
