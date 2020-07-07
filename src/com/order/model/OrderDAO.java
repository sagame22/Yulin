package com.order.model;

import java.util.List;

public interface OrderDAO {

	public int getTotal();
	public void add(OrderVO bean);
	public void update(OrderVO bean);
	public void delete(int id);
	public OrderVO get(int id);
	public List<OrderVO> list();
	public List<OrderVO> list(int start, int count);
	public List<OrderVO> list(int uid,String excludedStatus);
	public List<OrderVO> list(int uid, String excludedStatus, int start, int count);
	
}
