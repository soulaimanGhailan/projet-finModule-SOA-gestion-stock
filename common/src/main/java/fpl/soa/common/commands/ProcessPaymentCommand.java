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
    private Long productId;
    private BigDecimal productPrice;
    private Integer productQuantity;
    private String customerId;
}
