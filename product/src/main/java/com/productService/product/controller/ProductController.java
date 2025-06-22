package com.productService.product.controller;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.productService.product.DTO.ProductDTO;
import com.productService.product.common.CommonResponse;
import com.productService.product.model.Product;
import com.productService.product.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/save/product")
    public ResponseEntity<CommonResponse<Long>> addUpdateProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.addUpdateProduct(product));
    }

    @GetMapping("/get/product")
    public ResponseEntity<CommonResponse<ProductDTO>> getProduct(@RequestParam("productId") Long productId){
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @GetMapping("/delete/product")
    public ResponseEntity<CommonResponse<Integer>> deleteProduct(@RequestParam("productId") Long productId){
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }
 
}
