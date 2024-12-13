package fpl.soa.ordersservice.sagas;

import fpl.soa.common.commands.ReserveProductCommand;
import fpl.soa.common.events.OrderCreatedEvent;
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
        "${orders.events.topic.name}"
})
public class OrderSaga {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String productsCommandsTopicName;
    private final OrderHistoryService orderHistoryService;

    public OrderSaga(KafkaTemplate<String, Object> kafkaTemplate,
                     @Value("${products.commands.topic.name}") String productsCommandsTopicName,
                     OrderHistoryService orderHistoryService) {
        this.kafkaTemplate = kafkaTemplate;
        this.productsCommandsTopicName = productsCommandsTopicName;
        this.orderHistoryService = orderHistoryService;
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
}
