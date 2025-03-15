package com.challenge.brokagefirmapp.request;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ListOrdersRequest {
    private Long customerId;

    private Date fromDate;

    private Date toDate;
}
