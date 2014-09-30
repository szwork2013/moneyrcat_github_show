/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.emperises.monercat.domain.model;

import java.math.BigDecimal;

import android.text.TextUtils;

public class ZcmUser extends BaseObject{
	
    // uid 	
	private String uid="";
    // 手机号码 	
	private String utelephone="";
    // 用户名称 	
	private String uname="";
    // 终端设备Id 	
	private String udevicesId="";
    // 用户类型  0-终端用户 1-web用户 	
	private String utype="";
    // 随机密码 	
	private String urandomPass="";
    // ulevel 	
	private String ulevel="";
    // Ios终端标识 	
	private String utoken="";
    // 用户状态 	
	private String ustatus="";
    // 推广码(自己) 	
	private String utgm="";
    // 推广码(推荐) 	
	private String urecTgm="";
    // uaddress 	
	private String uaddress="";
    // 年龄 	
	private String uage = "";
    // 性别 	
	private String usex="";
    // 创建时间 	
	private String ucreateTime="";
    // 最后修改时间 	
	private String umodifyTime="";
    // 身份证号码 	
	private String uidentity="";
	
	
	private String balance; //账户余额
	

	public void setUid(String value) {
		this.uid = value;
	}
	
	public String getUid() {
		return this.uid;
	}
	public void setUtelephone(String value) {
		this.utelephone = value;
	}
	
	public String getUtelephone() {
		if(TextUtils.isEmpty(this.utelephone)){
			return "";
		}
		return this.utelephone;
	}
	public void setUname(String value) {
		this.uname = value;
	}
	
	public String getUname() {
		if(TextUtils.isEmpty(this.uname)){
			return "";
		}
		return this.uname;
	}
	public void setUdevicesId(String value) {
		this.udevicesId = value;
	}
	
	public String getUdevicesId() {
		return this.udevicesId;
	}
	public void setUtype(String value) {
		this.utype = value;
	}
	
	public String getUtype() {
		return this.utype;
	}
	public void setUrandomPass(String value) {
		this.urandomPass = value;
	}
	
	public String getUrandomPass() {
		return this.urandomPass;
	}
	public void setUlevel(String value) {
		this.ulevel = value;
	}
	
	public String getUlevel() {
		return this.ulevel;
	}
	public void setUtoken(String value) {
		this.utoken = value;
	}
	
	public String getUtoken() {
		return this.utoken;
	}
	public void setUstatus(String value) {
		this.ustatus = value;
	}
	
	public String getUstatus() {
		return this.ustatus;
	}
	public void setUtgm(String value) {
		this.utgm = value;
	}
	
	public String getUtgm() {
		return this.utgm;
	}
	public void setUrecTgm(String value) {
		this.urecTgm = value;
	}
	
	public String getUrecTgm() {
		return this.urecTgm;
	}
	public void setUaddress(String value) {
		this.uaddress = value;
	}
	
	public String getUaddress() {
		if(TextUtils.isEmpty(this.uaddress)){
			return "";
		}
		return this.uaddress;
	}
	public void setUage(String value) {
		this.uage = value;
	}
	
	public String getUage() {
		if(TextUtils.isEmpty(this.uage) || "0".equals(this.uage)){
			return "";
		}
		return this.uage+"岁";
	}
	public void setUsex(String value) {
		this.usex = value;
	}
	
	public String getUsex() {
		if(TextUtils.isEmpty(this.usex)){
			return "";
		}
		return this.usex;
	}
	public void setUcreateTime(String value) {
		this.ucreateTime = value;
	}
	
	public String getUcreateTime() {
		return this.ucreateTime;
	}
	public void setUmodifyTime(String value) {
		this.umodifyTime = value;
	}
	
	public String getUmodifyTime() {
		return this.umodifyTime;
	}
	public void setUidentity(String value) {
		this.uidentity = value;
	}
	
	public String getUidentity() {
		return this.uidentity;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
}

