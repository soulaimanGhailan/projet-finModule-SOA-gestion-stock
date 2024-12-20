package fpl.soa.ordersservice.repositories;

import fpl.soa.ordersservice.entities.OrderHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface OrderHistoryRepository extends JpaRepository<OrderHistoryEntity, String> {
    List<OrderHistoryEntity> findOrderHistoryEntitiesByOrderId(String orderId);
}
