package com.thuc.giadung.repository;

import com.thuc.giadung.entity.Order;
import com.thuc.giadung.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserOrderByCreatedAtDesc(User user);

    Page<Order> findByStatus(String status, Pageable pageable);

    List<Order> findTop10ByOrderByCreatedAtDesc();

    @Query(value = "SELECT SUM(o.total_price) FROM orders o WHERE o.status = 'DELIVERING'", nativeQuery = true)
    BigDecimal sumTotalPrice();

    Optional<Order> findByVnpTxnRef(String vnpTxnRef);
}
