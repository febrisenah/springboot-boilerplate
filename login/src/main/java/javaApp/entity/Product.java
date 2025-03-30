package javaApp.entity;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "\"product\"")
@Getter
@Setter
public class Product {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    private String name;
    private String category;
    private String subDescription;
    private String coverUrl;
    private Integer available;
    private Integer price;
    private String inventoryType;
    private Boolean newLabelEnabled;
    private String newLabelContent;
    private Boolean saleLabelEnabled;
    private String saleLabelContent;
}
