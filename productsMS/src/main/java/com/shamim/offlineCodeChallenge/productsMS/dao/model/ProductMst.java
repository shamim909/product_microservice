package com.shamim.offlineCodeChallenge.productsMS.dao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
 * The persistent class for the product_mst database table.
 * 
 */

@Entity
@Table(name = "product_mst")
@NamedQuery(name = "ProductMst.findAll", query = "SELECT p FROM ProductMst p")
@ToString
public class ProductMst implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_id")
	private long productId;

	@Column(name = "product_catagory_name")
	private String productCatagoryName;

	@Column(name = "product_catalog_name")
	private String productCatalogName;

	
	@Column(name = "product_activated_on")
	private Date productActivatedOn;

	private String product_Description;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "product_price")
	private BigDecimal productPrice;

	@Column(name = "product_sku")
	private String productSku;

	@Column(name = "product_status")
	private int productStatus;

	// bi-directional many-to-one association to ProductMstExtn
	@OneToMany(mappedBy = "productMst")
	private List<ProductMstExtn> productMstExtns;

	public ProductMst() {
	}

	public long getProductId() {
		return this.productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductCatagoryName() {
		return this.productCatagoryName;
	}

	public void setProductCatagoryName(String productCatagoryName) {
		this.productCatagoryName = productCatagoryName;
	}

	public String getProductCatalogName() {
		return this.productCatalogName;
	}

	public void setProductCatalogName(String productCatalogName) {
		this.productCatalogName = productCatalogName;
	}

	public Date getProductActivatedOn() {
		return this.productActivatedOn;
	}

	public void setProductActivatedOn(Date productActivatedOn) {
		this.productActivatedOn = productActivatedOn;
	}

	public String getProduct_Description() {
		return this.product_Description;
	}

	public void setProduct_Description(String product_Description) {
		this.product_Description = product_Description;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getProductPrice() {
		return this.productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductSku() {
		return this.productSku;
	}

	public void setProductSku(String productSku) {
		this.productSku = productSku;
	}

	public int getProductStatus() {
		return this.productStatus;
	}

	public void setProductStatus(int productStatus) {
		this.productStatus = productStatus;
	}

	public List<ProductMstExtn> getProductMstExtns() {
		return this.productMstExtns;
	}

	public void setProductMstExtns(List<ProductMstExtn> productMstExtns) {
		this.productMstExtns = productMstExtns;
	}

	public ProductMstExtn addProductMstExtn(ProductMstExtn productMstExtn) {
		getProductMstExtns().add(productMstExtn);
		productMstExtn.setProductMst(this);

		return productMstExtn;
	}

	public ProductMstExtn removeProductMstExtn(ProductMstExtn productMstExtn) {
		getProductMstExtns().remove(productMstExtn);
		productMstExtn.setProductMst(null);

		return productMstExtn;
	}



}