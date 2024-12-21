package fpl.soa.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReservedEvent {
    private String orderId;
    private String productId;
    private long productPrice;
    private Integer productQuantity;
    private String customerId;

}
