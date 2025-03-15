package com.challenge.brokagefirmapp.controller;

import com.challenge.brokagefirmapp.dto.AssetDto;
import com.challenge.brokagefirmapp.response.ListAssetsResponse;
import com.challenge.brokagefirmapp.service.AssetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

public class AssetControllerTest {
    private AssetService assetService;

    private AssetController assetController;

    @BeforeEach
    public void setUp() {
        assetService = Mockito.mock(AssetService.class);
        assetController = new AssetController(assetService);
    }

    @Test
    public void test_listAssets() {
        AssetDto assetDto1 = AssetDto.builder().assetName("asset1").build();
        AssetDto assetDto2 = AssetDto.builder().assetName("asset2").build();
        List<AssetDto> assetDtos = Arrays.asList(assetDto1, assetDto2);
        ListAssetsResponse listAssetsResponse = ListAssetsResponse.builder().assets(assetDtos).build();

        Mockito.when(assetService.listAssets(Mockito.anyLong())).thenReturn(listAssetsResponse);

        ListAssetsResponse response = assetController.listAssets(1L);

        Assertions.assertEquals(assetDtos, response.getAssets());
    }
}
