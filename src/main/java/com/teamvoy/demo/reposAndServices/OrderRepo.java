package com.teamvoy.demo.reposAndServices;

import com.teamvoy.demo.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Integer> {

    List<Order> findAllByItem(String item);
}
