package fpl.soa.creditcardservice;

import fpl.soa.creditcardservice.entities.CreditCard;
import fpl.soa.creditcardservice.service.CreditCardService;
import fpl.soa.creditcardservice.web.CreditCardRestController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CreditCardRestControllerTest {

    @Mock
    private CreditCardService creditCardService;

    @InjectMocks
    private CreditCardRestController creditCardRestController;

    private MockMvc mockMvc;

    private CreditCard creditCard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(creditCardRestController).build();

        // Sample CreditCard object
        creditCard = new CreditCard("1", "John Doe", "12", "2025", "123", "1234567890123456", "cust123");
    }

    @Test
    void testGetCreditCard() throws Exception {
        // Arrange
        String customerId = "cust123";
        when(creditCardService.getCreditCardById(customerId)).thenReturn(creditCard);

        // Act & Assert
        mockMvc.perform(get("/api/v1/creditCard/{customerId}", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardId").value(creditCard.getCardId()))
                .andExpect(jsonPath("$.cardHolderName").value(creditCard.getCardHolderName()))
                .andExpect(jsonPath("$.expirationMonth").value(creditCard.getExpirationMonth()))
                .andExpect(jsonPath("$.expirationYear").value(creditCard.getExpirationYear()))
                .andExpect(jsonPath("$.cvv").value(creditCard.getCvv()))
                .andExpect(jsonPath("$.cardNum").value(creditCard.getCardNum()))
                .andExpect(jsonPath("$.customerId").value(creditCard.getCustomerId()));

        // Verify interaction
        verify(creditCardService, times(1)).getCreditCardById(customerId);
    }

    @Test
    void testAddCreditCard() throws Exception {
        // Arrange
        when(creditCardService.createCreditCard(any(CreditCard.class))).thenReturn(creditCard);

        // Act & Assert
        mockMvc.perform(post("/api/v1/creditCard")
                        .contentType("application/json")
                        .content("{\"cardId\":\"1\",\"cardHolderName\":\"John Doe\",\"expirationMonth\":\"12\",\"expirationYear\":\"2025\",\"cvv\":\"123\",\"cardNum\":\"1234567890123456\",\"customerId\":\"cust123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cardId").value(creditCard.getCardId()))
                .andExpect(jsonPath("$.cardHolderName").value(creditCard.getCardHolderName()))
                .andExpect(jsonPath("$.expirationMonth").value(creditCard.getExpirationMonth()))
                .andExpect(jsonPath("$.expirationYear").value(creditCard.getExpirationYear()))
                .andExpect(jsonPath("$.cvv").value(creditCard.getCvv()))
                .andExpect(jsonPath("$.cardNum").value(creditCard.getCardNum()))
                .andExpect(jsonPath("$.customerId").value(creditCard.getCustomerId()));

        // Verify interaction
        verify(creditCardService, times(1)).createCreditCard(any(CreditCard.class));
    }
}
