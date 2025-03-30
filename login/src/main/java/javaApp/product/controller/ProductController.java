package javaApp.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javaApp.entity.Product;
import javaApp.product.model.ProductRequest;
import javaApp.product.model.ProductResponse;
import javaApp.product.service.ProductService;
import javaApp.users.model.WebResponse;

@RestController
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @PostMapping(path = "/api/product", consumes = "multipart/form-data")
    public WebResponse<String> addProduct(Product product, @ModelAttribute ProductRequest request) {
        productService.addProduct(request);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(path = "/api/product", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<ProductResponse>> getAllUsers(Product product) {
        List<ProductResponse> productResponses = productService.getAllProduct(product);
        return WebResponse.<List<ProductResponse>>builder().data(productResponses).build();
    }
}
