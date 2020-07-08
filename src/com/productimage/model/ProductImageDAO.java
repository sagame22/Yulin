package com.productimage.model;

import java.util.List;

import com.product.model.ProductVO;

public interface ProductImageDAO {

	public int getTotal();
	public int add(ProductImageVO bean);
	public void update(ProductImageVO bean);
	public void delete(int id);
	public ProductImageVO get(int id);
	public List<ProductImageVO> list(ProductVO p, String type);
	
}
