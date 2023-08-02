package com.shamim.offlineCodeChallenge.productsMS.dao.model;

import java.io.Serializable;
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
 * The persistent class for the product_att_chnged_hist_details database table.
 * 
 */
@ToString
@Entity
@Table(name="product_att_chnged_hist_details")
@NamedQuery(name="ProductAttChngedHistDetail.findAll", query="SELECT p FROM ProductAttChngedHistDetail p")
public class ProductAttChngedHistDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="product_att_chng_d_id")
	private long productAttChngDId;

	@Column(name="is_active")
	private int isActive;

	@Column(name="product_att")
	private String productAtt;

	@Column(name="product_att_chng_txnn_id")
	private long productAttChngTxnId;

	@Column(name="product_att_old_val")
	private String productAttOldVal;
	
	@Column(name="product_att_new_val")
	private String productAttNewVal;

	@Column(name="product_id")
	private long productId;

	public ProductAttChngedHistDetail() {
	}

	public long getProductAttChngDId() {
		return this.productAttChngDId;
	}

	public void setProductAttChngDId(long productAttChngDId) {
		this.productAttChngDId = productAttChngDId;
	}

	public int getIsActive() {
		return this.isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getProductAtt() {
		return this.productAtt;
	}

	public void setProductAtt(String productAtt) {
		this.productAtt = productAtt;
	}

	public long getProductAttChngTxnId() {
		return this.productAttChngTxnId;
	}

	public void setProductAttChngTxnId(long productAttChngTxnId) {
		this.productAttChngTxnId = productAttChngTxnId;
	}

	public String getProductAttOldVal() {
		return this.productAttOldVal;
	}

	public void setProductAttOldVal(String productAttOldVal) {
		this.productAttOldVal = productAttOldVal;
	}

	public long getProductId() {
		return this.productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductAttNewVal() {
		return productAttNewVal;
	}

	public void setProductAttNewVal(String productAttNewVal) {
		this.productAttNewVal = productAttNewVal;
	}

	public ProductAttChngedHistDetail(int isActive, String productAtt, String productAttOldVal, String productAttNewVal,
			long productId) {
		super();
		this.isActive = isActive;
		this.productAtt = productAtt;
		this.productAttOldVal = productAttOldVal;
		this.productAttNewVal = productAttNewVal;
		this.productId = productId;
	}

	

}