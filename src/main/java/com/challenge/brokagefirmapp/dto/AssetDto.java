package com.challenge.brokagefirmapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetDto {
    private Long id;

    private Long customerId;

    private String assetName;

    private Integer assetSize;

    private Integer usableSize;
}
