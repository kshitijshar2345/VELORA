package com.velora.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long productId;
  private String productName;
  private String imageUrl;
  private String size;
  private int quantity;
  private long unitPricePaise;

  public Long getId() {
    return id;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long v) {
    productId = v;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String v) {
    productName = v;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String v) {
    imageUrl = v;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String v) {
    size = v;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int v) {
    quantity = v;
  }

  public long getUnitPricePaise() {
    return unitPricePaise;
  }

  public void setUnitPricePaise(long v) {
    unitPricePaise = v;
  }
}
