package com.thuc.giadung.repository;

import com.thuc.giadung.entity.Order;
import com.thuc.giadung.entity.OrderDetail;
import com.thuc.giadung.entity.composite_key.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
    List<OrderDetail> findByOrder(Order order);

    @Query(value = "SELECT od.quantity FROM order_details od WHERE od.order_id = :orderId AND od.product_id = :productId", nativeQuery = true)
    int findByProductAndOrOrder(Long orderId, Long productId);

    List<OrderDetail> findByProductId(long productId);
}
