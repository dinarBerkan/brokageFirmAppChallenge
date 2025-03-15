package com.challenge.brokagefirmapp.domain;

import com.challenge.brokagefirmapp.property.Side;
import com.challenge.brokagefirmapp.property.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "STOCK_ORDER")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long customerId;

    private String assetName;

    private Side orderSide;

    private Integer size;

    private Double price;

    private Status status;

    @CreatedDate
    private Date createDate;
}
