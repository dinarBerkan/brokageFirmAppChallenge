package com.challenge.brokagefirmapp.request;

import com.challenge.brokagefirmapp.property.Side;
import lombok.Data;

@Data
public class CreateOrderRequest {
    private Long customerId;

    private String assetName;

    private Side side;

    private Integer size;

    private Double price;
}
