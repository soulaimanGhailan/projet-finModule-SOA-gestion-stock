package fpl.soa.ordersservice.service;



import fpl.soa.ordersservice.dtos.CreateOrderRequest;
import fpl.soa.ordersservice.dtos.CreateOrderResponse;

import java.util.UUID;

public interface OrdersService {
    CreateOrderResponse placeOrder(CreateOrderRequest orderReq);
    void approveOrder(UUID orderId);
}
