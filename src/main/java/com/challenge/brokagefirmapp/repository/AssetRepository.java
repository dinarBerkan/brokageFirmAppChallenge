package com.challenge.brokagefirmapp.repository;

import com.challenge.brokagefirmapp.domain.Asset;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends CrudRepository<Asset, Long> {
    List<Asset> findByCustomerId(Long customerId);
}
