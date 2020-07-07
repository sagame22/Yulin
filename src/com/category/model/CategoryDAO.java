package com.category.model;

import java.util.List;

public interface CategoryDAO {
	
	public int getTotal();
	public int add(CategoryVO bean);
	public void update(CategoryVO bean);
	public void delete(int id);
	public CategoryVO get(int id);
	public List<CategoryVO> list();
}
