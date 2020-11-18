package com.teamvoy.demo.tests;

import com.teamvoy.demo.models.Order;
import com.teamvoy.demo.reposAndServices.OrderRepo;
import com.teamvoy.demo.reposAndServices.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @MockBean
    private OrderRepo orderRepo;

    @BeforeEach
    void setOrderRepo() {
        List<Order> apples = new ArrayList<>();
        {
            apples.add(new Order(3.0, 20, "apple"));
            apples.add(new Order(1.0, 10, "apple"));
        }
        List<Order> pears = new ArrayList<>();
        {
            pears.add(new Order(11.0, 5, "pear"));
            pears.add(new Order(15.5, 15, "pear"));
        }
        Mockito.when(orderRepo.findAllByItem("apple")).thenReturn(apples);
        Mockito.when(orderRepo.findAllByItem("pear")).thenReturn(pears);

    }

    @Test
    void whenFindAllAvailable_thenReturnWithLowestPriceAndCorrectQuantity() {
        int numberOfItems = 5;

        Order found = orderService.getOrderWithLowestPriceByName("apple", numberOfItems);

        assertThat(found).isNotEqualTo(null);
        assertThat(found.getItem()).isEqualTo("apple");
        assertThat(found.getPrice()).isEqualTo(1.0);
        assertThat(found.getQuantity()).isEqualTo(numberOfItems);
    }

    @Test
    void whenFindAllInDemand_thenReturnWithLowestPriceAndCorrectQuantity() {
        int numberOfItems = 8;
        Order pear = new Order(11.0, 5, "pear");

        Order found = orderService.getOrderWithLowestPriceByName("pear", numberOfItems);

        assertThat(found).isEqualTo(pear);
    }
}
