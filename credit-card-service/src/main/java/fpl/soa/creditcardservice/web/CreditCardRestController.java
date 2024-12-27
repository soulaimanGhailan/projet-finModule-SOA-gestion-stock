package fpl.soa.creditcardservice.web;

import fpl.soa.creditcardservice.entities.CreditCard;
import fpl.soa.creditcardservice.service.CreditCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("api/v1/creditCard")
@Tag(name = "Credit Card Management", description = "Operations related to credit cards")
public class CreditCardRestController {
    private final CreditCardService creditCardService;

    public CreditCardRestController(CreditCardService creditCardService) {
        this.creditCardService = creditCardService;
    }

    @Operation(summary = "Retrieve a credit card by customer ID")
    @GetMapping("{customerId}")
    public CreditCard getCreditCard(@PathVariable String customerId) {
        return creditCardService.getCreditCardById(customerId);
    }

    @Operation(summary = "Add a new credit card")
    @PostMapping
    public CreditCard addCreditCard(@RequestBody CreditCard creditCard) {
        return creditCardService.createCreditCard(creditCard);
    }
}
