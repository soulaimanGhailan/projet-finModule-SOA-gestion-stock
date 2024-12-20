package fpl.soa.ordersservice.repositories;


import fpl.soa.ordersservice.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepo extends JpaRepository<OrderEntity, String> {
}
