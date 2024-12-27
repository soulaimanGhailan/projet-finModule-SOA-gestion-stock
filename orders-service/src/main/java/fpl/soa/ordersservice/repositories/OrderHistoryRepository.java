package fpl.soa.ordersservice.repositories;

import fpl.soa.ordersservice.entities.OrderHistoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;


public interface OrderHistoryRepository extends MongoRepository<OrderHistoryEntity, String> {
    List<OrderHistoryEntity> findOrderHistoryEntitiesByOrderId(String orderId);
}
