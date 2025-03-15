package com.challenge.brokagefirmapp.response;

import com.challenge.brokagefirmapp.dto.AssetDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ListAssetsResponse {
    List<AssetDto> assets;
}
