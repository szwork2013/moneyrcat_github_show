package com.emperises.monercat.domain.model;

import java.util.List;

import com.emperises.monercat.domain.DomainObject;

public class DuihuanHistory extends DomainObject {
//	 dhId - 主键ID
//     dhTrId - 交易ID
//     dhPname - 商品名称
//     dhTelephone - 手机号
//     dhAddress - 收货地址
//     dhUname - 收货人名称
//     dhStatus - 审核状态 【0-待审核 1-审核通过 2-审核未通过】
//     devicesId - 设备ID
//     userName - 喵用户昵称
//     tr_balance - 扣除喵币
//     dhCreateTime - 申请时间
	private String dhId;
	private String dhPname;
	private String dhTelephone;
	private String dhAddress;
	private String dhUname;
	private String dhStatus;
	private String dhCreateTime;
	private List<DuihuanHistory> rows;
	public String getDhId() {
		return dhId;
	}
	public void setDhId(String dhId) {
		this.dhId = dhId;
	}
	public String getDhPname() {
		return dhPname;
	}
	public void setDhPname(String dhPname) {
		this.dhPname = dhPname;
	}
	public String getDhTelephone() {
		return dhTelephone;
	}
	public void setDhTelephone(String dhTelephone) {
		this.dhTelephone = dhTelephone;
	}
	public String getDhAddress() {
		return dhAddress;
	}
	public void setDhAddress(String dhAddress) {
		this.dhAddress = dhAddress;
	}
	public String getDhUname() {
		return dhUname;
	}
	public void setDhUname(String dhUname) {
		this.dhUname = dhUname;
	}
	public String getDhStatus() {
		return dhStatus;
	}
	public void setDhStatus(String dhStatus) {
		this.dhStatus = dhStatus;
	}
	public String getDhCreateTime() {
		return dhCreateTime;
	}
	public void setDhCreateTime(String dhCreateTime) {
		this.dhCreateTime = dhCreateTime;
	}
	public List<DuihuanHistory> getRows() {
		return rows;
	}
	public void setRows(List<DuihuanHistory> rows) {
		this.rows = rows;
	}
	
}
