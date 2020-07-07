package com.category.model;

import java.util.List;

import com.product.model.ProductVO;

public class CategoryVO {
 
    private String name;
    private int categoryId;
    List<ProductVO> products;
    List<List<ProductVO>> productsByRow;
 
    public int getCategoryId() {
        return categoryId;
    }
 
    public void setCategoryId(int id) {
        this.categoryId = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    @Override
    public String toString() {
        return "Category [name=" + name + "]";
    }
 
    public List<ProductVO> getProducts() {
        return products;
    }
 
    public void setProducts(List<ProductVO> products) {
        this.products = products;
    }
 
    public List<List<ProductVO>> getProductsByRow() {
        return productsByRow;
    }
 
    public void setProductsByRow(List<List<ProductVO>> productsByRow) {
        this.productsByRow = productsByRow;
    }
 
}