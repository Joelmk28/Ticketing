package com.levraijmk.orderservice.repository;

import com.levraijmk.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
