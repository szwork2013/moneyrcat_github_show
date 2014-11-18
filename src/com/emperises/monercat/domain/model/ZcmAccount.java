/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.emperises.monercat.domain.model;


public class ZcmAccount extends BaseObject{
	
    // aid 	
	private String aid;
    // 用户ID 	
	private String auserId;
    // 0-积分账户  1-RMB账户 	
	private String atype;
    // 账户余额 	
	private String abalance;
	// 冻结余额 	
	private String aCoolBalance;
    // 账户状态 0-禁用  1-启用 	
	private String astatus;
    // 创建时间 	
	private String acreateTime;
    // 最后修改时间 	
	private String amodifyTime;
	
	public String getaCoolBalance() {
		return aCoolBalance;
	}

	public void setaCoolBalance(String aCoolBalance) {
		this.aCoolBalance = aCoolBalance;
	}

	public void setAid(String value) {
		this.aid = value;
	}
	
	public String getAid() {
		return this.aid;
	}
	public void setAuserId(String value) {
		this.auserId = value;
	}
	
	public String getAuserId() {
		return this.auserId;
	}
	public void setAtype(String value) {
		this.atype = value;
	}
	
	public String getAtype() {
		return this.atype;
	}
	public void setAbalance(String value) {
		this.abalance = value;
	}
	
	public String getAbalance() {
		return this.abalance;
	}
	public void setAstatus(String value) {
		this.astatus = value;
	}
	
	public String getAstatus() {
		return this.astatus;
	}
	public void setAcreateTime(String value) {
		this.acreateTime = value;
	}
	
	public String getAcreateTime() {
		return this.acreateTime;
	}
	public void setAmodifyTime(String value) {
		this.amodifyTime = value;
	}
	
	public String getAmodifyTime() {
		return this.amodifyTime;
	}
}

