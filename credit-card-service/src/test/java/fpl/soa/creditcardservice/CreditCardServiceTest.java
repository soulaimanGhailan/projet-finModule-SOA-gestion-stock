package fpl.soa.creditcardservice;


import fpl.soa.creditcardservice.entities.CreditCard;
import fpl.soa.creditcardservice.repositories.CreditCardRepo;
import fpl.soa.creditcardservice.service.CreditCardServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

public class CreditCardServiceTest {

    @Mock
    private CreditCardRepo creditCardRepo;

    @InjectMocks
    private CreditCardServiceImpl creditCardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    // Test for getCreditCardById
    @Test
    public void testGetCreditCardById_Found() {
        // Arrange
        String customerId = "C123";
        CreditCard creditCard = new CreditCard();
        creditCard.setCustomerId(customerId);
        creditCard.setCardNum("1234-5678-9101-1121");

        when(creditCardRepo.findCreditCardByCustomerId(customerId)).thenReturn(Optional.of(creditCard));

        // Act
        CreditCard result = creditCardService.getCreditCardById(customerId);

        // Assert
        assertNotNull(result);
        assertEquals(customerId, result.getCustomerId());
        verify(creditCardRepo, times(1)).findCreditCardByCustomerId(customerId);
    }

    @Test
    public void testGetCreditCardById_NotFound() {
        // Arrange
        String customerId = "C123";

        when(creditCardRepo.findCreditCardByCustomerId(customerId)).thenReturn(Optional.empty());

        // Act
        CreditCard result = creditCardService.getCreditCardById(customerId);

        // Assert
        assertNotNull(result); // Ensure a new CreditCard object is returned
        assertNull(result.getCardNum()); // Default values expected
        verify(creditCardRepo, times(1)).findCreditCardByCustomerId(customerId);
    }

    // Test for createCreditCard
    @Test
    public void testCreateCreditCard() {
        // Arrange
        CreditCard creditCard = new CreditCard();
        creditCard.setCustomerId("C123");
        creditCard.setCardNum("1234-5678-9101-1121");

        when(creditCardRepo.save(creditCard)).thenReturn(creditCard);

        // Act
        CreditCard result = creditCardService.createCreditCard(creditCard);

        // Assert
        assertNotNull(result);
        assertEquals("C123", result.getCustomerId());
        assertEquals("1234-5678-9101-1121", result.getCardNum());
        verify(creditCardRepo, times(1)).save(creditCard);
    }
}
