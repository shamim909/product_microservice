package com.shamim.offlineCodeChallenge.productsMS.dao.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.ToString;


/**
 * The persistent class for the product_chng_hist_txn database table.
 * 
 */
@ToString
@Entity
@Table(name="product_chng_hist_txn")
@NamedQuery(name="ProductChngHistTxn.findAll", query="SELECT p FROM ProductChngHistTxn p")
public class ProductChngHistTxn implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="product_chng_record_id")
	private long productChngRecordId;

	@Column(name="product_chng_hist_details")
	private String productChngHistDetails;

	@Column(name="product_chng_hist_event")
	private String productChngHistEvent;

	@Column(name="product_chng_performed_by")
	private String productChngPerformedBy;

	
	@Column(name="product_chng_performed_on")
	private Date productChngPerformedOn;

	@Column(name="product_chng_status")
	private int productChngStatus;

	@Column(name="product_id")
	private long productId;

	//bi-directional many-to-one association to ApprovalQueueTxn
	@JsonIgnore
	@OneToMany(mappedBy="productChngHistTxn")
	private List<ApprovalQueueTxn> approvalQueueTxns;

	public ProductChngHistTxn() {
	}

	public long getProductChngRecordId() {
		return this.productChngRecordId;
	}

	public void setProductChngRecordId(long productChngRecordId) {
		this.productChngRecordId = productChngRecordId;
	}

	public String getProductChngHistDetails() {
		return this.productChngHistDetails;
	}

	public void setProductChngHistDetails(String productChngHistDetails) {
		this.productChngHistDetails = productChngHistDetails;
	}

	public String getProductChngHistEvent() {
		return this.productChngHistEvent;
	}

	public void setProductChngHistEvent(String productChngHistEvent) {
		this.productChngHistEvent = productChngHistEvent;
	}

	public String getProductChngPerformedBy() {
		return this.productChngPerformedBy;
	}

	public void setProductChngPerformedBy(String productChngPerformedBy) {
		this.productChngPerformedBy = productChngPerformedBy;
	}

	public Date getProductChngPerformedOn() {
		return this.productChngPerformedOn;
	}

	public void setProductChngPerformedOn(Date productChngPerformedOn) {
		this.productChngPerformedOn = productChngPerformedOn;
	}

	public int getProductChngStatus() {
		return this.productChngStatus;
	}

	public void setProductChngStatus(int productChngStatus) {
		this.productChngStatus = productChngStatus;
	}

	public long getProductId() {
		return this.productId;
	}

	public void setProductId(long l) {
		this.productId = l;
	}

	public List<ApprovalQueueTxn> getApprovalQueueTxns() {
		return this.approvalQueueTxns;
	}

	public void setApprovalQueueTxns(List<ApprovalQueueTxn> approvalQueueTxns) {
		this.approvalQueueTxns = approvalQueueTxns;
	}

	public ApprovalQueueTxn addApprovalQueueTxn(ApprovalQueueTxn approvalQueueTxn) {
		getApprovalQueueTxns().add(approvalQueueTxn);
		approvalQueueTxn.setProductChngHistTxn(this);

		return approvalQueueTxn;
	}

	public ApprovalQueueTxn removeApprovalQueueTxn(ApprovalQueueTxn approvalQueueTxn) {
		getApprovalQueueTxns().remove(approvalQueueTxn);
		approvalQueueTxn.setProductChngHistTxn(null);

		return approvalQueueTxn;
	}

	public ProductChngHistTxn(String productChngHistDetails, String productChngHistEvent, String productChngPerformedBy,
			Date productChngPerformedOn, int productChngStatus, long productId) {
		super();
		this.productChngHistDetails = productChngHistDetails;
		this.productChngHistEvent = productChngHistEvent;
		this.productChngPerformedBy = productChngPerformedBy;
		this.productChngPerformedOn = productChngPerformedOn;
		this.productChngStatus = productChngStatus;
		this.productId = productId;
	}
	
	

}