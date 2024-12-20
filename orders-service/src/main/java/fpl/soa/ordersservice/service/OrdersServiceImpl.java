package fpl.soa.ordersservice.service;

import fpl.soa.common.events.OrderCreatedEvent;
import fpl.soa.common.types.OrderStatus;
import fpl.soa.ordersservice.dtos.CreateOrderRequest;
import fpl.soa.ordersservice.dtos.CreateOrderResponse;
import fpl.soa.ordersservice.entities.OrderEntity;
import fpl.soa.ordersservice.mappers.IMapper;
import fpl.soa.ordersservice.models.Customer;
import fpl.soa.ordersservice.models.Product;
import fpl.soa.ordersservice.repositories.OrderRepo;
import fpl.soa.ordersservice.restClient.CustomerRestClient;
import fpl.soa.ordersservice.restClient.ProductRestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Order;
import java.util.UUID;

@Service
public class OrdersServiceImpl implements OrdersService {

    private OrderRepo orderRepo  ;
    private IMapper mapper ;
    private KafkaTemplate<String, Object> kafkaTemplate;
    private String ordersEventsTopicName;
    private CustomerRestClient customerRestClient ;
    private ProductRestClient productRestClient ;

    public OrdersServiceImpl(OrderRepo orderRepo, IMapper mapper, KafkaTemplate<String, Object> kafkaTemplate, @Value("${orders.events.topic.name}") String ordersEventsTopicName, CustomerRestClient customerRestClient, ProductRestClient productRestClient) {
        this.orderRepo = orderRepo;
        this.mapper = mapper;
        this.kafkaTemplate = kafkaTemplate;
        this.ordersEventsTopicName = ordersEventsTopicName;
        this.customerRestClient = customerRestClient;
        this.productRestClient = productRestClient;
    }


    @Override
    public CreateOrderResponse placeOrder(CreateOrderRequest orderReq) {
        OrderEntity orderEntity = mapper.from(orderReq);
        orderEntity.setStatus(OrderStatus.CREATED);
        orderEntity.setOrderId(UUID.randomUUID().toString());
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
    public void approveOrder(String orderId) {

    }

    @Override
    public OrderEntity getOrderWithCustomer(String orderId) {
        OrderEntity orderById = getOrderById(orderId);
        Customer customer = customerRestClient.getCustomer(orderById.getCustomerId());
        Product product = productRestClient.getProduct(orderById.getProductId());
        orderById.setCustomer(customer);
        orderById.setProduct(product);
        return orderById;

    }

    @Override
    public OrderEntity getOrderById(String orderId) {
        return orderRepo.findById(orderId).orElse(null);
    }
}
