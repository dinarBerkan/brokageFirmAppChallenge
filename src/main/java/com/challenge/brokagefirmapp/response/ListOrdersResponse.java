package com.challenge.brokagefirmapp.response;

import com.challenge.brokagefirmapp.dto.OrderDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ListOrdersResponse {
    List<OrderDto> orders;
}
