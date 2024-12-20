package fpl.soa.paymentservice.service;


import fpl.soa.paymentservice.entities.PaymentEntity;

import java.util.List;

public interface PaymentService {
    List<PaymentEntity> findAll();
    PaymentEntity process(PaymentEntity payment);
}
