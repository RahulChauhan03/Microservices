package com.userService.user.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.productService.product.DTO.ProductDTO;
import com.userService.user.common.CommonResponse;
@FeignClient(name = "product", url = "http://localhost:8081") 
public interface ProductClient {


	@GetMapping("/get/product")
	 CommonResponse<ProductDTO> getProductById(@RequestParam("productId") Long id);
}
