package com.velora.api.entity;
import jakarta.persistence.*; import java.time.Instant; import java.util.ArrayList; import java.util.List;
@Entity @Table(name="customer_orders")
public class CustomerOrder {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private Long userId;
 @Column(nullable=false) private long totalPaise;
 @Column(nullable=false) private String status="PENDING_PAYMENT";
 private String paymentMethod="RAZORPAY"; private String paymentId; private String razorpayOrderId;
 @Column(length=2000) private String shippingAddress;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 @OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL, orphanRemoval=true) @JoinColumn(name="order_id") private List<OrderItem> items=new ArrayList<>();
 public Long getId(){return id;} public Long getUserId(){return userId;} public void setUserId(Long v){userId=v;} public long getTotalPaise(){return totalPaise;} public void setTotalPaise(long v){totalPaise=v;} public String getStatus(){return status;} public void setStatus(String v){status=v;} public String getPaymentMethod(){return paymentMethod;} public void setPaymentMethod(String v){paymentMethod=v;} public String getPaymentId(){return paymentId;} public void setPaymentId(String v){paymentId=v;} public String getRazorpayOrderId(){return razorpayOrderId;} public void setRazorpayOrderId(String v){razorpayOrderId=v;} public String getShippingAddress(){return shippingAddress;} public void setShippingAddress(String v){shippingAddress=v;} public Instant getCreatedAt(){return createdAt;} public List<OrderItem> getItems(){return items;}
}
