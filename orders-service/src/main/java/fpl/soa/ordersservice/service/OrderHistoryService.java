package fpl.soa.ordersservice.service;

import fpl.soa.common.types.OrderStatus;
import fpl.soa.ordersservice.entities.OrderHistoryEntity;

import java.util.List;
import java.util.UUID;

public interface OrderHistoryService {
    void add(String orderId, OrderStatus orderStatus);
    List<OrderHistoryEntity> findByOrderId(String orderId);
}
