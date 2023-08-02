package com.shamim.offlineCodeChallenge.productsMS.util;

import java.util.Random;

public class ProductUtil {

	public static String generateSKU(String productCatagoryName, String productName) {
		Random random = new Random();
		int ld = random.nextInt(900) + 100;		
		return "SKU" + firstTwo(productCatagoryName) + firstTwo(productName) + ld;
	}

	private static String firstTwo(String str) {
		return str.length() < 2 ? str : str.substring(0, 2);
	}
}
