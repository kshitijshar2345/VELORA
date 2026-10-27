package com.velora.api.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "coupons")
public class Coupon {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String code;

  @Column(nullable = false)
  private int discountPercent;

  private long minOrderPaise = 0;
  private Instant expiresAt;
  private boolean active = true;

  public Long getId() {
    return id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String v) {
    code = v;
  }

  public int getDiscountPercent() {
    return discountPercent;
  }

  public void setDiscountPercent(int v) {
    discountPercent = v;
  }

  public long getMinOrderPaise() {
    return minOrderPaise;
  }

  public void setMinOrderPaise(long v) {
    minOrderPaise = v;
  }

  public Instant getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(Instant v) {
    expiresAt = v;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean v) {
    active = v;
  }
}
