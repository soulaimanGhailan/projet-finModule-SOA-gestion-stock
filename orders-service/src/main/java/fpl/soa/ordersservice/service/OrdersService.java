package fpl.soa.ordersservice.service;
import fpl.soa.ordersservice.dtos.CreateOrderRequest;
import fpl.soa.ordersservice.dtos.CreateOrderResponse;
import fpl.soa.ordersservice.entities.OrderEntity;
import fpl.soa.ordersservice.models.Customer;

public interface OrdersService {
    CreateOrderResponse placeOrder(CreateOrderRequest orderReq);
    void approveOrder(String orderId);
    OrderEntity getOrderWithCustomer(String orderId);
    OrderEntity getOrderById(String orderId);
    void rejectOrder(String orderId);
    Customer getCustomerOfOrder(String orderId);
}
