package com.shopsphere.model;
import java.io.Serializable;
public class Product implements Serializable {
 private int productId,categoryId,stock; private String name,brand,description,imageUrl; private double price,discount;
 public int getProductId(){return productId;} public void setProductId(int v){productId=v;}
 public int getCategoryId(){return categoryId;} public void setCategoryId(int v){categoryId=v;}
 public int getStock(){return stock;} public void setStock(int v){stock=v;}
 public String getName(){return name;} public void setName(String v){name=v;}
 public String getBrand(){return brand;} public void setBrand(String v){brand=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public String getImageUrl(){return imageUrl;} public void setImageUrl(String v){imageUrl=v;}
 public double getPrice(){return price;} public void setPrice(double v){price=v;}
 public double getDiscount(){return discount;} public void setDiscount(double v){discount=v;}
 public double getFinalPrice(){return price-(price*discount/100.0);}
}
