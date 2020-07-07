package com.productimage.model;

import com.product.model.ProductVO;

public class ProductImageVO {

    private String type;
    private ProductVO productVO;
    private int imageId;
 
    public int getImageId() {
        return imageId;
    }
     
    public void setImageId(int id) {
        this.imageId = id;
    }
 
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public ProductVO getproductVO() {
        return productVO;
    }
    public void setproductVO(ProductVO productVO) {
        this.productVO = productVO;
    }
}