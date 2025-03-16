package com.challenge.brokagefirmapp.service;

import com.challenge.brokagefirmapp.domain.Asset;
import com.challenge.brokagefirmapp.dto.AssetDto;
import com.challenge.brokagefirmapp.mapper.AssetMapper;
import com.challenge.brokagefirmapp.repository.AssetRepository;
import com.challenge.brokagefirmapp.response.ListAssetsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {
    private final AssetRepository assetRepository;

    @Autowired
    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public ListAssetsResponse listAssets(Long customerId) {
        List<Asset> assetList = assetRepository.findByCustomerId(customerId);
        return ListAssetsResponse.builder().assets(assetList.stream().map(AssetMapper.assetMapper::assetToAssetDto).toList()).build();
    }

    public AssetDto getAssetOfCustomer(Long customerId, String assetName) {
        Asset asset = assetRepository.findByCustomerIdAndAssetName(customerId, assetName);
        return AssetMapper.assetMapper.assetToAssetDto(asset);
    }

    public void updateAssetOfCustomer(Long customerId, AssetDto assetDto) {
        Asset asset = assetRepository.findByCustomerIdAndAssetName(customerId, assetDto.getAssetName());
        if (asset != null) {
            asset.setUsableSize(assetDto.getUsableSize());
            assetRepository.save(asset);
        }
    }
}
