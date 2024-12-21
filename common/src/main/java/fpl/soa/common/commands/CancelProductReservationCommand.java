package fpl.soa.common.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@AllArgsConstructor
public class CancelProductReservationCommand {
    private Long productId;
    private String orderId;
    private Integer productQuantity;
}
