package fpl.soa.ordersservice.entities;


import fpl.soa.common.types.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity @AllArgsConstructor @NoArgsConstructor @Data
public class OrderHistoryEntity {
    @Id
    private String id;
    private String orderId;
    private OrderStatus status;
    private Timestamp createdAt;
}
