package fpl.soa.common.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor @Data @Builder
public class ReserveProductCommand {
    private String productId;
    private Integer productQuantity;
    private String orderId;
    private String customerId;
    private String customerEmailAddress;
    private String originatingAddress;
    private String shippingAddress;
    private String firstname;
    private String lastname;
}
