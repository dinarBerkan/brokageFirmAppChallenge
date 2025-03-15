package com.challenge.brokagefirmapp.service;

import com.challenge.brokagefirmapp.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetService {
    private AssetRepository assetRepository;

    @Autowired
    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }
}
