package com.comparator;

import java.util.Comparator;

import com.product.model.ProductVO;


public class ProductPriceComparator implements Comparator<ProductVO>{

	@Override
	public int compare(ProductVO p1, ProductVO p2) {
		return (int) (p1.getPromotePrice()-p2.getPromotePrice());
	}

}
