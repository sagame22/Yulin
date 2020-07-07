package com.product.model;

import java.util.List;

import com.category.model.CategoryVO;

public interface ProductDAO {

	public int getTotal(int cid);
	public void add(ProductVO bean);
	public void update(ProductVO bean);
	public void delete(int id);
	public ProductVO get(int id);
	public List<ProductVO> list(int cid);
	public List<ProductVO> list(int cid, int start, int count);
	public List<ProductVO> list();
	public List<ProductVO> list(int start, int count);
	public void fill(List<CategoryVO> cs);
	public void fill(CategoryVO c);
	public void fillByRow(List<CategoryVO> cs);
	public void setFirstProductImage(ProductVO p);
	public void setSaleAndReviewNumber(ProductVO p);
	public void setSaleAndReviewNumber(List<ProductVO> products);
	public List<ProductVO> search(String keyword, int start, int count);
}
