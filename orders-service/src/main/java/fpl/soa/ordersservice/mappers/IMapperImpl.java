package fpl.soa.ordersservice.mappers;


import fpl.soa.ordersservice.dtos.CreateOrderRequest;
import fpl.soa.ordersservice.dtos.CreateOrderResponse;
import fpl.soa.ordersservice.entities.OrderEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class IMapperImpl implements IMapper {
    @Override
    public CreateOrderResponse from(OrderEntity orderEntity) {
        CreateOrderResponse response = new CreateOrderResponse();
        BeanUtils.copyProperties(orderEntity, response);
        return response;
    }

    @Override
    public OrderEntity from(CreateOrderRequest request) {
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(request, orderEntity);
        return orderEntity;
    }
}
