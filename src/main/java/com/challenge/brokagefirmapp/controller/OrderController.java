package com.challenge.brokagefirmapp.controller;

import com.challenge.brokagefirmapp.request.CreateOrderRequest;
import com.challenge.brokagefirmapp.request.DeleteOrderRequest;
import com.challenge.brokagefirmapp.request.ListOrdersRequest;
import com.challenge.brokagefirmapp.response.CreateOrderResponse;
import com.challenge.brokagefirmapp.response.DeleteOrderResponse;
import com.challenge.brokagefirmapp.response.ListOrdersResponse;
import com.challenge.brokagefirmapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "/create", consumes = "application/json")
    public CreateOrderResponse createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        return orderService.createOrder(createOrderRequest);
    }

    @GetMapping("/list")
    public ListOrdersResponse listOrderResponse(@RequestParam Long customerId, @RequestParam Date fromDate, @RequestParam Date toDate) {
        ListOrdersRequest listOrdersRequest = ListOrdersRequest.builder()
                .customerId(customerId)
                .fromDate(fromDate)
                .toDate(toDate).build();
        return orderService.listOrders(listOrdersRequest);
    }

    @PostMapping(value = "/delete", consumes = "application/json")
    public DeleteOrderResponse deleteOrder(@RequestBody DeleteOrderRequest deleteOrderRequest) {
        return orderService.deleteOrder(deleteOrderRequest);
    }
}
