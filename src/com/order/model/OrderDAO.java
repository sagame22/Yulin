package com.order.model;

import java.util.List;

public interface OrderDAO {

	public int getTotal();
	public int add(OrderVO bean);
	public void update(OrderVO bean);
	public void delete(int id);
	public OrderVO get(int id);
	public List<OrderVO> list();
	public List<OrderVO> list(int uid,String excludedStatus);
	
}
