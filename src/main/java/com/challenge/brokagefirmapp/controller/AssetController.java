package com.challenge.brokagefirmapp.controller;

import com.challenge.brokagefirmapp.response.ListAssetsResponse;
import com.challenge.brokagefirmapp.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/asset")
public class AssetController {
    private final AssetService assetService;

    @Autowired
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping("/list")
    public ListAssetsResponse listAssets(@RequestParam Long customerId) {
        return assetService.listAssets(customerId);
    }
}
