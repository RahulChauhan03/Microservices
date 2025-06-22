package com.productService.product.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDTO {
 
	private String name;
	private Double price;
	private String description;
	private String category;

}
