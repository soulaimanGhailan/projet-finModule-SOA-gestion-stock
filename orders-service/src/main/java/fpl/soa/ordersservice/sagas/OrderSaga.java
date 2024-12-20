package fpl.soa.ordersservice.sagas;

import fpl.soa.common.commands.ProcessPaymentCommand;
import fpl.soa.common.commands.ReserveProductCommand;
import fpl.soa.common.events.OrderCreatedEvent;
import fpl.soa.common.events.PaymentProcessedEvent;
import fpl.soa.common.events.ProductReservedEvent;
import fpl.soa.common.types.OrderStatus;
import fpl.soa.ordersservice.service.OrderHistoryService;
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
        "${payments.events.topic.name}"
})
public class OrderSaga {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String productsCommandsTopicName;
    private final OrderHistoryService orderHistoryService;
    private final String productsEventsTopicName;
    private final String paymentsCommandsTopicName;

    public OrderSaga(KafkaTemplate<String, Object> kafkaTemplate,
                     @Value("${products.commands.topic.name}") String productsCommandsTopicName,
                     OrderHistoryService orderHistoryService, @Value("${products.events.topic.name}") String productsEventsTopicName,
                     @Value("${payments.commands.topic.name}") String paymentsCommandsTopicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.productsCommandsTopicName = productsCommandsTopicName;
        this.orderHistoryService = orderHistoryService;
        this.productsEventsTopicName = productsEventsTopicName;
        this.paymentsCommandsTopicName = paymentsCommandsTopicName;
    }

    @KafkaHandler
    public void handleEvent(@Payload OrderCreatedEvent event) {

        ReserveProductCommand command = new ReserveProductCommand(
                event.getProductId(),
                event.getProductQuantity(),
                event.getOrderId()
        );
        kafkaTemplate.send(productsCommandsTopicName,command);
        orderHistoryService.add(event.getOrderId(), OrderStatus.CREATED);
    }

    @KafkaHandler
    public void handleEvent(@Payload ProductReservedEvent event) {
        ProcessPaymentCommand processPaymentCommand = ProcessPaymentCommand.builder()
                .orderId(event.getOrderId())
                .productId(event.getProductId())
                .productPrice(event.getProductPrice())
                .productQuantity(event.getProductQuantity())
                .build();

        kafkaTemplate.send(paymentsCommandsTopicName,processPaymentCommand);

    }

}
