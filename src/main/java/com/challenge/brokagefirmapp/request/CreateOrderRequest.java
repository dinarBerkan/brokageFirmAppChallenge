package com.challenge.brokagefirmapp.request;

import com.challenge.brokagefirmapp.property.Side;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateOrderRequest {
    private Long customerId;

    private String assetName;

    private Side side;

    private Integer size;

    private Double price;
}
