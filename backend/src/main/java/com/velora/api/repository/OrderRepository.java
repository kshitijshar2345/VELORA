package com.velora.api.repository;

import com.velora.api.entity.CustomerOrder;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CustomerOrder, Long> {
  List<CustomerOrder> findByUserIdOrderByCreatedAtDesc(Long userId);
}
