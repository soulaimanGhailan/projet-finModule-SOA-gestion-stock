package fpl.soa.customerservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Customer {
    private String customerId;
    private String email;
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String shippingAddress;
}
