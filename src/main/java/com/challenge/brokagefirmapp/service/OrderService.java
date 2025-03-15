package com.challenge.brokagefirmapp.service;

import com.challenge.brokagefirmapp.domain.Order;
import com.challenge.brokagefirmapp.dto.OrderDto;
import com.challenge.brokagefirmapp.mapper.OrderMapper;
import com.challenge.brokagefirmapp.property.Status;
import com.challenge.brokagefirmapp.repository.OrderRepository;
import com.challenge.brokagefirmapp.request.CreateOrderRequest;
import com.challenge.brokagefirmapp.request.DeleteOrderRequest;
import com.challenge.brokagefirmapp.request.ListOrdersRequest;
import com.challenge.brokagefirmapp.response.CreateOrderResponse;
import com.challenge.brokagefirmapp.response.DeleteOrderResponse;
import com.challenge.brokagefirmapp.response.ListOrdersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;


    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest) {
        Order order = Order.builder()
                .customerId(createOrderRequest.getCustomerId())
                .assetName(createOrderRequest.getAssetName())
                .orderSide(createOrderRequest.getSide())
                .price(createOrderRequest.getPrice())
                .size(createOrderRequest.getSize())
                .status(Status.PENDING)
                .createDate(new Date())
                .build();
        order = orderRepository.save(order);
        OrderDto orderDto = OrderMapper.orderMapper.orderToOrderDto(order);
        return CreateOrderResponse.builder().order(orderDto).build();
    }

    public ListOrdersResponse listOrders(ListOrdersRequest listOrdersRequest) {
        List<Order> foundOrders = orderRepository.findByCustomerIdAndCreateDateBetween(listOrdersRequest.getCustomerId(),
                listOrdersRequest.getFromDate(),
                listOrdersRequest.getToDate());

        List<OrderDto> orderDtoList = foundOrders.stream().map(OrderMapper.orderMapper::orderToOrderDto).toList();

        return ListOrdersResponse.builder().orders(orderDtoList).build();
    }

    public DeleteOrderResponse deleteOrder(DeleteOrderRequest deleteOrderRequest) {
        DeleteOrderResponse deleteOrderResponse = DeleteOrderResponse.builder().build();
        Optional<Order> order = orderRepository.findById(deleteOrderRequest.getOrderId());

        if (order.isPresent()) {
            Order orderToDelete = order.get();
            if(orderToDelete.getStatus().equals(Status.PENDING)) {
                orderRepository.deleteById(deleteOrderRequest.getOrderId());
                deleteOrderResponse.setSuccess(Boolean.TRUE);
                deleteOrderResponse.setMessage("Order deleted Successfully");
            } else {
                deleteOrderResponse.setSuccess(Boolean.FALSE);
                deleteOrderResponse.setMessage("Only Pending Orders can be deleted. Requested Order's status is: " + orderToDelete.getStatus());
            }
        } else {
            deleteOrderResponse.setSuccess(Boolean.FALSE);
            deleteOrderResponse.setMessage("Order does not exist");
        }
        return deleteOrderResponse;
    }
}
