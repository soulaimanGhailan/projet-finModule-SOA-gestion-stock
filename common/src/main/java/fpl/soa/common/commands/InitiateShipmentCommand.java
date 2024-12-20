package fpl.soa.common.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor
@Builder
public class InitiateShipmentCommand {
    private String orderId ;
    private String shippingAddress ;
    private String originatingAddress ;
}
