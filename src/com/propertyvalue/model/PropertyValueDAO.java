package com.propertyvalue.model;

import java.util.List;

import com.product.model.ProductVO;

public interface PropertyValueDAO {

	public int getTotal();
	public void add(PropertyValueVO bean);
	public void update(PropertyValueVO bean);
	public void delete(int id);
	public PropertyValueVO get(int id);
	public PropertyValueVO get(int ptid, int pid );
	public List<PropertyValueVO> list();
	public void init(ProductVO p);
	public List<PropertyValueVO> list(int pid);
	
}
