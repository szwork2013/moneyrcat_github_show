package com.emperises.monercat.domain;


public class ADInfo extends DomainObject {
	private String adId;// 
	private int adIconResId;
	private String adImage; // 
	private String adDescription; //
	private String adSource;// 
	private String adIcon; // 
	private String adAward; // 
	private String adType;//
	private String adUrl;//html5
	private String recommendPrice;//推荐价格
	private String clickPrice;//点击价格
	private static final long serialVersionUID = 1L;
	private String adTtile; // 

	public String getAdTtile() {
		return adTtile;
	}

	public void setAdTtile(String adTtile) {
		this.adTtile = adTtile;
	}

	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	public String getAdImage() {
		return adImage;
	}

	public void setAdImage(String adImage) {
		this.adImage = adImage;
	}

	public String getAdDescription() {
		return adDescription;
	}

	public void setAdDescription(String adDescription) {
		this.adDescription = adDescription;
	}

	public String getAdSource() {
		return adSource;
	}

	public void setAdSource(String adSource) {
		this.adSource = adSource;
	}

	public String getAdIcon() {
		return adIcon;
	}

	public void setAdIcon(String adIcon) {
		this.adIcon = adIcon;
	}

	public String getAdAward() {
		return adAward;
	}

	public void setAdAward(String adAward) {
		this.adAward = adAward;
	}

	public String getAdType() {
		return adType;
	}

	public void setAdType(String adType) {
		this.adType = adType;
	}

	

	@Override
	public String toString() {
		return "ADInfo [adTtile=" + adTtile + ", adId=" + adId + ", adImage="
				+ adImage + ", adDescription=" + adDescription + ", adSource="
				+ adSource + ", adIcon=" + adIcon + ", adAward=" + adAward
				+ ", adType=" + adType + "]";
	}

	public String getClickPrice() {
		return clickPrice;
	}

	public void setClickPrice(String clickPrice) {
		this.clickPrice = clickPrice;
	}

	public String getRecommendPrice() {
		return recommendPrice;
	}

	public void setRecommendPrice(String recommendPrice) {
		this.recommendPrice = recommendPrice;
	}

	public String getAdUrl() {
		return adUrl;
	}

	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}

	public int getAdIconResId() {
		return adIconResId;
	}

	public void setAdIconResId(int adIconResId) {
		this.adIconResId = adIconResId;
	}

}
