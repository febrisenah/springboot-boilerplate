package javaApp.product.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javaApp.entity.Product;
import javaApp.product.model.ProductRequest;
import javaApp.product.model.ProductResponse;
import javaApp.repository.ProductRepository;
import javaApp.users.service.ValidationService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ValidationService validationService;

    @Autowired
    private ProductRepository productRepository;
    
    @Transactional
    public void addProduct(ProductRequest request) {
        try {
            validationService.validate(request);
            Product product = new Product();
            MultipartFile file = request.getCoverImage();
            String folderPath = System.getProperty("user.dir") + "/uploads";
            Path folder = Paths.get(folderPath);
            if (!Files.exists(folder)) {
                Files.createDirectories(folder);
            }
            Path filePath = folder.resolve(file.getOriginalFilename());
            file.transferTo(filePath.toFile());
            product.setId(UUID.randomUUID());
            product.setPrice(request.getPrice());
            product.setName(request.getName());
            product.setCategory(request.getCategory());
            product.setAvailable(request.getAvailable());
            product.setInventoryType(request.getInventoryType());
            product.setNewLabelContent(request.getNewLabelContent());
            product.setNewLabelEnabled(request.getNewLabelEnabled());
            product.setSaleLabelContent(request.getSaleLabelContent());
            product.setSaleLabelEnabled(request.getSaleLabelEnabled());
            product.setSubDescription(request.getSubDescription());
            product.setCoverUrl(folderPath);
            productRepository.create(product);
        } catch (Exception e) {
            throw new RuntimeException("Error", e);
        }
    }

    public List<ProductResponse> getAllProduct(Product product){
        try {
            return productRepository.getAllProductResponses().stream()
                .map(products -> ProductResponse.builder()
                        .id(products.getId())
                        .name(products.getName())
                        .available(products.getAvailable())
                        .category(products.getCategory())
                        .coverUrl(products.getCoverUrl())
                        .price(products.getPrice())
                        .inventoryType(products.getInventoryType())
                        .newLabelContent(products.getNewLabelContent())
                        .newLabelEnabled(products.getNewLabelEnabled())
                        .saleLabelContent(products.getSaleLabelContent())
                        .saleLabelEnabled(products.getSaleLabelEnabled())
                        .subDescription(products.getSubDescription())
                        .build()
                )
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error", e);
        }
    }
}
