package com.challenge.brokagefirmapp.service;

import com.challenge.brokagefirmapp.domain.Order;
import com.challenge.brokagefirmapp.dto.AssetDto;
import com.challenge.brokagefirmapp.dto.OrderDto;
import com.challenge.brokagefirmapp.mapper.OrderMapper;
import com.challenge.brokagefirmapp.property.Side;
import com.challenge.brokagefirmapp.property.Status;
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
import java.util.Optional;

public class OrderServiceTest {
    private static final String TEST_ASSET_NAME = "test";

    private AssetService assetService;

    private OrderRepository orderRepository;

    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        assetService = Mockito.mock(AssetService.class);
        orderRepository = Mockito.mock(OrderRepository.class);
        orderService = new OrderService(assetService, orderRepository);
    }

    @Test
    public void test_createOrder_buy_success() {
        Order order = Order.builder().id(1L).orderSide(Side.BUY).build();
        AssetDto asset = AssetDto.builder().id(1L).customerId(1L).assetName("TRY").assetSize(100).usableSize(100).build();
        CreateOrderRequest createOrderRequest = CreateOrderRequest.builder().customerId(1L).assetName(TEST_ASSET_NAME).side(Side.BUY).price(10).size(10).build();
        CreateOrderResponse createOrderResponse = CreateOrderResponse.builder().success(Boolean.TRUE).message("Order created successfully").order(OrderMapper.orderMapper.orderToOrderDto(order)).build();

        Mockito.when(assetService.getAssetOfCustomer(Mockito.anyLong(), Mockito.anyString())).thenReturn(asset);
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);

        CreateOrderResponse response = orderService.createOrder(createOrderRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(createOrderResponse, response);
    }

    @Test
    public void test_createOrder_buy_failure() {
        Order order = Order.builder().id(1L).orderSide(Side.BUY).build();
        AssetDto asset = AssetDto.builder().id(1L).customerId(1L).assetName("TRY").assetSize(100).usableSize(100).build();
        CreateOrderRequest createOrderRequest = CreateOrderRequest.builder().customerId(1L).assetName(TEST_ASSET_NAME).side(Side.BUY).price(10).size(11).build();
        CreateOrderResponse createOrderResponse = CreateOrderResponse.builder().success(Boolean.FALSE).message("Customer does not have enough TRY asset size").build();

        Mockito.when(assetService.getAssetOfCustomer(Mockito.anyLong(), Mockito.anyString())).thenReturn(asset);
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);

        CreateOrderResponse response = orderService.createOrder(createOrderRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(createOrderResponse, response);
    }

    @Test
    public void test_createOrder_sell_success() {
        Order order = Order.builder().id(1L).orderSide(Side.SELL).build();
        AssetDto asset = AssetDto.builder().id(1L).customerId(1L).assetName(TEST_ASSET_NAME).assetSize(10).usableSize(10).build();
        CreateOrderRequest createOrderRequest = CreateOrderRequest.builder().customerId(1L).assetName(TEST_ASSET_NAME).side(Side.SELL).price(10).size(10).build();
        CreateOrderResponse createOrderResponse = CreateOrderResponse.builder().success(Boolean.TRUE).message("Order created successfully").order(OrderMapper.orderMapper.orderToOrderDto(order)).build();

        Mockito.when(assetService.getAssetOfCustomer(Mockito.anyLong(), Mockito.anyString())).thenReturn(asset);
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);

        CreateOrderResponse response = orderService.createOrder(createOrderRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(createOrderResponse, response);
    }

    @Test
    public void test_createOrder_sell_failure() {
        Order order = Order.builder().id(1L).orderSide(Side.SELL).build();
        AssetDto asset = AssetDto.builder().id(1L).customerId(1L).assetName(TEST_ASSET_NAME).assetSize(10).usableSize(9).build();
        CreateOrderRequest createOrderRequest = CreateOrderRequest.builder().customerId(1L).assetName(TEST_ASSET_NAME).side(Side.SELL).price(10).size(10).build();
        CreateOrderResponse createOrderResponse = CreateOrderResponse.builder().success(Boolean.FALSE).message("Customer does not have enough test asset size").build();

        Mockito.when(assetService.getAssetOfCustomer(Mockito.anyLong(), Mockito.anyString())).thenReturn(asset);
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(order);

        CreateOrderResponse response = orderService.createOrder(createOrderRequest);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(createOrderResponse, response);
    }

    @Test
    public void test_createOrder_exception() {
        AssetDto asset = AssetDto.builder().id(1L).customerId(1L).assetName(TEST_ASSET_NAME).assetSize(10).usableSize(10).build();
        CreateOrderRequest createOrderRequest = CreateOrderRequest.builder().customerId(1L).assetName(TEST_ASSET_NAME).side(Side.SELL).price(10).size(10).build();
        CreateOrderResponse createOrderResponse = CreateOrderResponse.builder().success(Boolean.FALSE).message("Exception occurred during order creation").build();

        Mockito.when(assetService.getAssetOfCustomer(Mockito.anyLong(), Mockito.anyString())).thenReturn(asset);
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenThrow(RuntimeException.class);

        CreateOrderResponse response = orderService.createOrder(createOrderRequest);

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
    public void test_deleteOrder_exists_buy_successful() {
        Order order = Order.builder().id(1L).customerId(1L).orderSide(Side.BUY).price(1).size(1).status(Status.PENDING).build();
        DeleteOrderResponse deleteOrderResponse = DeleteOrderResponse.builder().success(Boolean.TRUE).message("Order deleted Successfully").build();
        AssetDto tryAssetDto = AssetDto.builder().usableSize(1).build();
        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(order));
        Mockito.when(assetService.getAssetOfCustomer(Mockito.anyLong(), Mockito.anyString())).thenReturn(tryAssetDto);

        DeleteOrderResponse response = orderService.deleteOrder(DeleteOrderRequest.builder().orderId(1L).build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(deleteOrderResponse, response);
        Mockito.verify(assetService, Mockito.times(1)).updateAssetOfCustomer(Mockito.anyLong(), Mockito.any(AssetDto.class));
    }

    @Test
    public void test_deleteOrder_exists_sell_successful() {
        Order order = Order.builder().id(1L).customerId(1L).orderSide(Side.SELL).assetName(TEST_ASSET_NAME).price(1).size(1).status(Status.PENDING).build();
        DeleteOrderResponse deleteOrderResponse = DeleteOrderResponse.builder().success(Boolean.TRUE).message("Order deleted Successfully").build();
        AssetDto assetDto = AssetDto.builder().usableSize(1).build();
        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(order));
        Mockito.when(assetService.getAssetOfCustomer(Mockito.anyLong(), Mockito.anyString())).thenReturn(assetDto);

        DeleteOrderResponse response = orderService.deleteOrder(DeleteOrderRequest.builder().orderId(1L).build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(deleteOrderResponse, response);
        Mockito.verify(assetService, Mockito.times(1)).updateAssetOfCustomer(Mockito.anyLong(), Mockito.any(AssetDto.class));
    }

    @Test
    public void test_deleteOrder_notExists() {
        DeleteOrderResponse deleteOrderResponse = DeleteOrderResponse.builder().success(Boolean.FALSE).message("Order does not exist").build();

        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        DeleteOrderResponse response = orderService.deleteOrder(DeleteOrderRequest.builder().orderId(1L).build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(deleteOrderResponse, response);
    }

    @Test
    public void test_deleteOrder_notPendingOrder() {
        Order order = Order.builder().id(1L).status(Status.MATCHED).build();
        DeleteOrderResponse deleteOrderResponse = DeleteOrderResponse.builder().success(Boolean.FALSE).message("Only Pending Orders can be deleted. Requested Order's status is: " + order.getStatus()).build();

        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(order));

        DeleteOrderResponse response = orderService.deleteOrder(DeleteOrderRequest.builder().orderId(1L).build());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(deleteOrderResponse, response);
    }
}
