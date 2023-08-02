package com.shamim.offlineCodeChallenge.productsMS.dao.repository;

import java.util.List;

import com.shamim.offlineCodeChallenge.productsMS.bo.ProductSearch;
import com.shamim.offlineCodeChallenge.productsMS.dao.model.ProductMst;

public interface ProductCustomRepository {
	List<ProductMst> findByCustomSearch(ProductSearch ps);
}
