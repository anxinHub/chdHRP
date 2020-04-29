package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.Date;

public class AccWageScheme implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long scheme_id;
	
	private Long group_id;
	private Long hos_id;
	
	private String copy_code;
	private String wage_code;
	
	private String scheme_code;
	private String scheme_name;
	private String scheme_type_code;
	
	private Long create_user;
	
	private Date create_date;
	
	private Integer is_gzt;// 是否工资条

	public Long getScheme_id() {
		return scheme_id;
	}

	public void setScheme_id(Long scheme_id) {
		this.scheme_id = scheme_id;
	}

	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public Long getHos_id() {
		return hos_id;
	}

	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public String getWage_code() {
		return wage_code;
	}

	public void setWage_code(String wage_code) {
		this.wage_code = wage_code;
	}

	public String getScheme_name() {
		return scheme_name;
	}

	public void setScheme_name(String scheme_name) {
		this.scheme_name = scheme_name;
	}

	public Long getCreate_user() {
		return create_user;
	}

	public void setCreate_user(Long create_user) {
		this.create_user = create_user;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public String getScheme_code() {
		return scheme_code;
	}

	public void setScheme_code(String scheme_code) {
		this.scheme_code = scheme_code;
	}

	public String getScheme_type_code() {
		return scheme_type_code;
	}

	public void setScheme_type_code(String scheme_type_code) {
		this.scheme_type_code = scheme_type_code;
	}

	public Integer getIs_gzt() {
		return is_gzt;
	}

	public void setIs_gzt(Integer is_gzt) {
		this.is_gzt = is_gzt;
	}
	
}
