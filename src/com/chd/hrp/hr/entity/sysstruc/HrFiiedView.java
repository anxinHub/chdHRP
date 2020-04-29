/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.entity.sysstruc;

import java.io.Serializable;

/**
 * 
 * @Description:
 * 
 * @Table: HR_FIIED_VIEW
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

public class HrFiiedView implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private Double group_id;

	/**
	 * 
	 */
	private Double hos_id;

	/**
	 * 
	 */
	//private String copy_code;
	
	private String col_code;

	/**
	 * 
	 */
	private String field_tab_code;

	private String cite_sql;
	
	private String query_sql;

	/**
	 * 
	 */
	private String note;

	/**
	 * 导入验证信息
	 */
	private String error_type;

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setGroup_id(Double value) {
		this.group_id = value;
	}

	/**
	 * 获取
	 * 
	 * @return Double
	 */
	public Double getGroup_id() {
		return this.group_id;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setHos_id(Double value) {
		this.hos_id = value;
	}

	/**
	 * 获取
	 * 
	 * @return Double
	 */
	public Double getHos_id() {
		return this.hos_id;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	/*public void setCopy_code(String value) {
		this.copy_code = value;
	}*/

	/**
	 * 获取
	 * 
	 * @return String
	 */
	/*public String getCopy_code() {
		return this.copy_code;
	}*/

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setField_tab_code(String value) {
		this.field_tab_code = value;
	}

	/**
	 * 获取
	 * 
	 * @return String
	 */
	public String getField_tab_code() {
		return this.field_tab_code;
	}

	public String getCite_sql() {
		return cite_sql;
	}

	public void setCite_sql(String cite_sql) {
		this.cite_sql = cite_sql;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 获取 导入验证信息
	 */
	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	public String getCol_code() {
		return col_code;
	}

	public void setCol_code(String col_code) {
		this.col_code = col_code;
	}

	public String getQuery_sql() {
		return query_sql;
	}

	public void setQuery_sql(String query_sql) {
		this.query_sql = query_sql;
	}

}