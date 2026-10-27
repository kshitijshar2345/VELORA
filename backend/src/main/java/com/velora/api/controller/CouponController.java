package com.velora.api.controller;

import com.velora.api.repository.CouponRepository;
import java.time.Instant;
import java.util.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {
  private final CouponRepository r;

  public CouponController(CouponRepository r) {
    this.r = r;
  }

  @GetMapping("/validate")
  public Map<String, Object> validate(@RequestParam String code, @RequestParam long subtotalPaise) {
    var c =
        r.findByCodeIgnoreCase(code.trim())
            .filter(
                x ->
                    x.isActive()
                        && (x.getExpiresAt() == null || x.getExpiresAt().isAfter(Instant.now()))
                        && subtotalPaise >= x.getMinOrderPaise())
            .orElseThrow(
                () ->
                    new org.springframework.web.server.ResponseStatusException(
                        org.springframework.http.HttpStatus.BAD_REQUEST,
                        "Invalid or expired coupon"));
    return Map.of(
        "code",
        c.getCode(),
        "discountPercent",
        c.getDiscountPercent(),
        "discountPaise",
        subtotalPaise * c.getDiscountPercent() / 100);
  }
}
