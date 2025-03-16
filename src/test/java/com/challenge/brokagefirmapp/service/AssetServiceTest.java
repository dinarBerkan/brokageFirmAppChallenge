package com.challenge.brokagefirmapp.service;

import com.challenge.brokagefirmapp.domain.Asset;
import com.challenge.brokagefirmapp.dto.AssetDto;
import com.challenge.brokagefirmapp.mapper.AssetMapper;
import com.challenge.brokagefirmapp.repository.AssetRepository;
import com.challenge.brokagefirmapp.response.ListAssetsResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class AssetServiceTest {
    private static final String TEST_ASSET_NAME = "test";

    private AssetRepository assetRepository;

    private AssetService assetService;

    @BeforeEach
    public void setUp() {
        assetRepository = Mockito.mock(AssetRepository.class);
        assetService = new AssetService(assetRepository);
    }

    @Test
    public void test_listAssets() {
        Asset asset1 = Asset.builder().id(1L).build();
        Asset asset2 = Asset.builder().id(2L).build();
        List<Asset> assets = Arrays.asList(asset1, asset2);
        List<AssetDto> assetDtos = assets.stream().map(AssetMapper.assetMapper::assetToAssetDto).toList();
        ListAssetsResponse listAssetsResponse = ListAssetsResponse.builder().assets(assetDtos).build();

        Mockito.when(assetRepository.findByCustomerId(Mockito.anyLong())).thenReturn(assets);

        ListAssetsResponse response = assetService.listAssets(1L);

        Assertions.assertNotNull(response);
        Assertions.assertEquals(listAssetsResponse, response);
    }

    @Test
    public void test_getAssetOfCustomer() {
        Asset asset = Asset.builder().id(1L).assetName(TEST_ASSET_NAME).build();
        AssetDto assetDto = AssetMapper.assetMapper.assetToAssetDto(asset);
        Mockito.when(assetRepository.findByCustomerIdAndAssetName(Mockito.anyLong(), Mockito.anyString())).thenReturn(asset);

        AssetDto returnedDto = assetService.getAssetOfCustomer(1L, TEST_ASSET_NAME);

        Assertions.assertNotNull(returnedDto);
        Assertions.assertEquals(assetDto, returnedDto);
    }

    @Test
    public void test_updateAssetOfCustomer_updated() {
        Asset asset = Asset.builder().id(1L).assetName(TEST_ASSET_NAME).build();
        AssetDto assetDto = AssetDto.builder().customerId(1L).assetName(TEST_ASSET_NAME).build();
        Mockito.when(assetRepository.findByCustomerIdAndAssetName(Mockito.anyLong(), Mockito.anyString())).thenReturn(asset);

        assetService.updateAssetOfCustomer(1L, assetDto);
        Mockito.verify(assetRepository, Mockito.times(1)).save(Mockito.any(Asset.class));
    }

    @Test
    public void test_updateAssetOfCustomer_notUpdated() {
        AssetDto assetDto = AssetDto.builder().customerId(1L).assetName(TEST_ASSET_NAME).build();
        Mockito.when(assetRepository.findByCustomerIdAndAssetName(Mockito.anyLong(), Mockito.anyString())).thenReturn(null);

        assetService.updateAssetOfCustomer(1L, assetDto);
        Mockito.verify(assetRepository, Mockito.never()).save(Mockito.any(Asset.class));
    }
}
