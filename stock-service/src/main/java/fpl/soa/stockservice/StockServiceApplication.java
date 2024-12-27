package fpl.soa.stockservice;

import fpl.soa.stockservice.entities.Product;
import fpl.soa.stockservice.repository.ProductRepo;
import fpl.soa.stockservice.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class StockServiceApplication {

    private ProductRepo productRepo ;
    private RepositoryRestConfiguration restConfiguration;
    private ProductService productService;

    public StockServiceApplication(ProductRepo productRepo, RepositoryRestConfiguration restConfiguration, ProductService productService) {
        this.productRepo = productRepo;
        this.restConfiguration = restConfiguration;
        this.productService = productService;
    }

    public static void main(String[] args) {
        SpringApplication.run(StockServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(){
        return args -> {
            restConfiguration.exposeIdsFor(Product.class) ;
            this.productService.initProduct();
        };
    }

}
