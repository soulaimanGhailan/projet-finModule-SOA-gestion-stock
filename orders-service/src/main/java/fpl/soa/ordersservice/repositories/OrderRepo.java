package fpl.soa.ordersservice.repositories;


import fpl.soa.ordersservice.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<OrderEntity, String> {
}
