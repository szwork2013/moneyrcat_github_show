/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.emperises.monercat.domain.model;

import java.util.List;

public class ZcmAccountDetail extends BaseObject{
	
	private List<ZcmAccountDetail> rows;
    // adId 	
	private String adId;
    // 账户ID 	
	private String gaAid;
    // 用户ID 	
	private String gaUserId;
    // 1-收入  2- 支出 	
	private String gaType;
    // 收入金额 	
	private String gaIncome;
    // 支出金额 	
	private String gaExpen;
    // 状态 0-处理中 1-成功 2-处理失败 	
	private String gaStatus;
    // 创建时间 	
	private String gaCreateTime;
    // 来源(广告ID) 	
	private String gaAdId;
    // 交易ID(交易表ID) 	
	private String gaTrId;
	

	public void setAdId(String value) {
		this.adId = value;
	}
	
	public String getAdId() {
		return this.adId;
	}
	public void setGaAid(String value) {
		this.gaAid = value;
	}
	
	public String getGaAid() {
		return this.gaAid;
	}
	public void setGaUserId(String value) {
		this.gaUserId = value;
	}
	
	public String getGaUserId() {
		return this.gaUserId;
	}
	public void setGaType(String value) {
		this.gaType = value;
	}
	
	public String getGaType() {
		return this.gaType;
	}
	public void setGaIncome(String value) {
		this.gaIncome = value;
	}
	
	public String getGaIncome() {
		return this.gaIncome;
	}
	public void setGaExpen(String value) {
		this.gaExpen = value;
	}
	
	public String getGaExpen() {
		return this.gaExpen;
	}
	public void setGaStatus(String value) {
		this.gaStatus = value;
	}
	
	public String getGaStatus() {
		return this.gaStatus;
	}
	public void setGaCreateTime(String value) {
		this.gaCreateTime = value;
	}
	
	public String getGaCreateTime() {
		return this.gaCreateTime;
	}
	public void setGaAdId(String value) {
		this.gaAdId = value;
	}
	
	public String getGaAdId() {
		return this.gaAdId;
	}
	public void setGaTrId(String value) {
		this.gaTrId = value;
	}
	
	public String getGaTrId() {
		return this.gaTrId;
	}

	public List<ZcmAccountDetail> getRows() {
		return rows;
	}

	public void setRows(List<ZcmAccountDetail> rows) {
		this.rows = rows;
	}
}

