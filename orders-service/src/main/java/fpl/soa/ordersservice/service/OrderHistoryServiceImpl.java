package fpl.soa.ordersservice.service;


import fpl.soa.common.types.OrderStatus;
import fpl.soa.ordersservice.entities.OrderEntity;
import fpl.soa.ordersservice.entities.OrderHistoryEntity;
import fpl.soa.ordersservice.repositories.OrderHistoryRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {
    private OrderHistoryRepository orderHistoryRepository;

    public OrderHistoryServiceImpl(OrderHistoryRepository orderHistoryRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
    }

    @Override
    public void add(String orderId, OrderStatus orderStatus) {
        OrderHistoryEntity entity = new OrderHistoryEntity();
        entity.setOrderId(orderId);
        entity.setStatus(orderStatus);
        entity.setCreatedAt(new Timestamp(new Date().getTime()));
        entity.setId(UUID.randomUUID().toString());
        System.out.println(entity);
        orderHistoryRepository.save(entity);
    }

    @Override
    public List<OrderHistoryEntity> findByOrderId(String orderId) {
      return  orderHistoryRepository.findOrderHistoryEntitiesByOrderId(orderId);
    }
}
