package com.challenge.brokagefirmapp.mapper;

import com.challenge.brokagefirmapp.domain.Order;
import com.challenge.brokagefirmapp.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    OrderDto orderToOrderDto(Order order);

    Order orderDtoToOrder(OrderDto orderDto);
}
