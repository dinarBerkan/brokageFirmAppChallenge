package com.challenge.brokagefirmapp.dto;

import com.challenge.brokagefirmapp.property.Side;
import com.challenge.brokagefirmapp.property.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;

    private Long customerId;

    private String assetName;

    private Side orderSide;

    private Integer size;

    private Double price;

    private Status status;

    private Date createDate;
}
