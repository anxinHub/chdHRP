package com.chd.hrp.mat.entity;
import java.util.List;
 
@SuppressWarnings("rawtypes")
public class DzbqMain {
	String PlanTime;
	String ExpiryTime;
	String ShopCode;
	String Template;
	List GoodsList;
	public String getPlanTime() {
		return PlanTime;
	}
	public void setPlanTime(String planTime) {
		PlanTime = planTime;
	}
	public String getExpiryTime() {
		return ExpiryTime;
	}
	public void setExpiryTime(String expiryTime) {
		ExpiryTime = expiryTime;
	}
	public String getShopCode() {
		return ShopCode;
	}
	public void setShopCode(String shopCode) {
		ShopCode = shopCode;
	}
	public String getTemplate() {
		return Template;
	}
	public void setTemplate(String template) {
		Template = template;
	}
	
	public List getGoodsList() {
		return GoodsList;
	}
	public void setGoodsList(List goodsList) {
		GoodsList = goodsList;
	}

}
