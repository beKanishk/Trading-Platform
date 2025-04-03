package com.Project.trading.response;

public class FunctionResponse {
	private String currencyName;
	private String functionName;
	private String currencyData;
	
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getCurrencyData() {
		return currencyData;
	}
	public void setCurrencyData(String currencyData) {
		this.currencyData = currencyData;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
}
