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
 * @Table:
 * HR_STORE_CONDITION
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrStoreCondition implements Serializable {

	
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
	private String store_type_code;
	
	/**
	 * 
	 */
	private Integer line_no;
	
	/**
	 * 
	 */
	private String l_bracket;
	
	/**
	 * 
	 */
	private String tab_code;
	private String tab_name;
	
	/**
	 * 
	 */
	private String col_code;
	private String col_name;
	
	/**
	 * 
	 */
	private String con_sign_code;
	private String con_sign_note;
	
	/**
	 * 
	 */
	private String col_value;
	
	private String field_col_code;
	
	private String field_col_name;
	
	/**
	 * 
	 */
	private String r_bracket;
	
	/**
	 * 
	 */
	private String join_sign_code;
	
	private String join_sign_note;
	

	private String field_col_name_type;
	
	private Integer row;
	
	/**
	 * 导入验证信息
	 */
	private String error_type;


	public Double getGroup_id() {
		return group_id;
	}


	public void setGroup_id(Double group_id) {
		this.group_id = group_id;
	}


	public Double getHos_id() {
		return hos_id;
	}


	public void setHos_id(Double hos_id) {
		this.hos_id = hos_id;
	}


	public String getStore_type_code() {
		return store_type_code;
	}


	public void setStore_type_code(String store_type_code) {
		this.store_type_code = store_type_code;
	}


	public Integer getLine_no() {
		return line_no;
	}


	public void setLine_no(Integer line_no) {
		this.line_no = line_no;
	}


	public String getL_bracket() {
		return l_bracket;
	}


	public void setL_bracket(String l_bracket) {
		this.l_bracket = l_bracket;
	}


	public String getTab_code() {
		return tab_code;
	}


	public void setTab_code(String tab_code) {
		this.tab_code = tab_code;
	}


	public String getTab_name() {
		return tab_name;
	}


	public void setTab_name(String tab_name) {
		this.tab_name = tab_name;
	}


	public String getCol_code() {
		return col_code;
	}


	public void setCol_code(String col_code) {
		this.col_code = col_code;
	}


	public String getCol_name() {
		return col_name;
	}


	public void setCol_name(String col_name) {
		this.col_name = col_name;
	}


	public String getCon_sign_code() {
		return con_sign_code;
	}


	public void setCon_sign_code(String con_sign_code) {
		this.con_sign_code = con_sign_code;
	}


	public String getCon_sign_note() {
		return con_sign_note;
	}


	public void setCon_sign_note(String con_sign_note) {
		this.con_sign_note = con_sign_note;
	}


	public String getCol_value() {
		return col_value;
	}


	public void setCol_value(String col_value) {
		this.col_value = col_value;
	}


	public String getField_col_code() {
		return field_col_code;
	}


	public void setField_col_code(String field_col_code) {
		this.field_col_code = field_col_code;
	}


	public String getField_col_name() {
		return field_col_name;
	}


	public void setField_col_name(String field_col_name) {
		this.field_col_name = field_col_name;
	}


	public String getR_bracket() {
		return r_bracket;
	}


	public void setR_bracket(String r_bracket) {
		this.r_bracket = r_bracket;
	}


	public String getJoin_sign_code() {
		return join_sign_code;
	}


	public void setJoin_sign_code(String join_sign_code) {
		this.join_sign_code = join_sign_code;
	}


	public String getJoin_sign_note() {
		return join_sign_note;
	}


	public void setJoin_sign_note(String join_sign_note) {
		this.join_sign_note = join_sign_note;
	}


	public String getField_col_name_type() {
		return field_col_name_type;
	}


	public void setField_col_name_type(String field_col_name_type) {
		this.field_col_name_type = field_col_name_type;
	}


	public String getError_type() {
		return error_type;
	}


	public void setError_type(String error_type) {
		this.error_type = error_type;
	}


	public Integer getRow() {
		return row;
	}


	public void setRow(Integer row) {
		this.row = row;
	}
	


	
}