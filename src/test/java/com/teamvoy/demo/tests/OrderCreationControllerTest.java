package com.teamvoy.demo.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamvoy.demo.controllers.OrderCreationController;
import com.teamvoy.demo.models.Order;
import com.teamvoy.demo.reposAndServices.OrderService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderCreationController.class)
public class OrderCreationControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private OrderService orderService;

    @Test
    void givenOrder_whenSaveOrder_thenReturnOrder() throws Exception {
        Order order = new Order(12.3, 30, "cookies");
        Order invalidOrder = new Order(12.3, -1, "cookies");

        ObjectMapper mapper = new ObjectMapper();
        String requestJson = mapper.writeValueAsString(order);
        String requestInvalidJson = mapper.writeValueAsString(invalidOrder);

        Mockito.when(orderService.saveOrder(order)).thenReturn(order);

        MvcResult result = mvc.perform(
                        post("/add/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult invalidResult = mvc.perform(
                post("/add/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestInvalidJson))
                .andExpect(status().isOk())
                .andReturn();

        Order found = mapper.readValue(result.getResponse().getContentAsString(), Order.class);
        String empty = invalidResult.getResponse().getContentAsString();

        assertThat(found.getItem()).isEqualTo(order.getItem());
        assertThat(empty).isEmpty();
    }

}
