package com.velora.api.controller;
import com.velora.api.entity.*;import com.velora.api.repository.*;import org.springframework.web.bind.annotation.*;import org.springframework.http.*;import java.util.*;
@RestController @RequestMapping("/api/admin") public class AdminController {
 private final ProductRepository products;private final CategoryRepository categories;private final CouponRepository coupons;private final OrderRepository orders;private final StoreContentRepository content;
 public AdminController(ProductRepository p,CategoryRepository c,CouponRepository cp,OrderRepository o,StoreContentRepository s){products=p;categories=c;coupons=cp;orders=o;content=s;}
 @GetMapping("/products") public List<Product> products(){return products.findAll();}
 @PostMapping("/products") public ResponseEntity<?> create(@RequestBody Product p){if(p.getName()==null||p.getName().isBlank()||p.getPricePaise()<0)return ResponseEntity.badRequest().body(Map.of("message","Name and valid price are required"));if(p.getSlug()==null||p.getSlug().isBlank())p.setSlug(slug(p.getName()));if(p.getImageUrl()==null||p.getImageUrl().isBlank())p.setImageUrl("https://images.unsplash.com/photo-1529139574466-a303027c1d8b?auto=format&fit=crop&w=900&q=85");return ResponseEntity.status(201).body(products.save(p));}
 @PutMapping("/products/{id}") public Product update(@PathVariable Long id,@RequestBody Product p){Product x=products.findById(id).orElseThrow();x.setName(p.getName());x.setSlug(p.getSlug()==null?slug(p.getName()):p.getSlug());x.setDescription(p.getDescription());x.setPricePaise(p.getPricePaise());x.setCompareAtPricePaise(p.getCompareAtPricePaise());x.setImageUrl(p.getImageUrl());x.setCategory(p.getCategory());x.setSizes(p.getSizes());x.setColors(p.getColors());x.setStock(p.getStock());x.setFeatured(p.isFeatured());x.setActive(p.isActive());return products.save(x);}
 @DeleteMapping("/products/{id}") public ResponseEntity<?> delete(@PathVariable Long id){products.deleteById(id);return ResponseEntity.noContent().build();}
 @GetMapping("/categories") public List<Category> categories(){return categories.findAll();}
 @PostMapping("/categories") public Category createCategory(@RequestBody Category c){if(c.getSlug()==null||c.getSlug().isBlank())c.setSlug(slug(c.getName()));return categories.save(c);}
 @DeleteMapping("/categories/{id}") public ResponseEntity<?> deleteCategory(@PathVariable Long id){categories.deleteById(id);return ResponseEntity.noContent().build();}
 @GetMapping("/coupons") public List<Coupon> coupons(){return coupons.findAll();}
 @PostMapping("/coupons") public Coupon coupon(@RequestBody Coupon c){c.setCode(c.getCode().trim().toUpperCase());if(c.getDiscountPercent()<1||c.getDiscountPercent()>80)throw new IllegalArgumentException("Discount must be between 1 and 80 percent");return coupons.save(c);}
 @DeleteMapping("/coupons/{id}") public ResponseEntity<?> deleteCoupon(@PathVariable Long id){coupons.deleteById(id);return ResponseEntity.noContent().build();}
 @GetMapping("/orders") public List<CustomerOrder> orders(){return orders.findAll(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC,"createdAt"));}
 @PatchMapping("/orders/{id}/status") public CustomerOrder status(@PathVariable Long id,@RequestBody Map<String,String> body){CustomerOrder o=orders.findById(id).orElseThrow();String s=body.getOrDefault("status","PROCESSING");if(!List.of("PROCESSING","SHIPPED","DELIVERED","CANCELLED").contains(s))throw new IllegalArgumentException("Invalid status");o.setStatus(s);return orders.save(o);}
 @GetMapping("/content") public Map<String,String> getContent(){Map<String,String> m=new HashMap<>();content.findAll().forEach(c->m.put(c.getContentKey(),c.getContentValue()));return m;}
 @PutMapping("/content") public Map<String,String> setContent(@RequestBody Map<String,String> values){values.forEach((k,v)->{if(k.length()<80){StoreContent c=content.findById(k).orElseGet(StoreContent::new);c.setContentKey(k);c.setContentValue(v);content.save(c);}});return getContent();}
 private String slug(String s){return s.toLowerCase().replaceAll("[^a-z0-9]+","-").replaceAll("^-|-$","");}
}
