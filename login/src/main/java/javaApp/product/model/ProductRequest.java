package javaApp.product.model;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    
    @NotBlank
    private String name;

    @NotBlank
    private String category;

    private MultipartFile coverImage;
    private String subDescription;
    private Integer available;
    private Integer price;
    private String inventoryType;
    private Boolean newLabelEnabled;
    private String newLabelContent;
    private Boolean saleLabelEnabled;
    private String saleLabelContent;
}