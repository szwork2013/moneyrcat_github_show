/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.emperises.monercat.domain.model;


public class ZcmLogs extends BaseObject{
	
    // lid 	
	private String lid;
    // IP地址 	
	private String lip;
    // 操作人 	
	private String luser;
    // 操作时间 	
	private String ltime;
    // lres 	
	private String lres;
    // 日志描述 	
	private String ldesc;

	public void setLid(String value) {
		this.lid = value;
	}
	
	public String getLid() {
		return this.lid;
	}
	public void setLip(String value) {
		this.lip = value;
	}
	
	public String getLip() {
		return this.lip;
	}
	public void setLuser(String value) {
		this.luser = value;
	}
	
	public String getLuser() {
		return this.luser;
	}
	public void setLtime(String value) {
		this.ltime = value;
	}
	
	public String getLtime() {
		return this.ltime;
	}
	public void setLres(String value) {
		this.lres = value;
	}
	
	public String getLres() {
		return this.lres;
	}
	public void setLdesc(String value) {
		this.ldesc = value;
	}
	
	public String getLdesc() {
		return this.ldesc;
	}
}

