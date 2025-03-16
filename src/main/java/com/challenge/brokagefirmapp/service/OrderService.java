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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private static final String TRY_ASSET = "TRY";

    private final AssetService assetService;

    private final OrderRepository orderRepository;


    @Autowired
    public OrderService(AssetService assetService, OrderRepository orderRepository) {
        this.assetService = assetService;
        this.orderRepository = orderRepository;
    }

    public CreateOrderResponse createOrder(CreateOrderRequest createOrderRequest) {
        CreateOrderResponse createOrderResponse = CreateOrderResponse.builder().build();
        AssetDto tryAsset = assetService.getAssetOfCustomer(createOrderRequest.getCustomerId(), TRY_ASSET);

        if(Side.BUY.equals(createOrderRequest.getSide())) {
            Integer totalAmount = createOrderRequest.getPrice() * createOrderRequest.getSize();
            if (tryAsset != null && tryAsset.getUsableSize() >= totalAmount) {
                createOrderResponse = saveOrder(createOrderRequest);
                tryAsset.setUsableSize(tryAsset.getUsableSize() - totalAmount);
                assetService.updateAssetOfCustomer(tryAsset.getCustomerId(), tryAsset);
            } else {
                createOrderResponse.setSuccess(Boolean.FALSE);
                createOrderResponse.setMessage("Customer does not have enough TRY asset size");
            }
        } else {
            AssetDto chosenAsset = assetService.getAssetOfCustomer(createOrderRequest.getCustomerId(), createOrderRequest.getAssetName());
            if(chosenAsset != null && chosenAsset.getUsableSize() >= createOrderRequest.getSize()) {
                createOrderResponse = saveOrder(createOrderRequest);
                chosenAsset.setUsableSize(chosenAsset.getUsableSize() - createOrderRequest.getSize());
                assetService.updateAssetOfCustomer(tryAsset.getCustomerId(), chosenAsset);
            } else {
                createOrderResponse.setSuccess(Boolean.FALSE);
                createOrderResponse.setMessage("Customer does not have enough " + createOrderRequest.getAssetName() + " asset size");
            }
        }
        return createOrderResponse;
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
                if(Side.BUY.equals(orderToDelete.getOrderSide())) {
                    Integer totalAmount = orderToDelete.getPrice() * orderToDelete.getSize();
                    AssetDto tryAssetOfCustomer = assetService.getAssetOfCustomer(orderToDelete.getCustomerId(), TRY_ASSET);
                    tryAssetOfCustomer.setUsableSize(tryAssetOfCustomer.getUsableSize() + totalAmount);
                    assetService.updateAssetOfCustomer(orderToDelete.getCustomerId(), tryAssetOfCustomer);
                } else {
                    AssetDto assetOfCustomer = assetService.getAssetOfCustomer(orderToDelete.getCustomerId(), orderToDelete.getAssetName());
                    assetOfCustomer.setUsableSize(assetOfCustomer.getUsableSize() + orderToDelete.getSize());
                    assetService.updateAssetOfCustomer(orderToDelete.getCustomerId(), assetOfCustomer);
                }
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

    private CreateOrderResponse saveOrder(CreateOrderRequest createOrderRequest) {
        CreateOrderResponse createOrderResponse = CreateOrderResponse.builder().build();

        Date createDate = Date.from(Instant.now());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDateStr = sdf.format(createDate);
        try {
            Date formattedCreateDate = sdf.parse(formattedDateStr);
            Order order = Order.builder()
                    .customerId(createOrderRequest.getCustomerId())
                    .assetName(createOrderRequest.getAssetName())
                    .orderSide(createOrderRequest.getSide())
                    .price(createOrderRequest.getPrice())
                    .size(createOrderRequest.getSize())
                    .status(Status.PENDING)
                    .createDate(formattedCreateDate)
                    .build();
            order = orderRepository.save(order);
            createOrderResponse.setOrder(OrderMapper.orderMapper.orderToOrderDto(order));
            createOrderResponse.setSuccess(Boolean.TRUE);
            createOrderResponse.setMessage("Order created successfully");
        } catch (Exception e) {
            createOrderResponse.setSuccess(Boolean.FALSE);
            createOrderResponse.setMessage("Exception occurred during order creation");
        }
        return createOrderResponse;
    }
}
