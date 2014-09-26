/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.emperises.monercat.domain.model;


public class ZcmApp extends BaseObject{
	
	private ZcmApp val;
    // appId 	
	private String appId;
    // 终端类型 0-android 1-IOS 	
	private String appType;
    // 版本 	
	private String appVersion;
    // 更新标题 	
	private String appTitle;
    // 更新内容 	
	private String appContent;
    // 下载地址 	
	private String appUrl;
    // 创建时间 	
	private String appCreateTime;
    // 创建人
	private String appUser;
	
	

	public void setAppId(String value) {
		this.appId = value;
	}
	
	public String getAppId() {
		return this.appId;
	}
	public void setAppType(String value) {
		this.appType = value;
	}
	
	public String getAppType() {
		return this.appType;
	}
	public void setAppVersion(String value) {
		this.appVersion = value;
	}
	
	public String getAppVersion() {
		return this.appVersion;
	}
	public void setAppTitle(String value) {
		this.appTitle = value;
	}
	
	public String getAppTitle() {
		return this.appTitle;
	}
	public void setAppContent(String value) {
		this.appContent = value;
	}
	
	public String getAppContent() {
		return this.appContent;
	}
	public void setAppUrl(String value) {
		this.appUrl = value;
	}
	
	public String getAppUrl() {
		return this.appUrl;
	}
	public void setAppCreateTime(String value) {
		this.appCreateTime = value;
	}
	
	public String getAppCreateTime() {
		return this.appCreateTime;
	}
	public void setAppUser(String value) {
		this.appUser = value;
	}
	
	public String getAppUser() {
		return this.appUser;
	}

	public ZcmApp getVal() {
		return val;
	}

	public void setVal(ZcmApp val) {
		this.val = val;
	}
}

