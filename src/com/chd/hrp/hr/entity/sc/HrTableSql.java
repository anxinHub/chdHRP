package com.chd.hrp.hr.entity.sc;

import java.io.Serializable;

public class HrTableSql implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5367804551822677591L;
	
	private String tab_code;
	private String sql_code;
	private String sql_name;
	private String sql_statement;
	private Integer is_custom;
	private Integer is_proc_jfunc;//1:存储过程, 2:java方法
	
	
	public String getTab_code() {
		return tab_code;
	}
	public void setTab_code(String tab_code) {
		this.tab_code = tab_code;
	}
	public String getSql_code() {
		return sql_code;
	}
	public void setSql_code(String sql_code) {
		this.sql_code = sql_code;
	}
	public String getSql_name() {
		return sql_name;
	}
	public void setSql_name(String sql_name) {
		this.sql_name = sql_name;
	}
	public String getSql_statement() {
		return sql_statement;
	}
	public void setSql_statement(String sql_statement) {
		this.sql_statement = sql_statement;
	}
	public Integer getIs_custom() {
		return is_custom;
	}
	public void setIs_custom(Integer is_custom) {
		this.is_custom = is_custom;
	}
	public Integer getIs_proc_jfunc() {
		return is_proc_jfunc;
	}
	public void setIs_proc_jfunc(Integer is_proc_jfunc) {
		this.is_proc_jfunc = is_proc_jfunc;
	}
	
	
}
