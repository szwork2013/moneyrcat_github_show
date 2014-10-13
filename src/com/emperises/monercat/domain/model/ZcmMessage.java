/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package com.emperises.monercat.domain.model;

import java.util.ArrayList;
import java.util.List;


public class ZcmMessage extends BaseObject{
	
    // mid 	
	private String mid;
    // 消息标题 	
	private String mtitle;
    // 消息内容 	
	private String mcontent;
    // 喵用户ID 	
	private String muserId;
    // 创建时间 	
	private String mcreateTime;
	private List<ZcmMessage> rows;

	public void setMid(String value) {
		this.mid = value;
	}
	
	public String getMid() {
		return this.mid;
	}
	public void setMtitle(String value) {
		this.mtitle = value;
	}
	
	public String getMtitle() {
		return this.mtitle;
	}
	public void setMcontent(String value) {
		this.mcontent = value;
	}
	
	public String getMcontent() {
		return this.mcontent;
	}
	public void setMuserId(String value) {
		this.muserId = value;
	}
	
	public String getMuserId() {
		return this.muserId;
	}
	public void setMcreateTime(String value) {
		this.mcreateTime = value;
	}
	
	public String getMcreateTime() {
		return this.mcreateTime;
	}

	public List<ZcmMessage> getRows() {
		if(rows == null){
			rows = new ArrayList<ZcmMessage>();
		}
		return rows;
	}

	public void setRows(List<ZcmMessage> rows) {
		this.rows = rows;
	}
}

