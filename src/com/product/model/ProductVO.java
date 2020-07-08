package com.product.model;

import com.category.model.CategoryVO;
import com.productimage.model.ProductImageVO;

import java.util.Date;

import java.util.List;
 
public class ProductVO implements java.io.Serializable{
    
	private static final long serialVersionUID = 1L;
	
	private String name;
    private String subTitle;
    private Double orignalPrice;
    private Double promotePrice;
    private int stock;
    private Date createDate;
    private CategoryVO categoryVO;
    private int productId;
    private ProductImageVO firstProductImage;
    private List<ProductImageVO> productImages;
    private List<ProductImageVO> productSingleImages;
    private List<ProductImageVO> productDetailImages;
    private int reviewCount;
    private int saleCount;
 
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSubTitle() {
        return subTitle;
    }
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }
    public Double getOrignalPrice() {
        return orignalPrice;
    }
    public void setOrignalPrice(Double orignalPrice) {
        this.orignalPrice = orignalPrice;
    }
    public Double getPromotePrice() {
        return promotePrice;
    }
    public void setPromotePrice(Double promotePrice) {
        this.promotePrice = promotePrice;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public CategoryVO getCategory() {
        return categoryVO;
    }
    public void setCategory(CategoryVO categoryVO) {
        this.categoryVO = categoryVO;
    }
    public int getProductId() {
        return productId;
    }
    public void setProductId(int id) {
        this.productId = id;
    }
     
    public String toString(){
        return name;
    }
    public ProductImageVO getFirstProductImage() {
        return firstProductImage;
    }
    public void setFirstProductImage(ProductImageVO firstProductImage) {
        this.firstProductImage = firstProductImage;
    }
    public List<ProductImageVO> getProductImages() {
        return productImages;
    }
    public void setProductImages(List<ProductImageVO> productImages) {
        this.productImages = productImages;
    }
    public List<ProductImageVO> getProductSingleImages() {
        return productSingleImages;
    }
    public void setProductSingleImages(List<ProductImageVO> productSingleImages) {
        this.productSingleImages = productSingleImages;
    }
    public List<ProductImageVO> getProductDetailImages() {
        return productDetailImages;
    }
    public void setProductDetailImages(List<ProductImageVO> productDetailImages) {
        this.productDetailImages = productDetailImages;
    }
    public int getReviewCount() {
        return reviewCount;
    }
    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }
    public int getSaleCount() {
        return saleCount;
    }
    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }
     
}