/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.emperises.monercat.domain.model;


public class ZcmAdertising extends BaseObject{

	private String adUrl;
	
	private String adAwardBalance;
    // adId 	
	private String adId;
    // 标题 	
	private String adTitle;
    // 广告内容 	
	private String adContent;
    // 广告图片 	
	private String adImage;
    // 广告图标 	
	private String adIcon;
    // 广告厂商 	
	private String adSource;
    // 广告赏金 	
	private String adAward;
    // 广告类型  	
	private String adType;
    // 广告状态 0-禁用 1-启用 	
	private String adStatus;
    // 创建时间 	
	private String adCreateTime;
    // 创建时间 	
	private String adAddUser;
    // 是否可以推荐 0-不可以  1-可以 	
	private String adIsRec;
    // 是否在首页显示 0-不显示 1-显示 	
	private String adIsTop;
	// 任务开始时间 	
	private String adStartTime;
    // 任务结束时间 	
	private String adEndTime;
	private String ad_desc;

	public void setAdId(String value) {
		this.adId = value;
	}
	
	public String getAdId() {
		return this.adId;
	}
	public void setAdTitle(String value) {
		this.adTitle = value;
	}
	
	public String getAdTitle() {
		return this.adTitle;
	}
	public void setAdContent(String value) {
		this.adContent = value;
	}
	
	public String getAdContent() {
		return this.adContent;
	}
	public void setAdImage(String value) {
		this.adImage = value;
	}
	
	public String getAdImage() {
		return this.adImage;
	}
	public void setAdIcon(String value) {
		this.adIcon = value;
	}
	
	public String getAdIcon() {
		return this.adIcon;
	}
	public void setAdSource(String value) {
		this.adSource = value;
	}
	
	public String getAdSource() {
		return this.adSource;
	}
	public void setAdAward(String value) {
		this.adAward = value;
	}
	
	public String getAdAward() {
		return this.adAward;
	}
	public void setAdType(String value) {
		this.adType = value;
	}
	
	public String getAdType() {
		return this.adType;
	}
	public void setAdStatus(String value) {
		this.adStatus = value;
	}
	
	public String getAdStatus() {
		return this.adStatus;
	}
	public void setAdCreateTime(String value) {
		this.adCreateTime = value;
	}
	
	public String getAdCreateTime() {
		return this.adCreateTime;
	}
	public void setAdAddUser(String value) {
		this.adAddUser = value;
	}
	
	public String getAdAddUser() {
		return this.adAddUser;
	}
	public void setAdIsRec(String value) {
		this.adIsRec = value;
	}
	
	public String getAdIsRec() {
		return this.adIsRec;
	}
	public void setAdIsTop(String value) {
		this.adIsTop = value;
	}
	
	public String getAdIsTop() {
		return this.adIsTop;
	}

	public String getAdStartTime() {
		return adStartTime;
	}

	public void setAdStartTime(String adStartTime) {
		this.adStartTime = adStartTime;
	}

	public String getAdEndTime() {
		return adEndTime;
	}

	public void setAdEndTime(String adEndTime) {
		this.adEndTime = adEndTime;
	}

	public String getAdUrl() {
		return adUrl;
	}

	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}

	public String getAd_award_balance() {
		return adAwardBalance;
	}

	public void setAd_award_balance(String ad_award_balance) {
		this.adAwardBalance = ad_award_balance;
	}

	public String getAd_desc() {
		return ad_desc;
	}

	public void setAd_desc(String ad_desc) {
		this.ad_desc = ad_desc;
	}
}

