package javaApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javaApp.entity.Product;
import javaApp.product.model.ProductResponse;
import javaApp.users.model.UserResponse;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    default Product create(Product product){
        return save(product);
    }

    default List<ProductResponse> getAllProductResponses(){
       return findAll().stream()
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .available(product.getAvailable())
                        .category(product.getCategory())
                        .coverUrl(product.getCoverUrl())
                        .inventoryType(product.getInventoryType())
                        .newLabelContent(product.getNewLabelContent())
                        .newLabelEnabled(product.getNewLabelEnabled())
                        .saleLabelContent(product.getSaleLabelContent())
                        .saleLabelEnabled(product.getSaleLabelEnabled())
                        .subDescription(product.getSubDescription())
                        .build()
                )
                .collect(Collectors.toList());
    }
}