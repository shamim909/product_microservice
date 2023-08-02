package com.shamim.offlineCodeChallenge.productsMS.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shamim.offlineCodeChallenge.productsMS.dao.model.ProductUpdateRequestTmp;
@Repository
public interface ProductUpdateRequestTmpRepository extends JpaRepository<ProductUpdateRequestTmp, Long>  {
   List<ProductUpdateRequestTmp> findByApprovalId(long approvalId);
}
