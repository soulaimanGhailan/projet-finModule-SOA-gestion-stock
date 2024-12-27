package fpl.soa.common.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data @AllArgsConstructor
@NoArgsConstructor @Builder
public class ProcessPaymentCommand {
    private String orderId;
    private String productId;
    private long productPrice;
    private Integer productQuantity;
    private String customerId;
    private String customerEmailAddress;
    private String originatingAddress;
    private String shippingAddress;
    private String firstname;
    private String lastname;
}
