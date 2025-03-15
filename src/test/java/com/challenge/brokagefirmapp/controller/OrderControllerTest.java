package com.challenge.brokagefirmapp.controller;

import com.challenge.brokagefirmapp.dto.OrderDto;
import com.challenge.brokagefirmapp.request.CreateOrderRequest;
import com.challenge.brokagefirmapp.request.DeleteOrderRequest;
import com.challenge.brokagefirmapp.request.ListOrdersRequest;
import com.challenge.brokagefirmapp.response.CreateOrderResponse;
import com.challenge.brokagefirmapp.response.DeleteOrderResponse;
import com.challenge.brokagefirmapp.response.ListOrdersResponse;
import com.challenge.brokagefirmapp.service.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class OrderControllerTest {
    private OrderService orderService;

    private OrderController orderController;
    
    @BeforeEach
    public void setUp(){
        orderService = Mockito.mock(OrderService.class);
        orderController = new OrderController(orderService);
    }

    @Test
    public void test_createOrder() {
        OrderDto orderDto = OrderDto.builder().build();
        CreateOrderResponse createOrderResponse = CreateOrderResponse.builder().order(orderDto).build();

        Mockito.when(orderService.createOrder(Mockito.any(CreateOrderRequest.class))).thenReturn(createOrderResponse);

        CreateOrderResponse response = orderController.createOrder(CreateOrderRequest.builder().build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(createOrderResponse, response);
    }

    @Test
    public void test_listOrders() {
        OrderDto orderDto1 = OrderDto.builder().build();
        OrderDto orderDto2 = OrderDto.builder().build();
        List<OrderDto> orderDtos = Arrays.asList(orderDto1, orderDto2);
        ListOrdersResponse listOrdersResponse = ListOrdersResponse.builder().orders(orderDtos).build();

        Mockito.when(orderService.listOrders(Mockito.any(ListOrdersRequest.class))).thenReturn(listOrdersResponse);

        ListOrdersResponse response = orderController.listOrderResponse(1L, new Date(), new Date());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(listOrdersResponse, response);
    }

    @Test
    public void test_deleteOrder(){
        DeleteOrderResponse deleteOrderResponse = DeleteOrderResponse.builder().success(true).build();

        Mockito.when(orderService.deleteOrder(Mockito.any(DeleteOrderRequest.class))).thenReturn(deleteOrderResponse);

        DeleteOrderResponse response = orderController.deleteOrder(DeleteOrderRequest.builder().build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(deleteOrderResponse, response);
    }
}
