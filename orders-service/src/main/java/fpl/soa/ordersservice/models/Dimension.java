package fpl.soa.ordersservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class Dimension {
    private double height ;
    private double width ;
    private double larger ;
    private double weight ;
}
