package com.shamim.offlineCodeChallenge.productsMS.service;

import java.util.List;

import com.shamim.offlineCodeChallenge.productsMS.bo.ProductApprovalRequest;
import com.shamim.offlineCodeChallenge.productsMS.bo.ProductRequest;
import com.shamim.offlineCodeChallenge.productsMS.bo.ProductSearch;
import com.shamim.offlineCodeChallenge.productsMS.dao.model.ProductMst;

public interface ProductService {
	public List<ProductMst> findAllActiveProducts();
	public List<ProductMst> findAllActiveProductsBy(ProductSearch ps);
	public long createProduct(ProductMst product);
	public boolean updateProduct(Long productId, ProductRequest product);
	public boolean removeProduct(long productID);
	

}
