package fpl.soa.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReservationFailedEvent {
    private UUID productId;
    private UUID orderId;
    private Integer productQuantity;

}
