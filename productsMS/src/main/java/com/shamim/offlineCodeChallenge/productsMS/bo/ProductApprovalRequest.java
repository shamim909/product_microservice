package com.shamim.offlineCodeChallenge.productsMS.bo;

import com.shamim.offlineCodeChallenge.productsMS.dao.model.ApprovalQueueTxn;
import com.shamim.offlineCodeChallenge.productsMS.dao.model.ProductMst;
import com.shamim.offlineCodeChallenge.productsMS.dao.model.ProductUpdateRequestTmp;

import lombok.ToString;

@ToString
public class ProductApprovalRequest {
	private ApprovalQueueTxn ApprovalRequest;	
	private ProductMst product;
	private ProductUpdateRequestTmp productUpdateRequestTmp;
	public ApprovalQueueTxn getApprovalRequest() {
		return ApprovalRequest;
	}
	public void setApprovalRequest(ApprovalQueueTxn approvalRequest) {
		ApprovalRequest = approvalRequest;
	}
	public ProductMst getProduct() {
		return product;
	}
	public void setProduct(ProductMst product) {
		this.product = product;
	}
	public ProductUpdateRequestTmp getProductUpdateRequestTmp() {
		return productUpdateRequestTmp;
	}
	public void setProductUpdateRequestTmp(ProductUpdateRequestTmp productUpdateRequestTmp) {
		this.productUpdateRequestTmp = productUpdateRequestTmp;
	}
	
	

}
