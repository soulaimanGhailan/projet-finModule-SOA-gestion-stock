package fpl.soa.ordersservice.entities;


import fpl.soa.common.types.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.sql.Timestamp;

@Document
@AllArgsConstructor @NoArgsConstructor @Data
public class OrderHistoryEntity {
    @Id
    private String id;
    private String orderId;
    private OrderStatus status;
    private Timestamp createdAt;
}
