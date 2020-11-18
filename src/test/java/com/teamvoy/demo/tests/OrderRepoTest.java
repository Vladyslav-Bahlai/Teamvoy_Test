package com.teamvoy.demo.tests;

import com.teamvoy.demo.models.Order;
import com.teamvoy.demo.reposAndServices.OrderRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class OrderRepoTest {
    @Autowired
    private OrderRepo orderRepo;

    @Test
    void whenFindAllByItem_thenContainsOrders() {
        Order melon1 = new Order(6.75, 15, "watermelon");
        Order melon2 = new Order(10.15, 13, "watermelon");
        orderRepo.save(melon1);
        orderRepo.save(melon2);
        orderRepo.flush();

        List<Order> found1 = orderRepo.findAllByItem(melon1.getItem());
        List<Order> found2 = orderRepo.findAllByItem("coconut");

        assertThat(found1).contains(melon1, melon2);
        assertThat(found2).isEmpty();
    }
}
