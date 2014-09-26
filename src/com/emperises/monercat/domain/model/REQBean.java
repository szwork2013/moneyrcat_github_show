package com.emperises.monercat.domain.model;


/**
 * 请求参数 bean
 * @author panyf
 *
 */
public class REQBean extends BaseObject {

	/**
	 * 
	 */
	
	private String action_type; //操作类型
	private String udevicesId; //设备ID
	private String utelephone; //电话号码
	private String uname; //姓名
	private String uidentity; //身份证号码
	private String smsCode;//短信校验码
	private String money; //提现金额
	private String ubankno;// 银行卡号码
	private String ubankName;// 开户行
	private String usex; //性别
	private String uaddress; //地址
	private String uage;//年龄
	
	public String getUbankno() {
		return ubankno;
	}
	public void setUbankno(String ubankno) {
		this.ubankno = ubankno;
	}
	public String getUbankName() {
		return ubankName;
	}
	public void setUbankName(String ubankName) {
		this.ubankName = ubankName;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getSmsCode() {
		return smsCode;
	}
	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
	public String getAction_type() {
		return action_type;
	}
	public void setAction_type(String action_type) {
		this.action_type = action_type;
	}
	public String getUdevicesId() {
		return udevicesId;
	}
	public void setUdevicesId(String udevicesId) {
		this.udevicesId = udevicesId;
	}
	public String getUtelephone() {
		return utelephone;
	}
	public void setUtelephone(String utelephone) {
		this.utelephone = utelephone;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getUidentity() {
		return uidentity;
	}
	public void setUidentity(String uidentity) {
		this.uidentity = uidentity;
	}
	public String getUsex() {
		return usex;
	}
	public void setUsex(String usex) {
		this.usex = usex;
	}
	public String getUaddress() {
		return uaddress;
	}
	public void setUaddress(String uaddress) {
		this.uaddress = uaddress;
	}
	public String getUage() {
		return uage;
	}
	public void setUage(String uage) {
		this.uage = uage;
	}
}
