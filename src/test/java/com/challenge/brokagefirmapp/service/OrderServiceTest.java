package com.challenge.brokagefirmapp.service;

import com.challenge.brokagefirmapp.domain.Order;
import com.challenge.brokagefirmapp.dto.OrderDto;
import com.challenge.brokagefirmapp.mapper.OrderMapper;
import com.challenge.brokagefirmapp.repository.OrderRepository;
import com.challenge.brokagefirmapp.request.CreateOrderRequest;
import com.challenge.brokagefirmapp.request.DeleteOrderRequest;
import com.challenge.brokagefirmapp.request.ListOrdersRequest;
import com.challenge.brokagefirmapp.response.CreateOrderResponse;
import com.challenge.brokagefirmapp.response.DeleteOrderResponse;
import com.challenge.brokagefirmapp.response.ListOrdersResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class OrderServiceTest {
    private OrderRepository orderRepository;

    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        orderRepository = Mockito.mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);
    }

    @Test
    public void test_createOrder() {
        Order order = Order.builder().id(1L).build();
        CreateOrderResponse createOrderResponse = CreateOrderResponse.builder().order(OrderMapper.orderMapper.orderToOrderDto(order)).build();

        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);

        CreateOrderResponse response = orderService.createOrder(CreateOrderRequest.builder().build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(createOrderResponse, response);
    }

    @Test
    public void test_listOrders() {
        Order order = Order.builder().id(1L).build();
        Order order2 = Order.builder().id(2L).build();
        List<Order> orders = Arrays.asList(order, order2);
        List<OrderDto> orderDtos = orders.stream().map(OrderMapper.orderMapper::orderToOrderDto).toList();
        ListOrdersResponse listOrdersResponse = ListOrdersResponse.builder().orders(orderDtos).build();

        Mockito.when(orderRepository.findByCustomerIdAndCreateDateBetween(Mockito.anyLong(), Mockito.any(Date.class), Mockito.any(Date.class))).thenReturn(orders);

        ListOrdersResponse response = orderService.listOrders(ListOrdersRequest.builder().customerId(1L).fromDate(new Date()).toDate(new Date()).build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(listOrdersResponse, response);
    }

    @Test
    public void test_deleteOrder_successful() {
        DeleteOrderResponse deleteOrderResponse = DeleteOrderResponse.builder().success(Boolean.TRUE).message("Order deleted Successfully").build();

        Mockito.when(orderRepository.existsById(Mockito.anyLong())).thenReturn(true);

        DeleteOrderResponse response = orderService.deleteOrder(DeleteOrderRequest.builder().orderId(1L).build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(deleteOrderResponse, response);
    }

    @Test
    public void test_deleteOrder_notExists() {
        DeleteOrderResponse deleteOrderResponse = DeleteOrderResponse.builder().success(Boolean.FALSE).message("Order does not exist").build();

        Mockito.when(orderRepository.existsById(Mockito.anyLong())).thenReturn(false);

        DeleteOrderResponse response = orderService.deleteOrder(DeleteOrderRequest.builder().orderId(1L).build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(deleteOrderResponse, response);
    }
}
