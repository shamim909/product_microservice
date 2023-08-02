package com.shamim.offlineCodeChallenge.productsMS.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shamim.offlineCodeChallenge.productsMS.bo.ProductApprovalRequest;
import com.shamim.offlineCodeChallenge.productsMS.dao.model.ApprovalQueueTxn;
import com.shamim.offlineCodeChallenge.productsMS.dao.model.ProductAttChngedHistDetail;
import com.shamim.offlineCodeChallenge.productsMS.dao.model.ProductChngHistTxn;
import com.shamim.offlineCodeChallenge.productsMS.dao.model.ProductMst;
import com.shamim.offlineCodeChallenge.productsMS.dao.model.ProductUpdateRequestTmp;
import com.shamim.offlineCodeChallenge.productsMS.dao.repository.ApprovalQueueRepository;
import com.shamim.offlineCodeChallenge.productsMS.dao.repository.ProductAttChngedHistDetailRepository;
import com.shamim.offlineCodeChallenge.productsMS.dao.repository.ProductChangeHistoryRepository;
import com.shamim.offlineCodeChallenge.productsMS.dao.repository.ProductRepository;
import com.shamim.offlineCodeChallenge.productsMS.dao.repository.ProductUpdateRequestTmpRepository;
import com.shamim.offlineCodeChallenge.productsMS.service.ApprovalQueueService;

@Service
public class ApprovalQueueServiceImpl implements ApprovalQueueService {

	
	Logger logger = LoggerFactory.getLogger(ApprovalQueueService.class);
	
	@Autowired
	ProductRepository productRepository;

	@Autowired
	ProductChangeHistoryRepository productChangeHistoryRepository;

	@Autowired
	ApprovalQueueRepository approvalQueueRepository;

	@Autowired
	ProductUpdateRequestTmpRepository productUpdateRequestTmpRepository;

	@Autowired
	ProductAttChngedHistDetailRepository productAttChngedHistDetailRepository;

	@Override
	public List<ProductApprovalRequest> findAllProductApprovalRequest() {
		
		logger.trace("All Product Approval requested");
		
		List<ProductApprovalRequest> parList = new ArrayList<ProductApprovalRequest>();

		List<ApprovalQueueTxn> aqtList = approvalQueueRepository.findByApprovalStatus(2);

		for (ApprovalQueueTxn approvalQueueTxn : aqtList) {
			ProductApprovalRequest par = new ProductApprovalRequest();
			par.setApprovalRequest(approvalQueueTxn);
			par.setProduct(productRepository.findById(approvalQueueTxn.getProductChngHistTxn().getProductId()).get());
			if ("UPDATE_PRICE".equalsIgnoreCase(approvalQueueTxn.getApprovalReqType())) {
				List<ProductUpdateRequestTmp> purtList = productUpdateRequestTmpRepository
						.findByApprovalId(approvalQueueTxn.getApprovalId());
				if (!purtList.isEmpty()) {
					par.setProductUpdateRequestTmp(purtList.get(0));
				}
			}
			parList.add(par);
		}
		
		logger.trace("Respone"+parList);
		
		return parList;
	}

	@Override
	public boolean approveProductApprovalRequest(long approvalId) {

		ProductApprovalRequest par = findProductApprovalRequestByApprovalId(approvalId);
		if (par != null) {
			ApprovalQueueTxn aqt = par.getApprovalRequest();
			if ("UPDATE_PRICE".equalsIgnoreCase(aqt.getApprovalReqType())) {

				return updatePriceAprovalHandlerpar(par);

			} else if ("CREATE".equalsIgnoreCase(aqt.getApprovalReqType())) {

				return createProductAprovalHandlerpar(par);

			} else if ("REMOVE".equalsIgnoreCase(aqt.getApprovalReqType())) {

				return removeProductAprovalHandlerpar(par);
			}

		}

		return false;
	}

	private boolean updatePriceAprovalHandlerpar(ProductApprovalRequest par) {
		// update the product price in master table and create change details log with
		// old and new value
		
		ProductMst pm = par.getProduct();
		
		ProductAttChngedHistDetail pachd = new ProductAttChngedHistDetail(1, "productPrice",
				pm.getProductPrice().toString(), par.getProductUpdateRequestTmp().getPrice().toString(),
				pm.getProductId());
		
		pm.setProductPrice(par.getProductUpdateRequestTmp().getPrice());

		ApprovalQueueTxn aqt = par.getApprovalRequest();
		aqt.setApprovalApprovedBy("APPROVER");
		aqt.setApprovalActOn(new Date());
		aqt.setApprovalStatus(1);

		ProductChngHistTxn pcht = new ProductChngHistTxn("Price change request approved by approver",
				"UPDATE_PRICE_APPROVED", "APPROVER", new Date(), 1, pm.getProductId());

		try {
			pcht = productChangeHistoryRepository.save(pcht);
			pachd.setProductAttChngTxnId(pcht.getProductChngRecordId());
			aqt.setProductChngHistTxn(pcht);
			aqt = approvalQueueRepository.save(aqt);
			productRepository.save(pm);
			productAttChngedHistDetailRepository.save(pachd);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean createProductAprovalHandlerpar(ProductApprovalRequest par) {
		
		ProductMst pm = par.getProduct();
		pm.setProductStatus(1); // setting product status as active
		pm.setProductActivatedOn(new Date());
		
		ApprovalQueueTxn aqt = par.getApprovalRequest();
		aqt.setApprovalApprovedBy("APPROVER");
		aqt.setApprovalActOn(new Date());
		aqt.setApprovalStatus(1);

		ProductChngHistTxn pcht = new ProductChngHistTxn("Create product request approved by approver",
				"CREATE_PRODUCT_APPROVED", "APPROVER", new Date(), 1, pm.getProductId());

		try {
			
			pcht = productChangeHistoryRepository.save(pcht);
			aqt.setProductChngHistTxn(pcht);
			aqt = approvalQueueRepository.save(aqt);
			productRepository.save(pm);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	private boolean removeProductAprovalHandlerpar(ProductApprovalRequest par) {
		
		ProductMst pm = par.getProduct();
		pm.setProductStatus(0); // setting product status as deactive for soft delete

		ApprovalQueueTxn aqt = par.getApprovalRequest();
		aqt.setApprovalApprovedBy("APPROVER");
		aqt.setApprovalActOn(new Date());
		aqt.setApprovalStatus(1);

		ProductChngHistTxn pcht = new ProductChngHistTxn("Create product request approved by approver",
				"CREATE_PRODUCT_APPROVED", "APPROVER", new Date(), 1, pm.getProductId());

		try {
			pcht = productChangeHistoryRepository.save(pcht);

			aqt.setProductChngHistTxn(pcht);
			aqt = approvalQueueRepository.save(aqt);
			productRepository.save(pm);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean rejectProductApprovalRequest(long approvalId) {
		try {

			Optional<ApprovalQueueTxn> aqtOp = approvalQueueRepository.findById(approvalId);
			if (aqtOp.isPresent()) {
				ApprovalQueueTxn aqt = aqtOp.get();
				approvalQueueRepository.updateStatus(approvalId, -1);
				ProductChngHistTxn pcht = new ProductChngHistTxn(aqt.getApprovalReqType() + " Request Rejected",
						"RequestRejected", "Approver", new Date(), 1, aqt.getProductChngHistTxn().getProductId());
				pcht = productChangeHistoryRepository.save(pcht);
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	// find a single approval request with all its associated objects

	public ProductApprovalRequest findProductApprovalRequestByApprovalId(long approvalId) {

		Optional<ApprovalQueueTxn> aqtOp = approvalQueueRepository.findById(approvalId);

		if (aqtOp.isPresent()) {

			ApprovalQueueTxn approvalQueueTxn = aqtOp.get();
			ProductApprovalRequest par = new ProductApprovalRequest();
			par.setApprovalRequest(approvalQueueTxn);
			par.setProduct(productRepository.findById(approvalQueueTxn.getProductChngHistTxn().getProductId()).get());
			if ("UPDATE_PRICE".equalsIgnoreCase(approvalQueueTxn.getApprovalReqType())) {
				List<ProductUpdateRequestTmp> purtList = productUpdateRequestTmpRepository
						.findByApprovalId(approvalQueueTxn.getApprovalId());
				if (!purtList.isEmpty()) {
					par.setProductUpdateRequestTmp(purtList.get(0));
				}
			}
			return par;
		}
		return null;
	}

}
