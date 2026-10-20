package com.shopsphere.model;
public class CartItem { private Product product; private int quantity;
 public CartItem(Product p,int q){product=p;quantity=q;} public Product getProduct(){return product;}
 public int getQuantity(){return quantity;} public void setQuantity(int q){quantity=q;}
 public double getSubtotal(){return product.getFinalPrice()*quantity;}
}
