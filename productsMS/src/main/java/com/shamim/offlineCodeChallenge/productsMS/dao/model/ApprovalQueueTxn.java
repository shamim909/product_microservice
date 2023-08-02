package com.shamim.offlineCodeChallenge.productsMS.dao.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.ToString;


/**
 * The persistent class for the approval_queue_txn database table.
 * 
 */
@Entity
@Table(name="approval_queue_txn")
@NamedQuery(name="ApprovalQueueTxn.findAll", query="SELECT a FROM ApprovalQueueTxn a")
@ToString
public class ApprovalQueueTxn implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="approval_id")
	private long approvalId;

	
	@Column(name="approval_act_on")
	private Date approvalActOn;

	@Column(name="approval_approved_by")
	private String approvalApprovedBy;

	
	@Column(name="approval_req_on")
	private Date approvalReqOn;

	@Column(name="approval_req_type")
	private String approvalReqType;

	@Column(name="approval_status")
	private int approvalStatus;

	//bi-directional many-to-one association to ProductChngHistTxn
	@ManyToOne
	@JoinColumn(name="approval_req_details")
	private ProductChngHistTxn productChngHistTxn;

	public ApprovalQueueTxn() {
	}

	public long getApprovalId() {
		return this.approvalId;
	}

	public void setApprovalId(long approvalId) {
		this.approvalId = approvalId;
	}

	public Date getApprovalActOn() {
		return this.approvalActOn;
	}

	public void setApprovalActOn(Date approvalActOn) {
		this.approvalActOn = approvalActOn;
	}

	public String getApprovalApprovedBy() {
		return this.approvalApprovedBy;
	}

	public void setApprovalApprovedBy(String approvalApprovedBy) {
		this.approvalApprovedBy = approvalApprovedBy;
	}

	public Date getApprovalReqOn() {
		return this.approvalReqOn;
	}

	public void setApprovalReqOn(Date approvalReqOn) {
		this.approvalReqOn = approvalReqOn;
	}

	public String getApprovalReqType() {
		return this.approvalReqType;
	}

	public void setApprovalReqType(String approvalReqType) {
		this.approvalReqType = approvalReqType;
	}

	public int getApprovalStatus() {
		return this.approvalStatus;
	}

	public void setApprovalStatus(int approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public ProductChngHistTxn getProductChngHistTxn() {
		return this.productChngHistTxn;
	}

	public void setProductChngHistTxn(ProductChngHistTxn productChngHistTxn) {
		this.productChngHistTxn = productChngHistTxn;
	}

	public ApprovalQueueTxn(Date approvalReqOn, String approvalReqType, int approvalStatus) {
		super();
		
		this.approvalReqOn = approvalReqOn;
		this.approvalReqType = approvalReqType;
		this.approvalStatus = approvalStatus;
	}
	

}