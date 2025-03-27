package javaApp.product.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private UUID id;
    private String name;
    private String category;
    private String subDescription;
    private String coverUrl;
    private Integer available;
    private String inventoryType;
    private Boolean newLabelEnabled;
    private String newLabelContent;
    private Boolean saleLabelEnabled;
    private String saleLabelContent;
}
