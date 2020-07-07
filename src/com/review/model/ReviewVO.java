package com.review.model;
import java.util.Date;

import com.member.model.MemberVO;
import com.product.model.ProductVO;
public class ReviewVO {

	 private String content;
	    private Date ReviewDate;
	    private MemberVO memberVO;
	    private ProductVO productVO;
	    private int ReviewId;
	 
	    public int getReviewId() {
	        return ReviewId;
	    }
	 
	    public void setReviewId(int id) {
	        this.ReviewId = id;
	    }
	    public String getContent() {
	        return content;
	    }
	    public void setContent(String content) {
	        this.content = content;
	    }
	    public Date getReviewDate() {
	        return ReviewDate;
	    }
	    public void setReviewDate(Date createDate) {
	        this.ReviewDate = createDate;
	    }
	     
	    public MemberVO getMember() {
	        return memberVO;
	    }
	    public void setMember(MemberVO user) {
	        this.memberVO = user;
	    }
	    public ProductVO getProduct() {
	        return productVO;
	    }
	    public void setProduct(ProductVO product) {
	        this.productVO = product;
	    }
	}