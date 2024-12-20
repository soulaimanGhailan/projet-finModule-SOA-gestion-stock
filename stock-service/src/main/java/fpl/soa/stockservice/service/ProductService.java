package fpl.soa.stockservice.service;

import fpl.soa.stockservice.entities.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> findAll();
    Product reserve(Product desiredProduct, String orderId);
    void cancelReservation(Product productToCancel, String orderId);
    Product save(Product product);
    Product getById(Long id);
}
