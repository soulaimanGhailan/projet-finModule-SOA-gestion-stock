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
    private UUID orderId;
    private UUID productId;
    private BigDecimal productPrice;
    private Integer productQuantity;
}
