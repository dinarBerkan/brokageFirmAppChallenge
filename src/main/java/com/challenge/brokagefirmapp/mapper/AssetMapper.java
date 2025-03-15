package com.challenge.brokagefirmapp.mapper;

import com.challenge.brokagefirmapp.domain.Asset;
import com.challenge.brokagefirmapp.dto.AssetDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AssetMapper {
    AssetMapper assetMapper = Mappers.getMapper(AssetMapper.class);

    AssetDto assetToAssetDto(Asset asset);
}
