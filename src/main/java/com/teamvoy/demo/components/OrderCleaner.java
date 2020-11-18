package com.teamvoy.demo.components;

import com.teamvoy.demo.models.Order;
import com.teamvoy.demo.reposAndServices.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/***
 * This class is created to delete a specified order object from db in a separate thread.
 * To use this class properly, you should inject it in another bean and set the orderToDelete obj
 * through a setter call and only then pass an instance of this class as a Runnable argument
 */
@Component
public class OrderCleaner implements Runnable {

    @Autowired
    private OrderRepo orderRepo;
    private Order orderToDelete;

    public OrderCleaner(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    //setter is used cuz I can't specify Order class as a param in a constructor
    public void setOrder(Order order) {
        this.orderToDelete = order;
    }

    @Override
    public void run() {
        orderRepo.delete(orderToDelete);
    }
}
