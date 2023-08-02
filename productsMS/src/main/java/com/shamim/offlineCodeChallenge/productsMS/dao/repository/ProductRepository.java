package com.shamim.offlineCodeChallenge.productsMS.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shamim.offlineCodeChallenge.productsMS.dao.model.ProductMst;

@Repository
public interface ProductRepository extends JpaRepository<ProductMst, Long>,ProductCustomRepository {
	
	List<ProductMst> findByProductStatusOrderByProductActivatedOnDesc(long productStatus);

}