package fpl.soa.paymentservice.service;


import fpl.soa.common.exceptions.CreditCardProcessorUnavailableException;
import fpl.soa.paymentservice.entities.PaymentEntity;
import fpl.soa.paymentservice.models.CreditCard;
import fpl.soa.paymentservice.repositories.PaymentRepo;
import fpl.soa.paymentservice.webClient.CreditCardRestClient;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PaymentServiceImpl implements PaymentService {
    private PaymentRepo paymentRepository;
    private CreditCardRestClient creditCardRestClient ;

    public PaymentServiceImpl(PaymentRepo paymentRepository,
                              CreditCardRestClient creditCardRestClient) {
        this.paymentRepository = paymentRepository;
        this.creditCardRestClient = creditCardRestClient;
    }

    @Override
    public PaymentEntity process(PaymentEntity payment) throws CreditCardProcessorUnavailableException {
        CreditCard creditCard = creditCardRestClient.getCreditCard(payment.getCustomerId());
        if (creditCard.getCardId() == null) {
            throw new CreditCardProcessorUnavailableException("credit card not found") ;
        }
        /** process payment logic { charge the card } **/
        return paymentRepository.save(payment);
    }

    @Override
    public List<PaymentEntity> findAll() {
        return paymentRepository.findAll() ;
    }
}
