package fpl.soa.stockservice.service;

import fpl.soa.stockservice.entities.PageInfo;
import fpl.soa.stockservice.entities.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product reserve(Product desiredProduct, String orderId);
    void cancelReservation(Product productToCancel, String orderId);


    Product createProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(String productId);
    Product getProductById(String productId) ;
    List<Product> getProductsByCategory() ;
    List<Product> getProductsByName(String name) ;
    List<Product> getAllProduct();
    List<Product> getProductPage(int page , int size);
    void initProduct() ;
    PageInfo getProductPageInfo(int size);
}
