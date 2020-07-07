package com.orderitem.model;

import java.util.List;

import com.order.model.OrderVO;

public interface OrderItemDAO {

	public int getTotal();
	public void add(OrderItemVO bean);
	public void update(OrderItemVO bean);
	public void delete(int id);
	public OrderItemVO get(int id);
	public List<OrderItemVO> listByUser(int uid);
	public List<OrderItemVO> listByUser(int uid, int start, int count);
	public List<OrderItemVO> listByOrder(int oid);
	public List<OrderItemVO> listByOrder(int oid, int start, int count);
	public void fill(List<OrderVO> os);
	public void fill(OrderVO o);
	public List<OrderItemVO> listByProduct(int pid);
	public List<OrderItemVO> listByProduct(int pid, int start, int count);
	public int getSaleCount(int pid);
	
}
