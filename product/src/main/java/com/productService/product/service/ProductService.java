package com.productService.product.service;

import org.springframework.stereotype.Service;

import com.productService.product.DTO.ProductDTO;
import com.productService.product.common.CommonResponse;
import com.productService.product.model.Product;

@Service
public interface ProductService {

	CommonResponse<Long> addUpdateProduct(Product product);

	CommonResponse<ProductDTO>  getProduct(Long productId);

	CommonResponse<Integer> deleteProduct(Long productId);

}
