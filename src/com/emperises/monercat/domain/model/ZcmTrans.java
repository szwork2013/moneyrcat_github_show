/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.emperises.monercat.domain.model;

import java.util.List;

public class ZcmTrans extends BaseObject{
	
	private List<ZcmTrans> rows ;
    // trId 	
	private String trId;
    // 用户ID 	
	private String trUserId;
    // 交易来源ID [任务ID、广告ID等] 	
	private String trSourceId;
    // 交易类型  0-签到 1-点击广告 2-朋友圈分享 3-兑换商品 4-提现 5-转账 6-存入 7-任务 	
	private String trType;
    // 交易子类型  提现4.1 银行卡 2-支付宝 	
	private String trChildType;
    // 交易金额 	
	private String trBalance;
    // 交易状态 0-交易处理中 1-交易成功 2-交易失败 	
	private String trStatus;
    // 交易开始时间 	
	private String trBeginTime;
    // 交易结束时间 	
	private String trEndTime;
    // trRemark 	
	private String trRemark;
    // 变更人 	
	private String trModifyUserId;
    // 变更时间 	
	private String trModifyTime;
	
	public void setTrId(String value) {
		this.trId = value;
	}
	
	public String getTrId() {
		return this.trId;
	}
	public void setTrUserId(String value) {
		this.trUserId = value;
	}
	
	public String getTrUserId() {
		return this.trUserId;
	}
	public void setTrSourceId(String value) {
		this.trSourceId = value;
	}
	
	public String getTrSourceId() {
		return this.trSourceId;
	}
	public void setTrType(String value) {
		this.trType = value;
	}
	
	public String getTrType() {
		return this.trType;
	}
	public void setTrChildType(String value) {
		this.trChildType = value;
	}
	
	public String getTrChildType() {
		return this.trChildType;
	}
	public void setTrBalance(String value) {
		this.trBalance = value;
	}
	
	public String getTrBalance() {
		return this.trBalance;
	}
	public void setTrStatus(String value) {
		this.trStatus = value;
	}
	
	public String getTrStatus() {
		return this.trStatus;
	}
	public void setTrBeginTime(String value) {
		this.trBeginTime = value;
	}
	
	public String getTrBeginTime() {
		return this.trBeginTime;
	}
	public void setTrEndTime(String value) {
		this.trEndTime = value;
	}
	
	public String getTrEndTime() {
		return this.trEndTime;
	}
	public void setTrRemark(String value) {
		this.trRemark = value;
	}
	
	public String getTrRemark() {
		return this.trRemark;
	}
	public void setTrModifyUserId(String value) {
		this.trModifyUserId = value;
	}
	
	public String getTrModifyUserId() {
		return this.trModifyUserId;
	}
	public void setTrModifyTime(String value) {
		this.trModifyTime = value;
	}
	
	public String getTrModifyTime() {
		return this.trModifyTime;
	}

	public List<ZcmTrans> getRows() {
		return rows;
	}

	public void setRows(List<ZcmTrans> rows) {
		this.rows = rows;
	}
}

