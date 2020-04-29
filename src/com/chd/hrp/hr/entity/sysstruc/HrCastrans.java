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
 * @Table: HR_CASTRANS
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

public class HrCastrans implements Serializable {

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

	/**
	 * 
	 */
	private String main_tab_code;
	
	private String main_tab_name;

	/**
	 * 
	 */
	private String main_col_code;
	
	private String main_col_name;

	/**
	 * 
	 */
	private String affi_tab_code;
	
	private String affi_tab_name;

	/**
	 * 
	 */
	private String affi_col_code;
	
	private String affi_col_name;

	/**
	 * MF：主到附 FM：附到主 RT：双向实时
	 */
	private String cas_nature;
	
	/**
	 * 1：自动 0：手动
	 */
	private Integer up_auto;
	
	/**
	 * 
	 */
	private Integer cas_level;

	/**
	 * 1：是 0：否
	 */
	private Integer is_sql;

	/**
	 * 
	 */
	private Object cas_sql;

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
	public void setMain_tab_code(String value) {
		this.main_tab_code = value;
	}

	/**
	 * 获取
	 * 
	 * @return String
	 */
	public String getMain_tab_code() {
		return this.main_tab_code;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setMain_col_code(String value) {
		this.main_col_code = value;
	}

	/**
	 * 获取
	 * 
	 * @return String
	 */
	public String getMain_col_code() {
		return this.main_col_code;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setAffi_tab_code(String value) {
		this.affi_tab_code = value;
	}

	/**
	 * 获取
	 * 
	 * @return String
	 */
	public String getAffi_tab_code() {
		return this.affi_tab_code;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setAffi_col_code(String value) {
		this.affi_col_code = value;
	}

	/**
	 * 获取
	 * 
	 * @return String
	 */
	public String getAffi_col_code() {
		return this.affi_col_code;
	}

	/**
	 * 设置 MF：主到附 FM：附到主 RT：双向实时
	 * 
	 * @param value
	 */
	public void setCas_nature(String value) {
		this.cas_nature = value;
	}

	/**
	 * 获取 MF：主到附 FM：附到主 RT：双向实时
	 * 
	 * @return String
	 */
	public String getCas_nature() {
		return this.cas_nature;
	}

	/**
	 * 设置 1：自动 0：手动
	 * 
	 * @param value
	 */
	public void setUp_auto(Integer value) {
		this.up_auto = value;
	}

	/**
	 * 获取 1：自动 0：手动
	 * 
	 * @return Integer
	 */
	public Integer getUp_auto() {
		return this.up_auto;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setCas_level(Integer value) {
		this.cas_level = value;
	}

	/**
	 * 获取
	 * 
	 * @return Integer
	 */
	public Integer getCas_level() {
		return this.cas_level;
	}

	/**
	 * 设置 1：是 0：否
	 * 
	 * @param value
	 */
	public void setIs_sql(Integer value) {
		this.is_sql = value;
	}

	/**
	 * 获取 1：是 0：否
	 * 
	 * @return Integer
	 */
	public Integer getIs_sql() {
		return this.is_sql;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setCas_sql(Object value) {
		this.cas_sql = value;
	}

	/**
	 * 获取
	 * 
	 * @return java.sql.Blob
	 */
	public Object getCas_sql() {
		return this.cas_sql;
	}

	/**
	 * 设置
	 * 
	 * @param value
	 */
	public void setNote(String value) {
		this.note = value;
	}

	/**
	 * 获取
	 * 
	 * @return String
	 */
	public String getNote() {
		return this.note;
	}

	/**
	 * 设置 导入验证信息
	 */
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	/**
	 * 获取 导入验证信息
	 */
	public String getError_type() {
		return error_type;
	}

	public String getMain_tab_name() {
		return main_tab_name;
	}

	public void setMain_tab_name(String main_tab_name) {
		this.main_tab_name = main_tab_name;
	}

	public String getMain_col_name() {
		return main_col_name;
	}

	public void setMain_col_name(String main_col_name) {
		this.main_col_name = main_col_name;
	}

	public String getAffi_tab_name() {
		return affi_tab_name;
	}

	public void setAffi_tab_name(String affi_tab_name) {
		this.affi_tab_name = affi_tab_name;
	}

	public String getAffi_col_name() {
		return affi_col_name;
	}

	public void setAffi_col_name(String affi_col_name) {
		this.affi_col_name = affi_col_name;
	}
	
	
}