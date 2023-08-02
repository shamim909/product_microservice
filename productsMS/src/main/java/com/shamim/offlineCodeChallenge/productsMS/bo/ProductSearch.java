package com.shamim.offlineCodeChallenge.productsMS.bo;

import java.math.BigDecimal;
import java.util.Date;

import lombok.ToString;

@ToString
public class ProductSearch {

	private String productName;
	private BigDecimal minPrice;
	private BigDecimal maxPrice;
	private Date minPostedDate;
	private Date maxPoistedDate;
	private int onlyActiveProduct = 1;

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(BigDecimal minPrice) {
		this.minPrice = minPrice;
	}

	public BigDecimal getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(BigDecimal maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Date getMinPostedDate() {
		return minPostedDate;
	}

	public void setMinPostedDate(Date minPostedDate) {
		this.minPostedDate = minPostedDate;
	}

	public Date getMaxPoistedDate() {
		return maxPoistedDate;
	}

	public void setMaxPoistedDate(Date maxPoistedDate) {
		this.maxPoistedDate = maxPoistedDate;
	}

	public int getOnlyActiveProduct() {
		return onlyActiveProduct;
	}

	public void setOnlyActiveProduct(int onlyActiveProduct) {
		this.onlyActiveProduct = onlyActiveProduct;
	}

}
