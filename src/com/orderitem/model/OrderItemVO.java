package com.orderitem.model;

import com.member.model.MemberVO;
import com.order.model.OrderVO;
import com.product.model.ProductVO;

public class OrderItemVO implements java.io.Serializable {

	   private int count;
	    private ProductVO product;
	    private OrderVO order;
	    private MemberVO member;
	    private int orderItemId;
	    public int getCount() {
	        return count;
	    }
	    public void setCount(int number) {
	        this.count = number;
	    }
	    public ProductVO getProduct() {
	        return product;
	    }
	    public void setProduct(ProductVO product) {
	        this.product = product;
	    }
	    public OrderVO getOrder() {
	        return order;
	    }
	    public void setOrder(OrderVO order) {
	        this.order = order;
	    }
	    public MemberVO getMember() {
	        return member;
	    }
	    public void setMember(MemberVO user) {
	        this.member = user;
	    }
	    public int getOrderItemId() {
	        return orderItemId;
	    }
	    public void setOrderItemId(int id) {
	        this.orderItemId = id;
	    }
	 
	}