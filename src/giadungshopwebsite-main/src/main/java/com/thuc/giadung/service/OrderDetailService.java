package com.thuc.giadung.service;

import com.thuc.giadung.entity.Order;
import com.thuc.giadung.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> getAllOrderDetailByOrder(Order order);
}
