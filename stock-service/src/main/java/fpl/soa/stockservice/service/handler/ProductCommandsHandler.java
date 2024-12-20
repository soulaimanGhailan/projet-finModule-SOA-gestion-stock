package fpl.soa.stockservice.service.handler;


import fpl.soa.common.commands.CancelProductReservationCommand;
import fpl.soa.common.commands.ReserveProductCommand;
import fpl.soa.common.events.ProductReservationCancelledEvent;
import fpl.soa.common.events.ProductReservationFailedEvent;
import fpl.soa.common.events.ProductReservedEvent;
import fpl.soa.stockservice.entities.Product;
import fpl.soa.stockservice.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics="${products.commands.topic.name}")
public class ProductCommandsHandler {

    private final ProductService productService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String productEventsTopicName;

    public ProductCommandsHandler(ProductService productService,
                                  KafkaTemplate<String, Object> kafkaTemplate,
                                  @Value("${products.events.topic.name}") String productEventsTopicName) {
        this.productService = productService;
        this.kafkaTemplate = kafkaTemplate;
        this.productEventsTopicName = productEventsTopicName;
    }

    @KafkaHandler
    public void handleCommand(@Payload ReserveProductCommand command) {

        System.out.println("reverved");

        try {
            Product desiredProduct = Product.builder().productId(command.getProductId()).quantity(command.getProductQuantity()).build();
            Product reservedProduct = productService.reserve(desiredProduct, command.getOrderId());
            ProductReservedEvent productReservedEvent = new ProductReservedEvent(command.getOrderId(),
                    command.getProductId(),
                    reservedProduct.getProductPrice().getPrice(),
                    command.getProductQuantity() , command.getCustomerId());
            kafkaTemplate.send(productEventsTopicName, productReservedEvent);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            ProductReservationFailedEvent productReservationFailedEvent = new ProductReservationFailedEvent(command.getProductId(),
                    command.getOrderId(), command.getProductQuantity());
            kafkaTemplate.send(productEventsTopicName, productReservationFailedEvent);
        }
    }
    @KafkaHandler
    public void handleCommand(@Payload CancelProductReservationCommand command) {
        Product productToCancel = Product.builder().quantity(command.getProductQuantity()).productId(command.getProductId()).build();
        productService.cancelReservation(productToCancel, command.getOrderId());

        ProductReservationCancelledEvent productReservationCancelledEvent =
                new ProductReservationCancelledEvent(command.getProductId(), command.getOrderId());
        kafkaTemplate.send(productEventsTopicName, productReservationCancelledEvent);
    }
}
