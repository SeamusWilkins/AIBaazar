package com.seamus.aibazaar.repository;

import com.seamus.aibazaar.entity.ProductOrder;
import com.seamus.aibazaar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<ProductOrder, Long> {

    List<ProductOrder> findAllByUser(User user);

}
