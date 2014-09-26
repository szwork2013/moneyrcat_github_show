package com.emperises.monercat.domain.model;

import java.util.List;

import com.emperises.monercat.domain.DomainObject;

public class AdInfoV3 extends DomainObject{

	private List<ZcmAdertising> rows;
	private String val;

	public List<ZcmAdertising> getRows() {
		return rows;
	}

	public void setRows(List<ZcmAdertising> rows) {
		this.rows = rows;
	}

	public String getValue() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}
}
