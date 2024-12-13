package fpl.soa.ordersservice.entities;


import fpl.soa.common.types.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Table(name = "orders_history")
@Entity @AllArgsConstructor @NoArgsConstructor @Data
public class OrderHistoryEntity {
    @Id
    private UUID id;
    @Column(name = "order_id")
    private UUID orderId;
    @Column(name = "status")
    private OrderStatus status;
    @Column(name = "created_at")
    private Timestamp createdAt;
}
