package com.shamim.offlineCodeChallenge.productsMS.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shamim.offlineCodeChallenge.productsMS.dao.model.ProductMstExtn;

@Repository
public interface ProdcutMstExtRepository extends JpaRepository<ProductMstExtn, Long>  {

}
