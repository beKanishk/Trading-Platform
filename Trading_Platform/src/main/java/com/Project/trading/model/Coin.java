package com.Project.trading.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Coin {
	@Override
	public String toString() {
		return "Coin [id=" + id + ", symbol=" + symbol + ", name=" + name + ", image=" + image + ", currentPrice="
				+ currentPrice + ", marketCap=" + marketCap + ", marketCapRank=" + marketCapRank
				+ ", fullyDilutedValuation=" + fullyDilutedValuation + ", totalVolume=" + totalVolume + ", high24h="
				+ high24h + ", low24h=" + low24h + ", priceChange24h=" + priceChange24h + ", priceChangePercentage24h="
				+ priceChangePercentage24h + ", marketCapChange24h=" + marketCapChange24h
				+ ", marketCapChangePercentage24h=" + marketCapChangePercentage24h + ", circulatingSupply="
				+ circulatingSupply + ", totalSupply=" + totalSupply + ", maxSupply=" + maxSupply + ", ath=" + ath
				+ ", athChangePercentage=" + athChangePercentage + ", athDate=" + athDate + ", atl=" + atl
				+ ", atlChangePercentage=" + atlChangePercentage + ", atlDate=" + atlDate + ", roi=" + roi
				+ ", lastUpdated=" + lastUpdated + "]";
	}
	@Id
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("symbol")
    private String symbol;
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("image")
    private String image;
    
    @JsonProperty("current_price")
    private double currentPrice;
    
    @JsonProperty("market_cap")
    private double marketCap;
    
    @JsonProperty("market_cap_rank")
    private int marketCapRank;
    
    @JsonProperty("fully_diluted_valuation")
    private double fullyDilutedValuation;
    
    @JsonProperty("total_volume")
    private double totalVolume;
    
    @JsonProperty("high_24h")
    private double high24h;
    
    @JsonProperty("low_24h")
    private double low24h;
    
    @JsonProperty("price_change_24h")
    private double priceChange24h;
    
    @JsonProperty("price_change_percentage_24h")
    private double priceChangePercentage24h;
    
    @JsonProperty("market_cap_change_24h")
    private double marketCapChange24h;
    
    @JsonProperty("market_cap_change_percentage_24h")
    private double marketCapChangePercentage24h;
    
    @JsonProperty("circulating_supply")
    private double circulatingSupply;
    
    @JsonProperty("total_supply")
    private double totalSupply;
    
    @JsonProperty("max_supply")
    private Double maxSupply;
    
    @JsonProperty("ath")
    private double ath;
    
    @JsonProperty("ath_change_percentage")
    private double athChangePercentage;
    
    @JsonProperty("ath_date")
    private Date athDate;
    
    @JsonProperty("atl")
    private double atl;
    
    @JsonProperty("atl_change_percentage")
    private double atlChangePercentage;
    
    @JsonProperty("atl_date")
    private Date atlDate;
    
    @JsonProperty("roi")
    @JsonIgnore
    private Double roi;
    
    @JsonProperty("last_updated")
    private Date lastUpdated;

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getSymbol() { return symbol; }
    public void setSymbol(String symbol) { this.symbol = symbol; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public double getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(double currentPrice) { this.currentPrice = currentPrice; }
    public double getMarketCap() { return marketCap; }
    public void setMarketCap(double marketCap) { this.marketCap = marketCap; }
    public int getMarketCapRank() { return marketCapRank; }
    public void setMarketCapRank(int marketCapRank) { this.marketCapRank = marketCapRank; }
    public double getFullyDilutedValuation() { return fullyDilutedValuation; }
    public void setFullyDilutedValuation(double fullyDilutedValuation) { this.fullyDilutedValuation = fullyDilutedValuation; }
    public double getTotalVolume() { return totalVolume; }
    public void setTotalVolume(double totalVolume) { this.totalVolume = totalVolume; }
    public double getHigh24h() { return high24h; }
    public void setHigh24h(double high24h) { this.high24h = high24h; }
    public double getLow24h() { return low24h; }
    public void setLow24h(double low24h) { this.low24h = low24h; }
    public double getPriceChange24h() { return priceChange24h; }
    public void setPriceChange24h(double priceChange24h) { this.priceChange24h = priceChange24h; }
    public double getPriceChangePercentage24h() { return priceChangePercentage24h; }
    public void setPriceChangePercentage24h(double priceChangePercentage24h) { this.priceChangePercentage24h = priceChangePercentage24h; }
    public double getMarketCapChange24h() { return marketCapChange24h; }
    public void setMarketCapChange24h(double marketCapChange24h) { this.marketCapChange24h = marketCapChange24h; }
    public double getMarketCapChangePercentage24h() { return marketCapChangePercentage24h; }
    public void setMarketCapChangePercentage24h(double marketCapChangePercentage24h) { this.marketCapChangePercentage24h = marketCapChangePercentage24h; }
    public double getCirculatingSupply() { return circulatingSupply; }
    public void setCirculatingSupply(double circulatingSupply) { this.circulatingSupply = circulatingSupply; }
    public double getTotalSupply() { return totalSupply; }
    public void setTotalSupply(double totalSupply) { this.totalSupply = totalSupply; }
    public Double getMaxSupply() { return maxSupply; }
    public void setMaxSupply(Double maxSupply) { this.maxSupply = maxSupply; }
    public double getAth() { return ath; }
    public void setAth(double ath) { this.ath = ath; }
    public double getAthChangePercentage() { return athChangePercentage; }
    public void setAthChangePercentage(double athChangePercentage) { this.athChangePercentage = athChangePercentage; }
    public Date getAthDate() { return athDate; }
    public void setAthDate(Date athDate) { this.athDate = athDate; }
    public double getAtl() { return atl; }
    public void setAtl(double atl) { this.atl = atl; }
    public double getAtlChangePercentage() { return atlChangePercentage; }
    public void setAtlChangePercentage(double atlChangePercentage) { this.atlChangePercentage = atlChangePercentage; }
    public Date getAtlDate() { return atlDate; }
    public void setAtlDate(Date atlDate) { this.atlDate = atlDate; }
    public Double getRoi() { return roi; }
    public void setRoi(Double roi) { this.roi = roi; }
    public Date getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(Date lastUpdated) { this.lastUpdated = lastUpdated; }
}
