package com.teamvoy.demo.reposAndServices;

import com.teamvoy.demo.models.Order;

public interface OrderService {
    Order saveOrder(Order order);
    Order getOrderWithLowestPriceByName(String item, int numberOfItems);
}
