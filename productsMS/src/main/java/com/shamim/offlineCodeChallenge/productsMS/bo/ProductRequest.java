package com.shamim.offlineCodeChallenge.productsMS.bo;

import java.math.BigDecimal;

import lombok.ToString;

@ToString
public class ProductRequest {
	public enum ProductStatus {
		DELETED, ACTIVE, NEW, REJECTED;
	}

	private String name;
	private BigDecimal price;
	private ProductStatus status;

	public int getStatus() {
		switch (status) {
		case DELETED:
			return 0;
		case ACTIVE:
			return 1;
		case NEW:
			return 2;
		case REJECTED:
			return -1;
		}
		return 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setStatus(ProductStatus status) {
		this.status = status;
	}

}
