package com.shamim.offlineCodeChallenge.productsMS.dao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.ToString;


/**
 * The persistent class for the product_update_request_tmp database table.
 * 
 */
@ToString
@Entity
@Table(name="product_update_request_tmp")
@NamedQuery(name="ProductUpdateRequestTmp.findAll", query="SELECT p FROM ProductUpdateRequestTmp p")
public class ProductUpdateRequestTmp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;

	@Column(name="approval_id")
	private long approvalId;

	@Column(name="is_active")
	private int isActive;

	private String name;

	private BigDecimal price;

	@Column(name="product_id")
	private long productId;

	private int status;

	public ProductUpdateRequestTmp() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getApprovalId() {
		return this.approvalId;
	}

	public void setApprovalId(long l) {
		this.approvalId = l;
	}

	public int getIsActive() {
		return this.isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public long getProductId() {
		return this.productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	

}