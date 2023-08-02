package com.shamim.offlineCodeChallenge.productsMS.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shamim.offlineCodeChallenge.productsMS.bo.ProductApprovalRequest;
import com.shamim.offlineCodeChallenge.productsMS.service.ApprovalQueueService;
import com.shamim.offlineCodeChallenge.productsMS.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ApprovalContraller {
	
	Logger logger = LoggerFactory.getLogger(ApprovalContraller.class);

	@Autowired
	ProductService productService;

	@Autowired
	ApprovalQueueService approvalQueueService;

	@RequestMapping(value = "/approval-queue", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ProductApprovalRequest> getApprovalRequests() {
		logger.trace("find all requests initiated");
		return approvalQueueService.findAllProductApprovalRequest();
	}

	@RequestMapping(value = "/approval-queue/{approvalId}/reject", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> rejectRequest(@PathVariable("approvalId") long approvalId) {
		logger.trace("Reject request initiated with id "+approvalId);

		return new ResponseEntity<Boolean>(approvalQueueService.rejectProductApprovalRequest(approvalId),
				new HttpHeaders(), HttpStatus.CREATED);

	}

	@RequestMapping(value = "/approval-queue/{approvalId}/approve", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> approveRequest(@PathVariable("approvalId") long approvalId) {
		
		logger.trace("Approve request initiated with id "+approvalId);

		return new ResponseEntity<Boolean>(approvalQueueService.approveProductApprovalRequest(approvalId),
				new HttpHeaders(), HttpStatus.CREATED);
	}

}
