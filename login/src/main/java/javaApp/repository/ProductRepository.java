package javaApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javaApp.entity.Product;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    default Product create(Product product){
        return save(product);
    }

    default List<Product> getAllProductResponses(){
       return findAll();
    }
}