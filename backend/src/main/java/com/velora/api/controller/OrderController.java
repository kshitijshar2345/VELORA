package com.velora.api.controller;

import com.velora.api.entity.*;
import com.velora.api.repository.*;
import java.util.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
  private final UserRepository users;
  private final ProductRepository products;
  private final OrderRepository orders;

  public OrderController(UserRepository u, ProductRepository p, OrderRepository o) {
    users = u;
    products = p;
    orders = o;
  }

  public record ItemRequest(Long productId, String size, int quantity) {}

  public record CreateOrderRequest(
      List<ItemRequest> items, String shippingAddress, String paymentMethod) {}

  @PostMapping
  public CustomerOrder create(@RequestBody CreateOrderRequest req, Authentication a) {
    if (req.items() == null || req.items().isEmpty())
      throw new org.springframework.web.server.ResponseStatusException(
          org.springframework.http.HttpStatus.BAD_REQUEST, "Cart is empty");
    User u = users.findByEmail(a.getName()).orElseThrow();
    CustomerOrder o = new CustomerOrder();
    o.setUserId(u.getId());
    o.setShippingAddress(req.shippingAddress());
    long total = 0;
    for (ItemRequest i : req.items()) {
      if (i.quantity() < 1 || i.quantity() > 20)
        throw new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.BAD_REQUEST, "Invalid quantity");
      Product p = products.findById(i.productId()).filter(Product::isActive).orElseThrow();
      if (p.getStock() < i.quantity())
        throw new org.springframework.web.server.ResponseStatusException(
            org.springframework.http.HttpStatus.CONFLICT, "Insufficient stock for " + p.getName());
      OrderItem oi = new OrderItem();
      oi.setProductId(p.getId());
      oi.setProductName(p.getName());
      oi.setImageUrl(p.getImageUrl());
      oi.setSize(i.size());
      oi.setQuantity(i.quantity());
      oi.setUnitPricePaise(p.getPricePaise());
      o.getItems().add(oi);
      total += p.getPricePaise() * i.quantity();
    }
    o.setTotalPaise(total);
    o.setPaymentMethod("RAZORPAY".equalsIgnoreCase(req.paymentMethod()) ? "RAZORPAY" : "COD");
    if ("COD".equals(o.getPaymentMethod())) o.setStatus("PROCESSING");
    return orders.save(o);
  }

  @GetMapping("/mine")
  public List<CustomerOrder> mine(Authentication a) {
    User u = users.findByEmail(a.getName()).orElseThrow();
    return orders.findByUserIdOrderByCreatedAtDesc(u.getId());
  }
}
