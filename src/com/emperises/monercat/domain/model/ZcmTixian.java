/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.emperises.monercat.domain.model;


public class ZcmTixian extends BaseObject{
	
    // tid 	
	private String tid;
    // 交易表ID 	
	private String ttrId;
    // 用户ID 	
	private String tuserId;
    // 用户名称 	
	private String tuserName;
    // 银行卡号(或支付宝账号) 	
	private String tuserBankNum;
    // 开户行 	
	private String tbankName;
    // 提现方式  0-银行卡提现  1-支付宝 	
	private String ttype;
    // 提现状态 0-处理中 1-提现成功 2-提现失败 	
	private String tstatus;
    // 提现时间 	
	private String ttime;
    // 修改人 	
	private String tmodifyUserId;
    // 最后修改时间 	
	private String tmodifyTime;
	
	/**
	 * 提现状态
	 */
	public static final String STATUS_ING="0"; //处理中
	public static final String STATUS_SUCCESS="1"; //提现成功
	public static final String STATUS_FALID="2"; //提现失败

	public void setTid(String value) {
		this.tid = value;
	}
	
	public String getTid() {
		return this.tid;
	}
	public void setTtrId(String value) {
		this.ttrId = value;
	}
	
	public String getTtrId() {
		return this.ttrId;
	}
	public void setTuserId(String value) {
		this.tuserId = value;
	}
	
	public String getTuserId() {
		return this.tuserId;
	}
	public void setTuserName(String value) {
		this.tuserName = value;
	}
	
	public String getTuserName() {
		return this.tuserName;
	}
	public void setTuserBankNum(String value) {
		this.tuserBankNum = value;
	}
	
	public String getTuserBankNum() {
		return this.tuserBankNum;
	}
	public void setTbankName(String value) {
		this.tbankName = value;
	}
	
	public String getTbankName() {
		return this.tbankName;
	}
	public void setTtype(String value) {
		this.ttype = value;
	}
	
	public String getTtype() {
		return this.ttype;
	}
	public void setTstatus(String value) {
		this.tstatus = value;
	}
	
	public String getTstatus() {
		return this.tstatus;
	}
	public void setTtime(String value) {
		this.ttime = value;
	}
	
	public String getTtime() {
		return this.ttime;
	}
	public void setTmodifyUserId(String value) {
		this.tmodifyUserId = value;
	}
	
	public String getTmodifyUserId() {
		return this.tmodifyUserId;
	}
	public void setTmodifyTime(String value) {
		this.tmodifyTime = value;
	}
	
	public String getTmodifyTime() {
		return this.tmodifyTime;
	}
}

