package com.property.model;

import com.category.model.CategoryVO;

public class PropertyVO implements java.io.Serializable{
	 
    private String name;
    private CategoryVO categoryVO;
    private int propertyId;
 
    public int getPropertyId() {
        return propertyId;
    }
 
    public void setPropertyId(int id) {
        this.propertyId = id;
    }
 
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public CategoryVO getCategory() {
        return categoryVO;
    }
    public void setCategory(CategoryVO category) {
        this.categoryVO = category;
    }
     
}
