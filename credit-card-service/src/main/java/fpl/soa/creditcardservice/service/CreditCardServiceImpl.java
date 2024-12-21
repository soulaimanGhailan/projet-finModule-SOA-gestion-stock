package fpl.soa.creditcardservice.service;

import fpl.soa.creditcardservice.entities.CreditCard;
import fpl.soa.creditcardservice.repositories.CreditCardRepo;
import org.springframework.stereotype.Service;

@Service
public class CreditCardServiceImpl implements CreditCardService {
    private CreditCardRepo creditCardRepo ;

    public CreditCardServiceImpl(CreditCardRepo creditCardRepo) {
        this.creditCardRepo = creditCardRepo;
    }

    @Override
    public CreditCard getCreditCardById(String customerId) {
        return creditCardRepo.findCreditCardByCustomerId(customerId).orElse(new CreditCard()) ;
    }

    @Override
    public CreditCard createCreditCard(CreditCard creditCard) {
        return creditCardRepo.save(creditCard);
    }
}
