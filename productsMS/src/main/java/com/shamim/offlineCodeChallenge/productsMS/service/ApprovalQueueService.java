package com.shamim.offlineCodeChallenge.productsMS.service;

import java.util.List;

import com.shamim.offlineCodeChallenge.productsMS.bo.ProductApprovalRequest;

public interface ApprovalQueueService {
	public List<ProductApprovalRequest> findAllProductApprovalRequest();
	public boolean approveProductApprovalRequest(long approvalId);
	public boolean rejectProductApprovalRequest(long approvalId);
}
