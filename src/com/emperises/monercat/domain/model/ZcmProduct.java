/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.emperises.monercat.domain.model;

import java.util.List;


public class ZcmProduct extends BaseObject{
	
    /**
	 * 
	 */
	  // pid 	
	private String pid="";
    // 商品名称 	
	private String pname="";
    // 商品价格 	
	private String pprice = "";
    // 商品描述 	
	private String pdesc="";
    // plogo 	
	private String plogo="";
    // 商品状态  0-禁用  1-启用 	
	private String pstatus="";
    // 商品数量 	
	private String pnum = "";
    // 商品类型 	
	private String ptype="";
    // 商品提供商 	
	private String pprovider="";
    // 创建时间 	
	private String pcreateTime="";
    // 创建人 	
	private String paddUser="";
	private String pTotalNum = "";
	private List<ZcmProduct> rows;
	private String pUrl="";
	private String p_max_dh_num;
	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public String getPprovider() {
		return pprovider;
	}

	public void setPprovider(String pprovider) {
		this.pprovider = pprovider;
	}

	public void setPid(String value) {
		this.pid = value;
	}
	
	public String getPid() {
		return this.pid;
	}
	public void setPname(String value) {
		this.pname = value;
	}
	
	public String getPname() {
		return this.pname;
	}
	public void setPprice(String value) {
		this.pprice = value;
	}
	
	public String getPprice() {
		return this.pprice;
	}
	public void setPdesc(String value) {
		this.pdesc = value;
	}
	
	public String getPdesc() {
		return this.pdesc;
	}
	public void setPlogo(String value) {
		this.plogo = value;
	}
	
	public String getPlogo() {
		return this.plogo;
	}
	public void setPstatus(String value) {
		this.pstatus = value;
	}
	
	public String getPstatus() {
		return this.pstatus;
	}
	public void setPcreateTime(String value) {
		this.pcreateTime = value;
	}
	
	public String getPcreateTime() {
		return this.pcreateTime;
	}
	public void setPaddUser(String value) {
		this.paddUser = value;
	}
	
	public String getPaddUser() {
		return this.paddUser;
	}
	public void setPnum(String value) {
		this.pnum = value;
	}
	
	public String getPnum() {
		return this.pnum;
	}

	public List<ZcmProduct> getRows() {
		return rows;
	}

	public void setRows(List<ZcmProduct> rows) {
		this.rows = rows;
	}

	public String getpTotalNum() {
		return pTotalNum;
	}

	public void setpTotalNum(String pTotalNum) {
		this.pTotalNum = pTotalNum;
	}

	public String getpUrl() {
		return pUrl;
	}

	public void setpUrl(String pUrl) {
		this.pUrl = pUrl;
	}

	public String getP_max_dh_num() {
		return p_max_dh_num;
	}

	public void setP_max_dh_num(String p_max_dh_num) {
		this.p_max_dh_num = p_max_dh_num;
	}



}

