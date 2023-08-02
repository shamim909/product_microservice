package com.shamim.offlineCodeChallenge.productsMS.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shamim.offlineCodeChallenge.productsMS.bo.ProductRequest;
import com.shamim.offlineCodeChallenge.productsMS.bo.ProductSearch;
import com.shamim.offlineCodeChallenge.productsMS.dao.model.ApprovalQueueTxn;
import com.shamim.offlineCodeChallenge.productsMS.dao.model.ProductChngHistTxn;
import com.shamim.offlineCodeChallenge.productsMS.dao.model.ProductMst;
import com.shamim.offlineCodeChallenge.productsMS.dao.model.ProductMstExtn;
import com.shamim.offlineCodeChallenge.productsMS.dao.model.ProductUpdateRequestTmp;
import com.shamim.offlineCodeChallenge.productsMS.dao.repository.ApprovalQueueRepository;
import com.shamim.offlineCodeChallenge.productsMS.dao.repository.ProdcutMstExtRepository;
import com.shamim.offlineCodeChallenge.productsMS.dao.repository.ProductChangeHistoryRepository;
import com.shamim.offlineCodeChallenge.productsMS.dao.repository.ProductRepository;
import com.shamim.offlineCodeChallenge.productsMS.dao.repository.ProductUpdateRequestTmpRepository;
import com.shamim.offlineCodeChallenge.productsMS.service.ProductService;
import com.shamim.offlineCodeChallenge.productsMS.util.ProductUtil;

@Service
public class ProductServiceImpl implements ProductService {
	
	Logger logger = LoggerFactory.getLogger(ProductService.class);

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProductChangeHistoryRepository productChangeHistoryRepository;

	@Autowired
	ApprovalQueueRepository approvalQueueRepository;

	@Autowired
	ProductUpdateRequestTmpRepository productUpdateRequestTmpRepository;

	@Autowired
	ProdcutMstExtRepository prodcutMstExtRepository;

	@Override
	public List<ProductMst> findAllActiveProducts() {

		return productRepository.findByProductStatusOrderByProductActivatedOnDesc(1);
	}

	@Override
	public List<ProductMst> findAllActiveProductsBy(ProductSearch ps) {

		return productRepository.findByCustomSearch(ps);
	}

	@Override
	public long createProduct(ProductMst product) {
		
		logger.trace(""+product);
		
		BigDecimal minAlimit = new BigDecimal(5000);
		BigDecimal zero = new BigDecimal(0);
		BigDecimal maxAlimit = new BigDecimal(10000);
		
		if (product != null) {
			
			product.setProductSku(ProductUtil.generateSKU(product.getProductCatagoryName(), product.getProductName()));
			
			if (product.getProductPrice().compareTo(zero) < 0)
				return -1;
			else if (product.getProductPrice().compareTo(minAlimit) <= 0) {
				
				
				product.setProductActivatedOn(new Date());
				product.setProductStatus(1); // active product
				ProductMst pm = productRepository.save(product);
				ProductChngHistTxn pcht = new ProductChngHistTxn(
						"Auto approved as product price is within the auto aproval limit", "Created", "System",
						new Date(), 1, product.getProductId());
				pcht = productChangeHistoryRepository.save(pcht);

				List<ProductMstExtn> pmelist = new ArrayList<ProductMstExtn>();
				for (ProductMstExtn pme : product.getProductMstExtns()) {
					pme.setProductMst(pm);
					pmelist.add(pme);
				}
				
				prodcutMstExtRepository.saveAll(pmelist);

			} else if (product.getProductPrice().compareTo(maxAlimit) <= 0) {
				product.setProductActivatedOn(new Date());
				product.setProductStatus(2); // under approval workflow
				ProductMst pm = productRepository.save(product); // save product

				List<ProductMstExtn> pmelist = new ArrayList<ProductMstExtn>();
				for (ProductMstExtn pme : product.getProductMstExtns()) {
					pme.setProductMst(pm);
					pmelist.add(pme);
				}
				prodcutMstExtRepository.saveAll(pmelist); // save product extension

				ProductChngHistTxn pcht = new ProductChngHistTxn("Product approval request created",
						"ApprovalRequested", "System", new Date(), 1, product.getProductId());
				ApprovalQueueTxn aqt = new ApprovalQueueTxn(new Date(), "CREATE", 2);
//				List<ApprovalQueueTxn> aqtList = new ArrayList<ApprovalQueueTxn>();
//				aqtList.add(aqt);
//				pcht.setApprovalQueueTxns(aqtList);
				pcht = productChangeHistoryRepository.save(pcht);
				aqt.setProductChngHistTxn(pcht);
				approvalQueueRepository.save(aqt);
			} else {
				return -1;
			}

		}
		return 0;
	}

	@Override
	public boolean updateProduct(Long productId, ProductRequest product) {
		Optional<ProductMst> pm = productRepository.findById(productId);
		if (pm.isPresent()) {
			ProductMst pmo = pm.get();
			pmo.setProductName(product.getName());
			pmo.setProductStatus(product.getStatus()); // I do not understand why we need to change status through
														// update api. FYI we have a delete api
			if (product.getPrice() != null) {
				BigDecimal priceDiff;
				int diff = pmo.getProductPrice().compareTo(product.getPrice());

				if (diff == -1) {
					priceDiff = product.getPrice().subtract(pmo.getProductPrice());
				} else if (diff == 1) {
					priceDiff = pmo.getProductPrice().subtract(product.getPrice());
				} else {
					priceDiff = new BigDecimal(0.0);
				}
				
				logger.trace("=================================>>"+priceDiff+"===>exp: "+priceDiff.compareTo(pmo.getProductPrice().divide(new BigDecimal(2.0))));
				
				if (priceDiff.compareTo(pmo.getProductPrice().divide(new BigDecimal(2.0))) == 1) {
					pmo.setProductPrice(product.getPrice());
					// APPROVAL FLOW
					ApprovalQueueTxn aqt = new ApprovalQueueTxn();
					aqt.setApprovalReqOn(new Date());
					aqt.setApprovalReqType("UPDATE_PRICE");
					aqt.setApprovalStatus(2);

					// -- approval request created

					// -- to be updated data pushed

					// change log push
					ProductChngHistTxn pct = new ProductChngHistTxn();
					pct.setProductChngPerformedBy("System");
					pct.setProductId(pmo.getProductId());
					pct.setProductChngHistEvent("UPDATE_PRICE");
					pct.setProductChngPerformedOn(new Date());
					pct.setProductChngStatus(1);
					pct.setProductChngHistDetails("Price update request created");
//					List<ApprovalQueueTxn> aqtList = new ArrayList<ApprovalQueueTxn>();
//					aqtList.add(aqt);
//					pct.setApprovalQueueTxns(aqtList);
					pct = productChangeHistoryRepository.save(pct);

					aqt.setProductChngHistTxn(pct);
					aqt = approvalQueueRepository.save(aqt);

					ProductUpdateRequestTmp put = new ProductUpdateRequestTmp();
					put.setApprovalId(aqt.getApprovalId());
					put.setProductId(pmo.getProductId());
					put.setStatus(product.getStatus());
					put.setName(product.getName());
					put.setPrice(product.getPrice());
					put.setIsActive(1);
					productUpdateRequestTmpRepository.save(put);

				} else {
					pmo.setProductPrice(product.getPrice());
					ProductChngHistTxn pct = new ProductChngHistTxn();
					pct.setProductChngPerformedBy("System");
					pct.setProductId(pmo.getProductId());
					pct.setProductChngHistEvent("UPDATE_PRICE");
					pct.setProductChngPerformedOn(new Date());
					pct.setProductChngStatus(1);
					pct.setProductChngHistDetails("Price updated");//
					pct = productChangeHistoryRepository.save(pct);
					productRepository.save(pmo);
				}
			}
		}
		return true;
	}

	@Override
	public boolean removeProduct(long productID) {
		Optional<ProductMst> pmL = productRepository.findById(productID);
		if (pmL.isEmpty()) {
			return false;
		}else {
			ProductMst pm=pmL.get();
			
			
			ProductChngHistTxn pcht = new ProductChngHistTxn("Product remove request created",
					"RemoveApprovalRequested", "System", new Date(), 1, pm.getProductId());
			ApprovalQueueTxn aqt = new ApprovalQueueTxn(new Date(), "REMOVE", 2);
			pcht = productChangeHistoryRepository.save(pcht);
			aqt.setProductChngHistTxn(pcht);
			approvalQueueRepository.save(aqt);
			
			return true;
		}
	}

	
}
