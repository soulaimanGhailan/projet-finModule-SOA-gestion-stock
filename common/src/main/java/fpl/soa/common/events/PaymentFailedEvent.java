package fpl.soa.common.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class PaymentFailedEvent {
    private String orderId;
    private String productId;
    private Integer productQuantity;
    private String customerEmailAddress;
    private String originatingAddress;
    private String shippingAddress;
    private String firstname;
    private String lastname;
//    private long amount ;
}