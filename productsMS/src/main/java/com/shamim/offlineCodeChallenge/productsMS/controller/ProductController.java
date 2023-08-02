package com.shamim.offlineCodeChallenge.productsMS.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shamim.offlineCodeChallenge.productsMS.bo.ProductRequest;
import com.shamim.offlineCodeChallenge.productsMS.bo.ProductSearch;
import com.shamim.offlineCodeChallenge.productsMS.dao.model.ProductMst;
import com.shamim.offlineCodeChallenge.productsMS.exception.ApiError;
import com.shamim.offlineCodeChallenge.productsMS.service.ApprovalQueueService;
import com.shamim.offlineCodeChallenge.productsMS.service.ProductService;

@RestController
@RequestMapping("/api/")
public class ProductController {

	Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	ProductService productService;

	@Autowired
	ApprovalQueueService approvalQueueService;

	@GetMapping("/products")
	public List<ProductMst> getAllActiveProducts() {

		logger.trace("Find all active product initiated");

		return productService.findAllActiveProducts();
	}

	@RequestMapping(value = "/product", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createProduct(@RequestBody ProductMst productMst) {

		logger.trace("Create product requested + " + productMst);

		long ret = productService.createProduct(productMst);
		
		if (ret == -1) {
			return new ResponseEntity<Object>(
					new ApiError(HttpStatus.BAD_REQUEST, "OverPriced",
							"Product Can not be created as price is greater than 10000"),
					new HttpHeaders(), HttpStatus.CREATED);

		}
		productMst.setProductId(ret);
		
		HttpHeaders headers = new HttpHeaders();

		logger.trace("return response" + ret);

		ResponseEntity<Object> resEntity = new ResponseEntity<>(productMst, headers, HttpStatus.CREATED);

		return resEntity;
	}

	@RequestMapping(value = "products/search", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProductMst> searchProducts(@RequestBody ProductSearch productSearch) {

		logger.trace("search request received " + productSearch);

		return productService.findAllActiveProductsBy(productSearch);
	}

	@RequestMapping(value = "products/{productId}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> updateProduct(@RequestBody ProductRequest productRequest,
			@PathVariable("productId") Long productId) {

		logger.trace("update product request received " + productRequest);

		return new ResponseEntity<Boolean>(productService.updateProduct(productId, productRequest), new HttpHeaders(),
				HttpStatus.CREATED);
	}

	@RequestMapping(value = "products/{productId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> deleteProduct(@PathVariable("productId") Long productId) {

		logger.trace("delete product request received " + productId);

		return new ResponseEntity<Boolean>(productService.removeProduct(productId), new HttpHeaders(),
				HttpStatus.CREATED);
	}

}
