package com.teamvoy.demo.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamvoy.demo.controllers.OrderSelectionController;
import com.teamvoy.demo.models.Order;
import com.teamvoy.demo.reposAndServices.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderSelectionController.class)
public class OrderSelectionControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private OrderService orderService;

    @Test
    void givenParams_whenSelectOrders_thenReturnOrderMatchingParams() throws Exception {
        when(orderService.getOrderWithLowestPriceByName("cake", 10))
                .thenReturn(new Order(10.0, 10, "cake"));

        MvcResult result = mvc.perform(
                get("/get/order")
                .queryParam("item", "cake")
                .queryParam("quantity", "10"))
            .andExpect(status().isOk())
            .andReturn();

        Order order = new ObjectMapper().readValue(result.getResponse().getContentAsString(),
                Order.class);
        assertThat(order.getItem()).isEqualTo("cake");
    }
}
