package fpl.soa.creditcardservice.web;

import fpl.soa.creditcardservice.entities.CreditCard;
import fpl.soa.creditcardservice.service.CreditCardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/creditCard")
public class CreditCardRestController {

    private CreditCardService creditCardService;

    public CreditCardRestController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @GetMapping("{customerId}")
    public CreditCard getCreditCard(@PathVariable String customerId) {
        return creditCardService.getCreditCardById(customerId) ;
    }

    @PostMapping
    public CreditCard addCreditCard(@RequestBody CreditCard creditCard) {
        return creditCardService.createCreditCard(creditCard) ;
    }

}
