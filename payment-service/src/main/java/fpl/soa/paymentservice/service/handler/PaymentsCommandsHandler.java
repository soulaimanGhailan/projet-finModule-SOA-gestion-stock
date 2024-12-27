package fpl.soa.paymentservice.service.handler;


import fpl.soa.common.commands.ProcessPaymentCommand;
import fpl.soa.common.events.PaymentFailedEvent;
import fpl.soa.common.events.PaymentProcessedEvent;
import fpl.soa.common.exceptions.CreditCardProcessorUnavailableException;
import fpl.soa.paymentservice.entities.PaymentEntity;
import fpl.soa.paymentservice.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics="${payments.commands.topic.name}")
public class PaymentsCommandsHandler {

    private final PaymentService paymentService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String paymentEventsTopicName;

    public PaymentsCommandsHandler(PaymentService paymentService,
                                   KafkaTemplate<String, Object> kafkaTemplate,
                                   @Value("${payments.events.topic.name}") String paymentEventsTopicName) {
        this.paymentService = paymentService;
        this.kafkaTemplate = kafkaTemplate;
        this.paymentEventsTopicName = paymentEventsTopicName;
    }

    @KafkaHandler
    public void handleCommand(@Payload ProcessPaymentCommand command) {

        try {
            PaymentEntity payment = PaymentEntity.builder()
                    .productId(command.getProductId())
                    .orderId(command.getOrderId())
                    .customerId(command.getCustomerId())
                    .productQuantity(command.getProductQuantity())
                    .productPrice(command.getProductPrice())
                    .build();
            PaymentEntity processedPayment = paymentService.process(payment);
            PaymentProcessedEvent paymentProcessedEvent = PaymentProcessedEvent.builder()
                    .orderId(processedPayment.getOrderId())
                    .paymentId(payment.getId())
                    .amount(payment.getAmount())
                    .customerEmailAddress(command.getCustomerEmailAddress())
                    .shippingAddress(command.getShippingAddress())
                    .originatingAddress(command.getOriginatingAddress())
                    .firstname(command.getFirstname())
                    .lastname(command.getLastname())
                    .build();

            kafkaTemplate.send(paymentEventsTopicName, paymentProcessedEvent);
        } catch (CreditCardProcessorUnavailableException e) {
            logger.error(e.getLocalizedMessage(), e);
            PaymentFailedEvent paymentFailedEvent = PaymentFailedEvent.builder()
                    .orderId(command.getOrderId())
                    .productId(command.getProductId())
                    .productQuantity(command.getProductQuantity())
                    .customerEmailAddress(command.getCustomerEmailAddress())
                    .firstname(command.getFirstname())
                    .lastname(command.getLastname())
                    .build();
            kafkaTemplate.send(paymentEventsTopicName,paymentFailedEvent);
        }
    }
}
