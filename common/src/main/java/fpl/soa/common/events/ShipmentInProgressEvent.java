package fpl.soa.common.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class ShipmentInProgressEvent {
    private String orderId ;
    private String shippingAddress ;
    private String originatingAddress ;
    private LocalDate shipmentInitDate ;
    private LocalDate deliveryExpectedDate ;
    private String customerEmailAddress;
    private String firstname;
    private String lastname;
    private String trackingNum ;
}
