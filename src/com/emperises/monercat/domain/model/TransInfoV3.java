package com.emperises.monercat.domain.model;

import java.util.List;

import com.emperises.monercat.domain.DomainObject;

public class TransInfoV3 extends DomainObject {
	private List<TransInfoV3 > rows;
	private String trAmount;
	private String trBalance;
	private String trBeginTime;
	private String trRemark;
	private String trInOrOut;
	private String trEndTime;
	private String trType;
	public String getTrAmount() {
		return trAmount;
	}
	public void setTrAmount(String trAmount) {
		this.trAmount = trAmount;
	}
	public String getTrBalance() {
		return trBalance;
	}
	public void setTrBalance(String trBalance) {
		this.trBalance = trBalance;
	}
	public String getTrBeginTime() {
		return trBeginTime;
	}
	public void setTrBeginTime(String trBeginTime) {
		this.trBeginTime = trBeginTime;
	}
	public String getTrRemark() {
		return trRemark;
	}
	public void setTrRemark(String trRemark) {
		this.trRemark = trRemark;
	}
	public String getTrInOrOut() {
		return trInOrOut;
	}
	public void setTrInOrOut(String trInOrOut) {
		this.trInOrOut = trInOrOut;
	}
	public String getTrEndTime() {
		return trEndTime;
	}
	public void setTrEndTime(String trEndTime) {
		this.trEndTime = trEndTime;
	}
	public String getTrType() {
		return trType;
	}
	public void setTrType(String trType) {
		this.trType = trType;
	}
	public List<TransInfoV3 > getRows() {
		return rows;
	}
	public void setRows(List<TransInfoV3 > rows) {
		this.rows = rows;
	}
	
}
