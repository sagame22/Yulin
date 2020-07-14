package com.review.model;

import java.util.List;

public interface ReviewDAO {

	public int getTotal();
	public int getTotal(int pid);
	public void add(ReviewVO bean);
	public void update(ReviewVO bean);
	public void delete(int id);
	public ReviewVO get(int id);
	public List<ReviewVO> list(int pid);
	public int getCount(int pid);
	public boolean isExist(String content, int pid);
	
}
