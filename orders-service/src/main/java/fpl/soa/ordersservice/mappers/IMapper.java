package fpl.soa.ordersservice.mappers;


import fpl.soa.ordersservice.dtos.CreateOrderRequest;
import fpl.soa.ordersservice.dtos.CreateOrderResponse;
import fpl.soa.ordersservice.entities.OrderEntity;

public interface IMapper {
    CreateOrderResponse from(OrderEntity orderEntity);
    OrderEntity from(CreateOrderRequest request);
}
