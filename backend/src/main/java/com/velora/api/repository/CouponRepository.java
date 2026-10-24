package com.velora.api.repository;
import com.velora.api.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface CouponRepository extends JpaRepository<Coupon, Long> { Optional<Coupon> findByCodeIgnoreCase(String code); }
