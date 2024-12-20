package fpl.soa.paymentservice.repositories;

import fpl.soa.paymentservice.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<PaymentEntity, Long> {
}
