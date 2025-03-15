package com.challenge.brokagefirmapp.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteOrderRequest {
    private Long orderId;
}
