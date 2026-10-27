package com.velora.api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "store_content")
public class StoreContent {
  @Id private String contentKey;

  @Column(length = 5000)
  private String contentValue;

  public String getContentKey() {
    return contentKey;
  }

  public void setContentKey(String v) {
    contentKey = v;
  }

  public String getContentValue() {
    return contentValue;
  }

  public void setContentValue(String v) {
    contentValue = v;
  }
}
