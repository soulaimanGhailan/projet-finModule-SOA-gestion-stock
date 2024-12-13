package fpl.soa.common.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor @Data
public class ReserveProductCommand {
    private UUID productId;
    private Integer productQuantity;
    private UUID orderId;
}
