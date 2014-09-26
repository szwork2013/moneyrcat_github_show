package com.emperises.monercat.domain;

import java.io.Serializable;

public class DomainObject implements Serializable{

	public DomainObject() {
	}
	private static final long serialVersionUID = 1L;
	private String code;
	private String msg;
	public String getResultCode() {
		return code;
	}
	public String getResultMsg() {
		return msg;
	}
	
}
