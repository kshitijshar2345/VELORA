package com.velora.api.entity;
import jakarta.persistence.*; import java.time.Instant;
@Entity @Table(name="products")
public class Product {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String name;
 @Column(nullable=false,unique=true) private String slug;
 @Column(length=3000) private String description;
 @Column(nullable=false) private long pricePaise;
 private Long compareAtPricePaise;
 @Column(nullable=false) private String imageUrl;
 private String category; private String sizes; private String colors;
 @Column(nullable=false) private int stock=0;
 @Column(nullable=false) private boolean featured=false;
 @Column(nullable=false) private boolean active=true;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 public Long getId(){return id;} public String getName(){return name;} public void setName(String v){name=v;} public String getSlug(){return slug;} public void setSlug(String v){slug=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;} public long getPricePaise(){return pricePaise;} public void setPricePaise(long v){pricePaise=v;} public Long getCompareAtPricePaise(){return compareAtPricePaise;} public void setCompareAtPricePaise(Long v){compareAtPricePaise=v;} public String getImageUrl(){return imageUrl;} public void setImageUrl(String v){imageUrl=v;} public String getCategory(){return category;} public void setCategory(String v){category=v;} public String getSizes(){return sizes;} public void setSizes(String v){sizes=v;} public String getColors(){return colors;} public void setColors(String v){colors=v;} public int getStock(){return stock;} public void setStock(int v){stock=v;} public boolean isFeatured(){return featured;} public void setFeatured(boolean v){featured=v;} public boolean isActive(){return active;} public void setActive(boolean v){active=v;} public Instant getCreatedAt(){return createdAt;}
}
