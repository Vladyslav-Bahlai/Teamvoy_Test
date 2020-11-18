package com.teamvoy.demo.controllers;

import com.teamvoy.demo.models.Order;
import com.teamvoy.demo.reposAndServices.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/get")
public class OrderSelectionController {

    private OrderService orderService;

    public OrderSelectionController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/order")
    public Order getOrderByItem(
            @RequestParam String item,
            @RequestParam(name = "quantity") int numberOfItems
    ) {
        return orderService.getOrderWithLowestPriceByName(item, numberOfItems);
    }

}
