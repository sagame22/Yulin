package com.orderitem.model;

import com.member.model.MemberVO;
import com.order.model.OrderVO;
import com.product.model.ProductVO;

public class OrderItemVO {

	   private int number;
	    private ProductVO product;
	    private OrderVO order;
	    private MemberVO user;
	    private int id;
	    public int getNumber() {
	        return number;
	    }
	    public void setNumber(int number) {
	        this.number = number;
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
	    public MemberVO getUser() {
	        return user;
	    }
	    public void setUser(MemberVO user) {
	        this.user = user;
	    }
	    public int getId() {
	        return id;
	    }
	    public void setId(int id) {
	        this.id = id;
	    }
	 
	}