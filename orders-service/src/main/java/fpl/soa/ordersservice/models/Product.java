package fpl.soa.ordersservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data @AllArgsConstructor @NoArgsConstructor
@Builder
public class Product {
    private String productId;
    private String name;
    private Date addingDate;
    private String brand;
    private Integer quantity; // Optional field (use Integer to allow null values)
    private String originLocation;
    private String description;
    private String category; // Use String to map category during transfer
    private String status; // Use String to map status during transfer
    private Double price; // Optional, simplified for model
    private List<String> colors; // Optional
    private Boolean selected;
    private Price productPrice;// Optional (Boolean allows null)
    private List<String> productImagesBas64 = new ArrayList<>(); // Optional
    private Dimension dimension;
}

