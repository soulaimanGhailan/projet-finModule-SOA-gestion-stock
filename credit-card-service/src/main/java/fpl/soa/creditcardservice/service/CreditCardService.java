package fpl.soa.creditcardservice.service;

import fpl.soa.creditcardservice.entities.CreditCard;


public interface CreditCardService {
    CreditCard getCreditCardById(String customerId);
    CreditCard createCreditCard(CreditCard creditCard);
}
