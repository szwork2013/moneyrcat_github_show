/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.emperises.monercat.domain.model;


public class ZcmRecommend extends BaseObject{
	
    // recId 	
	private String recId;
    // 推荐人ID 	
	private String recUserId;
    // 广告ID 	
	private String recAdId;
    // 姓名 	
	private String recName;
    // 手机号码 	
	private String recPhone;
    // 性别 	
	private String recGender;
    // 推荐时间 	
	private String recTime;
    // 推荐状态  0-成功 1-处理中  2-失败 	
	private String recStatus;
	
	/**
	 * 推荐状态
	 * 
	 */
	public static final String STATUS_SUCCESS="0"; //成功
	public static final String STATUS_ING="1"; //处理中
	public static final String STATUS_FALID="2"; //失败

	public void setRecId(String value) {
		this.recId = value;
	}
	
	public String getRecId() {
		return this.recId;
	}
	public void setRecUserId(String value) {
		this.recUserId = value;
	}
	
	public String getRecUserId() {
		return this.recUserId;
	}
	public void setRecAdId(String value) {
		this.recAdId = value;
	}
	
	public String getRecAdId() {
		return this.recAdId;
	}
	public void setRecName(String value) {
		this.recName = value;
	}
	
	public String getRecName() {
		return this.recName;
	}
	public void setRecPhone(String value) {
		this.recPhone = value;
	}
	
	public String getRecPhone() {
		return this.recPhone;
	}
	public void setRecGender(String value) {
		this.recGender = value;
	}
	
	public String getRecGender() {
		return this.recGender;
	}
	public void setRecTime(String value) {
		this.recTime = value;
	}
	
	public String getRecTime() {
		return this.recTime;
	}
	public void setRecStatus(String value) {
		this.recStatus = value;
	}
	
	public String getRecStatus() {
		return this.recStatus;
	}
}

