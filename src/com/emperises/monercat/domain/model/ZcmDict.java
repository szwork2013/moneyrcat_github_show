/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.emperises.monercat.domain.model;


public class ZcmDict extends BaseObject{
	
    // did 	
	private String did;
    // 编码 	
	private String dkey;
    // 值 	
	private String dvalue;
    // 名称 	
	private String dname;
    // 状态 0-禁用 1-启用 	
	private String dstatus;
    // 描述 	
	private String ddesc;

	public void setDid(String value) {
		this.did = value;
	}
	
	public String getDid() {
		return this.did;
	}
	public void setDkey(String value) {
		this.dkey = value;
	}
	
	public String getDkey() {
		return this.dkey;
	}
	public void setDvalue(String value) {
		this.dvalue = value;
	}
	
	public String getDvalue() {
		return this.dvalue;
	}
	public void setDname(String value) {
		this.dname = value;
	}
	
	public String getDname() {
		return this.dname;
	}
	public void setDstatus(String value) {
		this.dstatus = value;
	}
	
	public String getDstatus() {
		return this.dstatus;
	}
	public void setDdesc(String value) {
		this.ddesc = value;
	}
	
	public String getDdesc() {
		return this.ddesc;
	}
}

