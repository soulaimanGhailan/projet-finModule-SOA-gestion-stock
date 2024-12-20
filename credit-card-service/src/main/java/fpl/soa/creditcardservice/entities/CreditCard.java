package fpl.soa.creditcardservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Data
public class CreditCard {
    @Id
    private String cardId ;
    private String cardHolderName ;
    private String expirationMonth ;
    private String expirationYear ;
    private String cvv ;
    private String cardNum ;
    private String customerId ;
}
