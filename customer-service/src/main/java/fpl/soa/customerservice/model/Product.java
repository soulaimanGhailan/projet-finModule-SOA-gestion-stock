package fpl.soa.customerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
@Builder
public class Product {
    private String productId ;
    private String name;
    private Price productPrice;
    private  String originLocation;
    private String pickedColor ;
    private List<String> productImagesBas64;

}
