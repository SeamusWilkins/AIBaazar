package com.seamus.aibazaar.service;

import com.seamus.aibazaar.entity.User;
import com.seamus.aibazaar.entity.ProductOrder;
import com.seamus.aibazaar.entity.User;
import com.seamus.aibazaar.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<ProductOrder> findAllByUser(User user) {
        return orderRepository.findAllByUser(user);
    }

    public List<ProductOrder> findAllOrders() {
        return orderRepository.findAll();
    }

    public void saveAll(List<ProductOrder> orders) {
        orderRepository.saveAll(orders);
    }
}
