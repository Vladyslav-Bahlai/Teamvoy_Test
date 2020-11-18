package com.teamvoy.demo.reposAndServices;

import com.teamvoy.demo.components.OrderCleaner;
import com.teamvoy.demo.models.Order;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepo orderRepo;
    private OrderCleaner orderCleaner;
    // in seconds
    private int orderValidationPeriod;

    public OrderServiceImpl(OrderRepo orderRepo, OrderCleaner orderCleaner) {
        this.orderRepo = orderRepo;
        this.orderCleaner = orderCleaner;
        this.orderValidationPeriod = 600;
    }

    public void setOrderValidationPeriod(int orderValidationPeriod) {
        if (orderValidationPeriod > 0) {
            this.orderValidationPeriod = orderValidationPeriod;
        }
    }

    public int getOrderValidationPeriod() {
        return orderValidationPeriod;
    }

    @Override
    public Order saveOrder(Order order) {
        // ScheduledExecutorService is used to delete inactive orders after orderValidationPeriod
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        orderCleaner.setOrder(order);
        executorService.schedule(orderCleaner, orderValidationPeriod, TimeUnit.SECONDS);

        return orderRepo.save(order);
    }

    @Override
    public Order getOrderWithLowestPriceByName(String item, int numberOfItems) {
        List<Order> orders = orderRepo.findAllByItem(item);

        if (orders.size() == 0 || numberOfItems < 0) return null;
        // get item with the lowest price from the list
        // filter((o)->o.getQuantity()>0) could be additionally added to filter out sold out items
        Order orderWithLowestPrice = orders.stream().min(Comparator.comparing(Order::getPrice)).get();
        // get numberOfItems if there are enough in the storage, else get all available
        int numberOfBoughtItems = orderWithLowestPrice.getQuantity() < numberOfItems ?
                        orderWithLowestPrice.getQuantity() :
                        numberOfItems;
        orderWithLowestPrice.setQuantity(orderWithLowestPrice.getQuantity()-numberOfBoughtItems);
        orderRepo.save(orderWithLowestPrice);
        // I'm not sure what exactly is expected to be returned so the line below
        // may be commented out in case we want to get a number of remaining items
        orderWithLowestPrice.setQuantity(numberOfBoughtItems);
        return orderWithLowestPrice;
    }
}
