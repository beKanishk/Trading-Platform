package com.Project.trading.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Project.trading.model.Asset;
import com.Project.trading.model.Coin;
import com.Project.trading.model.User;
import com.Project.trading.repository.AssetRepository;
import com.Project.trading.serviceInterface.AssetServiceInterface;

@Service
public class AssetService implements AssetServiceInterface{

	@Autowired
	private AssetRepository assetRepository;
	
	@Override
	public Asset createAsset(User user, Coin coin, double quantity) {
		Asset asset = new Asset();
		asset.setUser(user);
		asset.setCoin(coin);
		asset.setQuantity(quantity);
		asset.setBuyPrice(coin.getCurrentPrice());
		
		return assetRepository.save(asset);
	}

	@Override
	public Asset getAssetById(Long assetId) throws Exception {
	    return assetRepository.findById(assetId)
	            .orElseThrow(() -> new Exception("Asset with ID " + assetId + " not found"));
	}


	@Override
	public Asset getAssetByUserIdAndAssetId(Long userId, Long assetId) throws Exception {
		return assetRepository.findById(assetId).orElseThrow(() -> new Exception("Asset not found"));
	}

	@Override
	public List<Asset> getUsersAssets(Long userId) {
		return assetRepository.findByUserId(userId);
	}

	@Override
	public Asset updateAsset(Long assetId, double quantity) throws Exception {
		Asset oldAsset = getAssetById(assetId);
		oldAsset.setQuantity(quantity + oldAsset.getQuantity());
		return assetRepository.save(oldAsset);
	}

	@Override
	public Asset getAssetByUserIdAndCoinId(Long userId, String coinId) {
		return assetRepository.findByUserIdAndCoinId(userId, coinId);
	}

	@Override
	public void deleteAsset(Long assetId) {
		assetRepository.deleteById(assetId);
		
	}
	
}
