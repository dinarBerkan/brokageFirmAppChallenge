package com.challenge.brokagefirmapp.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteOrderResponse {
    private Boolean success;
    private String message;
}
