package fpl.soa.stockservice.entities;

import fpl.soa.stockservice.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class Price {
    private Currency currency ;
    private long price ;
    private String symbol ;
}
