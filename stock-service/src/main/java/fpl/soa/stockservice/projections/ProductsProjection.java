package fpl.soa.stockservice.projections;

import fpl.soa.stockservice.entities.Dimension;
import fpl.soa.stockservice.entities.Price;
import fpl.soa.stockservice.entities.Product;
import fpl.soa.stockservice.enums.ProductCategory;
import fpl.soa.stockservice.enums.ProductStatus;
import org.springframework.data.rest.core.config.Projection;

import java.util.Date;
import java.util.List;

@Projection(name = "withPrice", types = { Product.class })
public interface ProductsProjection {
    String getProductId();
    String getName();
    Date getAddingDate();
    int getQuantity() ;
    String getDescription();
    ProductCategory getCategory();
    ProductStatus getStatus();
    List<String> getColors();
    boolean isSelected();
    List<String> getProductImagesBas64();
    Price getProductPrice(); // Include the productPrice field
    String getBrand();
    Dimension getDimension() ;
    String getOriginLocation() ;
}
