package fpl.soa.stockservice.service;

import fpl.soa.common.exceptions.ProductInsufficientQuantityException;
import fpl.soa.stockservice.entities.Product;
import fpl.soa.stockservice.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepo productRepository;

    public ProductServiceImpl(ProductRepo productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product reserve(Product desiredProduct, String orderId) {
        System.out.println(desiredProduct);
        Product productEntity = productRepository.findById(desiredProduct.getId()).orElseThrow(() -> new RuntimeException("product not found"));
        if (desiredProduct.getQuantity() > productEntity.getQuantity()) {
            throw new ProductInsufficientQuantityException(productEntity.getId(), orderId);
        }

        productEntity.setQuantity(productEntity.getQuantity() - desiredProduct.getQuantity());
        return productRepository.save(productEntity);
    }

    @Override
    public void cancelReservation(Product productToCancel, String orderId) {
        Product productEntity = productRepository.findById(productToCancel.getId()).orElseThrow();
        productEntity.setQuantity(productEntity.getQuantity() + productToCancel.getQuantity());
        productRepository.save(productEntity);
    }

    @Override
    public Product save(Product product) {
        Product productEntity = new Product();
        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice());
        productEntity.setQuantity(product.getQuantity());
        return productRepository.save(productEntity);
    }

    @Override
    public Product getById( Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("product not found"));
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll().stream().collect(Collectors.toList());
    }
}
