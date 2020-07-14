package com.comparator;

import java.util.Comparator;

import com.product.model.ProductVO;


public class ProductDateComparator implements Comparator<ProductVO>{

	@Override
	public int compare(ProductVO p1, ProductVO p2) {
		return p1.getCreateDate().compareTo(p2.getCreateDate());
	}

}
