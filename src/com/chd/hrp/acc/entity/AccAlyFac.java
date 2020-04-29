package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.Date;

public class AccAlyFac implements Serializable {



	private String group_id;
	private Integer fac_code;
	private Date create_date;

	public String getGroup_id() {
		return group_id;
	}

	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}

	public Integer getFac_code() {
		return fac_code;
	}

	public void setFac_code(Integer fac_code) {
		this.fac_code = fac_code;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}


}
