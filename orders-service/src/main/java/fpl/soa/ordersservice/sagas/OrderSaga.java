package fpl.soa.ordersservice.sagas;

import fpl.soa.common.commands.ApproveOrderCommand;
import fpl.soa.common.commands.InitiateShipmentCommand;
import fpl.soa.common.commands.ProcessPaymentCommand;
import fpl.soa.common.commands.ReserveProductCommand;
import fpl.soa.common.events.*;
import fpl.soa.common.types.OrderStatus;
import fpl.soa.ordersservice.entities.OrderEntity;
import fpl.soa.ordersservice.service.OrderHistoryService;
import fpl.soa.ordersservice.service.OrdersService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@KafkaListener(topics={
        "${orders.events.topic.name}" ,
        "${products.events.topic.name}",
        "${payments.events.topic.name}",
        "${shipment.event.topic.name}"
})
public class OrderSaga {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String productsCommandsTopicName;
    private final OrderHistoryService orderHistoryService;
    private final String shipmentCommandsTopicName;
    private final String paymentsCommandsTopicName;
    private OrdersService ordersService ;
    private String ordersCommandsTopicName ;

    public OrderSaga(KafkaTemplate<String, Object> kafkaTemplate,
                     @Value("${products.commands.topic.name}") String productsCommandsTopicName,
                     OrderHistoryService orderHistoryService, @Value("${shipment.commands.topic.name}") String shipmentCommandsTopicName,
                     @Value("${payments.commands.topic.name}") String paymentsCommandsTopicName, OrdersService ordersService, @Value("${orders.commands.topic.name}") String ordersCommandsTopicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.productsCommandsTopicName = productsCommandsTopicName;
        this.orderHistoryService = orderHistoryService;
        this.shipmentCommandsTopicName = shipmentCommandsTopicName;
        this.paymentsCommandsTopicName = paymentsCommandsTopicName;
        this.ordersService = ordersService;
        this.ordersCommandsTopicName = ordersCommandsTopicName;
    }

    @KafkaHandler
    public void handleEvent(@Payload OrderCreatedEvent event) {
        System.out.println("***** SAGA step 1 : OrderCreated / orderId : " + event.getOrderId() + " ************* ");
        ReserveProductCommand command = new ReserveProductCommand(
                event.getProductId(),
                event.getProductQuantity(),
                event.getOrderId() ,
                event.getCustomerId()
        );
        kafkaTemplate.send(productsCommandsTopicName,command);
        orderHistoryService.add(event.getOrderId(), OrderStatus.CREATED);
    }

    @KafkaHandler
    public void handleEvent(@Payload ProductReservedEvent event) {
        System.out.println("***** SAGA step 2 : ProductReserved / orderId : " + event.getOrderId() + " ************* ");
        ProcessPaymentCommand processPaymentCommand = ProcessPaymentCommand.builder()
                .orderId(event.getOrderId())
                .productId(event.getProductId())
                .productPrice(event.getProductPrice())
                .customerId(event.getCustomerId())
                .productQuantity(event.getProductQuantity())
                .build();

        kafkaTemplate.send(paymentsCommandsTopicName,processPaymentCommand);


    }
    @KafkaHandler
    public void handleEvent(@Payload PaymentProcessedEvent event){
        System.out.println("***** SAGA step 3 : PaymentProcessed / orderId :  " + event.getOrderId() + " ************* ");
        OrderEntity orderWithCustomer = ordersService.getOrderWithCustomer(event.getOrderId());
        InitiateShipmentCommand initiateShipmentCommand = InitiateShipmentCommand.builder()
                .orderId(event.getOrderId())
                .shippingAddress(orderWithCustomer.getCustomer().getShippingAddress())
                .originatingAddress(orderWithCustomer.getProduct().getOriginLocation()).build();
        kafkaTemplate.send(shipmentCommandsTopicName,initiateShipmentCommand);
    }

    @KafkaHandler
    public void handleEvent(@Payload ShipmentInProgressEvent event){
        System.out.println("***** SAGA step 4 : ShipmentInProgress / orderId : " + event.getOrderId() + " ************* ");
        ApproveOrderCommand approveOrderCommand = new ApproveOrderCommand(event.getOrderId());
        kafkaTemplate.send(ordersCommandsTopicName,approveOrderCommand);
    }
    @KafkaHandler
    public void handleEvent(@Payload OrderApprovedEvent event) {
        System.out.println("***** SAGA step 5 : OrderApproved / orderId : " + event.getOrderId() + " ************* ");
        orderHistoryService.add(event.getOrderId(), OrderStatus.APPROVED);
    }
}