package com.propertyvalue.model;

import com.product.model.ProductVO;
import com.property.model.PropertyVO;

public class PropertyValueVO {

	 private String value;
	    private ProductVO productVO;
	    private PropertyVO propertyVO;
	    private int propertyValId;
	 
	    public int getPropertyValId() {
	        return propertyValId;
	    }
	 
	    public void setPropertyValId(int id) {
	        this.propertyValId = id;
	    }
	 
	    public String getValue() {
	        return value;
	    }
	    public void setValue(String value) {
	        this.value = value;
	    }
	    public ProductVO getProduct() {
	        return productVO;
	    }
	    public void setProduct(ProductVO product) {
	        this.productVO = product;
	    }
	    public PropertyVO getProperty() {
	        return propertyVO;
	    }
	    public void setProperty(PropertyVO property) {
	        this.propertyVO = property;
	    }
	}
