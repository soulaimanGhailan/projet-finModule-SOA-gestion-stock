package fpl.soa.ordersservice.service;



import fpl.soa.ordersservice.dtos.CreateOrderRequest;
import fpl.soa.ordersservice.dtos.CreateOrderResponse;
import fpl.soa.ordersservice.entities.OrderEntity;



public interface OrdersService {
    CreateOrderResponse placeOrder(CreateOrderRequest orderReq);
    void approveOrder(String orderId);
    OrderEntity getOrderWithCustomer(String orderId);
    OrderEntity getOrderById(String orderId);
}
