package com.shamim.offlineCodeChallenge.productsMS.dao.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
 * The persistent class for the product_mst_extn database table.
 * 
 */
@ToString
@Entity
@Table(name="product_mst_extn")
@NamedQuery(name="ProductMstExtn.findAll", query="SELECT p FROM ProductMstExtn p")
public class ProductMstExtn implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="product_extn_record_id")
	private long productExtnRecordId;

	
	@Column(name="product_att_created_on")
	private Date productAttCreatedOn;

	@Column(name="product_att_name")
	private String productAttName;

	@Column(name="product_att_status")
	private int productAttStatus;

	@Column(name="product_att_value")
	private String productAttValue;

	//bi-directional many-to-one association to ProductMst
	@ManyToOne
	@JoinColumn(name="product_id")
	@JsonIgnore
	private ProductMst productMst;

	public ProductMstExtn() {
	}

	public long getProductExtnRecordId() {
		return this.productExtnRecordId;
	}

	public void setProductExtnRecordId(long productExtnRecordId) {
		this.productExtnRecordId = productExtnRecordId;
	}

	public Date getProductAttCreatedOn() {
		return this.productAttCreatedOn;
	}

	public void setProductAttCreatedOn(Date productAttCreatedOn) {
		this.productAttCreatedOn = productAttCreatedOn;
	}

	public String getProductAttName() {
		return this.productAttName;
	}

	public void setProductAttName(String productAttName) {
		this.productAttName = productAttName;
	}

	public int getProductAttStatus() {
		return this.productAttStatus;
	}

	public void setProductAttStatus(int productAttStatus) {
		this.productAttStatus = productAttStatus;
	}

	public String getProductAttValue() {
		return this.productAttValue;
	}

	public void setProductAttValue(String productAttValue) {
		this.productAttValue = productAttValue;
	}

	

	public ProductMst getProductMst() {
		return this.productMst;
	}

	public void setProductMst(ProductMst productMst) {
		this.productMst = productMst;
	}

}