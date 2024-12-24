package fpl.soa.ordersservice.repositories;


import fpl.soa.ordersservice.entities.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface OrderRepo extends MongoRepository<OrderEntity, String> {
}
