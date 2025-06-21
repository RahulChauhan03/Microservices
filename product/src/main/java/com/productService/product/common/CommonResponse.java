package com.productService.product.common;

import lombok.Data;

@Data
public class CommonResponse<T> {

	T data;
	String successMessages;
	Integer status;
	boolean success;
}
