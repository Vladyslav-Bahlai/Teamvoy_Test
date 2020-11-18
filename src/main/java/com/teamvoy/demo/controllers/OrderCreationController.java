package com.teamvoy.demo.controllers;

import com.teamvoy.demo.models.Order;
import com.teamvoy.demo.reposAndServices.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/add")
public class OrderCreationController {

    private OrderService orderService;

    public OrderCreationController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order")
    public Order createOrderFromBody(@RequestBody Order order) {
        Order orderToSave = validateOrder(order);
        if (orderToSave == null) {
            return null;
        } else {
            return orderService.saveOrder(orderToSave);
        }
    }

    private Order validateOrder(Order order) {
        if (order == null || order.getPrice() < 0 ||
                order.getQuantity() <= 0 || order.getItem() == null) {
            return null;
        } else {
            return order;
        }
    }

}
