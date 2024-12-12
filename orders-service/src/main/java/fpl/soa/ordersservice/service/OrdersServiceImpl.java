package fpl.soa.ordersservice.service;

import fpl.soa.common.events.OrderCreatedEvent;
import fpl.soa.common.types.OrderStatus;
import fpl.soa.ordersservice.dtos.CreateOrderRequest;
import fpl.soa.ordersservice.dtos.CreateOrderResponse;
import fpl.soa.ordersservice.entities.OrderEntity;
import fpl.soa.ordersservice.mappers.IMapper;
import fpl.soa.ordersservice.repositories.OrderRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrdersServiceImpl implements OrdersService {

    private OrderRepo orderRepo  ;
    private IMapper mapper ;
    private KafkaTemplate<String, Object> kafkaTemplate;
    private String ordersEventsTopicName;

    public OrdersServiceImpl(OrderRepo orderRepo, IMapper mapper, KafkaTemplate<String, Object> kafkaTemplate,@Value("${orders.events.topic.name}") String ordersEventsTopicName) {
        this.orderRepo = orderRepo;
        this.mapper = mapper;
        this.kafkaTemplate = kafkaTemplate;
        this.ordersEventsTopicName = ordersEventsTopicName;
    }


    @Override
    public CreateOrderResponse placeOrder(CreateOrderRequest orderReq) {
        OrderEntity orderEntity = mapper.from(orderReq);
        orderEntity.setStatus(OrderStatus.CREATED);
        orderEntity.setOrderId(UUID.randomUUID());
        OrderEntity savedOrderEntity = orderRepo.save(orderEntity);

        OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.builder()
                .orderId(savedOrderEntity.getOrderId())
                .customerId(savedOrderEntity.getCustomerId())
                .productId(savedOrderEntity.getProductId())
                .productQuantity(savedOrderEntity.getProductQuantity())
                .build();

        kafkaTemplate.send(ordersEventsTopicName , orderCreatedEvent) ;


        return mapper.from(savedOrderEntity);
    }

    @Override
    public void approveOrder(UUID orderId) {

    }
}
