package com.comparator;

import java.util.Comparator;

import com.product.model.ProductVO;


public class ProductAllComparator implements Comparator<ProductVO>{

	@Override
	public int compare(ProductVO p1, ProductVO p2) {
		return p2.getReviewCount()*p2.getSaleCount()-p1.getReviewCount()*p2.getSaleCount();
	}

}
