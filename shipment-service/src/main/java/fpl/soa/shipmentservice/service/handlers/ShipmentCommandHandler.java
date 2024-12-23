package fpl.soa.shipmentservice.service.handlers;

import com.thoughtworks.xstream.converters.time.LocalDateConverter;
import fpl.soa.common.commands.InitiateShipmentCommand;
import fpl.soa.common.events.ShipmentInProgressEvent;
import fpl.soa.shipmentservice.entities.ShipmentEntity;
import fpl.soa.shipmentservice.enums.ShipmentStatus;
import fpl.soa.shipmentservice.service.ShipmentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Component
@KafkaListener(topics="${shipment.commands.topic.name}")
public class ShipmentCommandHandler {
    private ShipmentService shipmentService;
    private KafkaTemplate<String, Object> kafkaTemplate;
    private String ShipmentEventTopic;

    public ShipmentCommandHandler(ShipmentService shipmentService, KafkaTemplate<String, Object> kafkaTemplate,
                                  @Value("${shipment.event.topic.name}") String shipmentEventTopic) {
        this.shipmentService = shipmentService;
        this.kafkaTemplate = kafkaTemplate;
        ShipmentEventTopic = shipmentEventTopic;
    }

    @KafkaHandler
    public void handle(@Payload InitiateShipmentCommand command){
        ShipmentEntity shipmentEntity = ShipmentEntity.builder()
                .shipmentId(UUID.randomUUID().toString())
                .shippedDate(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .status(ShipmentStatus.IN_PROGRESS)
                .destination(command.getShippingAddress())
                .trackingNumber(generateTrackingNumber())
                .orderId(command.getOrderId())
                .estimatedDeliveryDate(LocalDate.now().plusWeeks(1))
                .origin(command.getOriginatingAddress())
                .build();
        ShipmentEntity createdShipmentEntity = shipmentService.createShipment(shipmentEntity);

        ShipmentInProgressEvent shipmentInProgressEvent = ShipmentInProgressEvent.builder()
                .deliveryExpectedDate(createdShipmentEntity.getEstimatedDeliveryDate())
                .originatingAddress(shipmentEntity.getOrigin())
                .shipmentInitDate(shipmentEntity.getShippedDate())
                .shippingAddress(shipmentEntity.getDestination())
                .customerEmailAddress(command.getCustomerEmailAddress())
                .firstname(command.getFirstname())
                .lastname(command.getLastname())
                .orderId(shipmentEntity.getOrderId())
                .trackingNum(createdShipmentEntity.getTrackingNumber())
                .build();

        kafkaTemplate.send(ShipmentEventTopic, shipmentInProgressEvent);

    }

    public static String generateTrackingNumber() {
        return "TRK-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }
}
