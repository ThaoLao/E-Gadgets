package com.thuc.giadung.service.impl;

import com.thuc.giadung.entity.Order;
import com.thuc.giadung.entity.OrderDetail;
import com.thuc.giadung.repository.OrderDetailRepository;
import com.thuc.giadung.service.OrderDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> getAllOrderDetailByOrder(Order order) {
        return orderDetailRepository.findByOrder(order);
    }
}
