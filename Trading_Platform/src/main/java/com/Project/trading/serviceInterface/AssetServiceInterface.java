package com.Project.trading.serviceInterface;

import java.util.List;

import com.Project.trading.model.Asset;
import com.Project.trading.model.Coin;
import com.Project.trading.model.User;

public interface AssetServiceInterface {
	Asset createAsset(User user, Coin coin, double quantity);
	
	Asset getAssetById(Long assetId) throws Exception;
	
	Asset getAssetByUserIdAndAssetId(Long userId, Long assetId) throws Exception;
	
	List<Asset> getUsersAssets(Long userId);
	
	Asset updateAsset(Long assetId, double quantity) throws Exception;
	
	Asset getAssetByUserIdAndCoinId(Long userId, String coinId);
	
	void deleteAsset(Long assetId);
}
