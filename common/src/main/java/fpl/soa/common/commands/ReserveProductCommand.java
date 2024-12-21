package fpl.soa.common.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor @Data
public class ReserveProductCommand {
    private String productId;
    private Integer productQuantity;
    private String orderId;
    private String customerId;
}
