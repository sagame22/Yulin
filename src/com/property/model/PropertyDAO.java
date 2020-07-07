package com.property.model;

import java.util.List;



public interface PropertyDAO {
	
	public int getTotal(int cid);
	public int add(PropertyVO bean);
	public void update(PropertyVO bean);
	public void delete(int id);
	public PropertyVO get(int id);
	public List<PropertyVO> list(int cid);

}
