package fpl.soa.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReservationFailedEvent {
    private Long productId;
    private String orderId;
    private Integer productQuantity;

}
