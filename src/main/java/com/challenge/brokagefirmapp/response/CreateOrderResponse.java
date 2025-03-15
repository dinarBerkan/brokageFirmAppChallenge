package com.challenge.brokagefirmapp.response;

import com.challenge.brokagefirmapp.dto.OrderDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderResponse {
    private Boolean success;
    private String message;
    private OrderDto order;
}
