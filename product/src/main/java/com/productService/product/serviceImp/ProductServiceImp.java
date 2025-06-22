package com.productService.product.serviceImp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.productService.product.DTO.ProductDTO;
import com.productService.product.common.CommonResponse;
import com.productService.product.common.HttpStatusCodes;
import com.productService.product.model.Product;
import com.productService.product.repo.ProductRepo;
import com.productService.product.service.ProductService;
import jakarta.transaction.Transactional;
import com.productService.product.common.Constants;

@Service
public class ProductServiceImp implements ProductService {

	@Autowired
	ProductRepo productRepo;

	@Transactional
	@Override
	public CommonResponse<Long> addUpdateProduct(Product product) {
		CommonResponse<Long> response = new CommonResponse<>();

		try {
			Product productToSave;
			if (product.getProductId() != null) {
				productToSave = productRepo.findById(product.getProductId())
						.orElseThrow(() -> new RuntimeException(Constants.PRODUCT_NOT_FOUND + product.getProductId()));
				productToSave.setName(product.getName());
				productToSave.setPrice(product.getPrice());
				productToSave.setDescription(product.getDescription());
				productToSave.setCategory(product.getCategory());
				response.setSuccessMessages(Constants.PRODUCT_UPDATED_SUCCESSFULLY);
			} else {
				productToSave = product;
				response.setSuccessMessages(Constants.PRODUCT_ADDED_SUCCSSFULLY);
			}
			Product savedProduct = productRepo.save(productToSave);
			response.setData(savedProduct.getProductId());
			response.setStatus(HttpStatusCodes.OK);
			response.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			response.setData(null);
			response.setSuccessMessages(Constants.ERROR);
			response.setStatus(HttpStatusCodes.INTERNAL_SERVER_ERROR);
			response.setSuccess(false);
		}

		return response;
	}

	@Override
	public CommonResponse<ProductDTO> getProduct(Long productId) {
		CommonResponse<ProductDTO> response = new CommonResponse<>();
		
		try {
			Optional<Product> product = productRepo.findById(productId);
			if (product.isPresent()) {
				ProductDTO userDto = new ProductDTO(product.get().getName(),product.get().getPrice(),
						product.get().getDescription(),product.get().getCategory());
				response.setData(userDto);
				response.setSuccessMessages(Constants.GET_PRODUCT_SUCCSSFULLY);
			} else {
				response.setData(null);
				response.setSuccessMessages(Constants.PRODUCT_NOT_FOUND + productId);
			}
			response.setStatus(HttpStatusCodes.OK);
			response.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			response.setData(null);
			response.setSuccessMessages(Constants.ERROR);
			response.setStatus(HttpStatusCodes.INTERNAL_SERVER_ERROR);
			response.setSuccess(false);
		}
		
		return response;
	}

	@Override
	public CommonResponse<Integer> deleteProduct(Long productId) {
		CommonResponse<Integer> response = new CommonResponse<>();
		try {
			 
			if (productRepo.existsById(productId)) {
				productRepo.deleteById(productId);
				response.setData(1);
				response.setSuccessMessages(Constants.PRODUCT_DELETED_SUCCSSFULLY);
			} else {
				response.setData(null);
				response.setSuccessMessages(Constants.PRODUCT_NOT_FOUND + productId);
			}
			response.setStatus(HttpStatusCodes.OK);
			response.setSuccess(false);
		} catch (Exception e) {
			e.printStackTrace();
			response.setData(null);
			response.setSuccessMessages(Constants.ERROR);
			response.setStatus(HttpStatusCodes.INTERNAL_SERVER_ERROR);
			response.setSuccess(false);
		}
		return response;
	}

}
