package fpl.soa.ordersservice.service;

import fpl.soa.common.events.OrderApprovedEvent;
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
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
        Customer customer = customerRestClient.getCustomer(orderReq.getCustomerId(), getToken());
        Product product = productRestClient.getProduct(orderReq.getProductId(), getToken());
        OrderEntity orderEntity = mapper.from(orderReq);
        orderEntity.setStatus(OrderStatus.CREATED);
        orderEntity.setProductId(orderReq.getProductId());
        orderEntity.setOrderId(UUID.randomUUID().toString());
        OrderEntity savedOrderEntity = orderRepo.save(orderEntity);

        OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.builder()
                .orderId(savedOrderEntity.getOrderId())
                .customerId(savedOrderEntity.getCustomerId())
                .productId(savedOrderEntity.getProductId())
                .productQuantity(savedOrderEntity.getProductQuantity())
                .customerEmailAddress(customer.getEmail())
                .shippingAddress(customer.getShippingAddress())
                .originatingAddress(product.getOriginLocation())
                .firstname(customer.getFirstname())
                .lastname(customer.getLastname())
                .build();

        kafkaTemplate.send(ordersEventsTopicName , orderCreatedEvent) ;


        return mapper.from(savedOrderEntity);
    }

    @Override
    public void approveOrder(String orderId) {
        OrderEntity orderEntity = orderRepo.findById(orderId).orElse(null);
        Assert.notNull(orderEntity, "No order is found with id " + orderId + " in the database table");
        orderEntity.setStatus(OrderStatus.APPROVED);
        orderRepo.save(orderEntity);
        OrderApprovedEvent orderApprovedEvent = new OrderApprovedEvent(orderId);
        kafkaTemplate.send(ordersEventsTopicName, orderApprovedEvent);
    }

    @Override
    public OrderEntity getOrderWithCustomer(String orderId) {
        OrderEntity orderById = getOrderById(orderId);
        Customer customer = customerRestClient.getCustomer(orderById.getCustomerId() , getToken());
        System.out.println(customer);
        Product product = productRestClient.getProduct(orderById.getProductId() , getToken());
        orderById.setCustomer(customer);
        orderById.setProduct(product);
        return orderById;

    }

    @Override
    public OrderEntity getOrderById(String orderId) {
        return orderRepo.findById(orderId).orElse(null);
    }

    @Override
    public void rejectOrder(String  orderId) {
        OrderEntity orderEntity = orderRepo.findById(orderId).orElse(null);
        Assert.notNull(orderEntity, "No order found with id: " + orderId);
        orderEntity.setStatus(OrderStatus.REJECTED);
        orderRepo.save(orderEntity);
    }

    @Override
    public Customer getCustomerOfOrder(String orderId) {
        OrderEntity orderEntity = orderRepo.findById(orderId).orElse(null);
        return customerRestClient.getCustomer(orderEntity.getCustomerId() , getToken());
    }

    @Override
    public Customer getCustomer(String customerId) {
        return customerRestClient.getCustomer(customerId , getToken());
    }

    private String getToken(){
        KeycloakSecurityContext context = (KeycloakSecurityContext) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        String token ="bearer "+ context.getTokenString();
        System.out.println("*****************************");
        return token;
    }
}
