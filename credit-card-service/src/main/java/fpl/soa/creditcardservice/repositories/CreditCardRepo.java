package fpl.soa.creditcardservice.repositories;


import fpl.soa.creditcardservice.entities.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;


@RepositoryRestResource
public interface CreditCardRepo extends JpaRepository<CreditCard, String> {
    Optional<CreditCard> findCreditCardByCustomerId(String customerId);
}
