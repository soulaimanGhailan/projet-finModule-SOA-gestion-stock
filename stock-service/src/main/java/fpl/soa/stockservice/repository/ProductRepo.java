package fpl.soa.stockservice.repository;

import fpl.soa.stockservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepo extends JpaRepository<Product, Long> {
}
