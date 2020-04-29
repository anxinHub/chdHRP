/** 
* 2015-2-2 
* IncomeItem.java 
* author:alfred
*/ 
package com.chd.hrp.hpm.entity; 

import java.io.Serializable;

public class AphiIncomeItem implements Serializable{

	/**
	 * 收入项目表INCOMEITEM
	 */
	private static final long serialVersionUID = 1L;
	
	private Long group_id;

	private Long hos_id;
	
	
	
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
	}//单位编码
	
	private String copy_code;//账套编码
	
	private String income_item_code;//收入项目编码
	
	private String income_item_name;//收入项目名称
	
	private String spell_code;//拼音码
	
	private String wbx_code;//五笔码
	
	private String data_source;//数据来源
	
	private Integer is_stop;//是否停用
	
	public Integer getIs_stop() {
		return is_stop;
	}

	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}

	private String error_type;

	

	

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public String getIncome_item_code() {
		return income_item_code;
	}

	public void setIncome_item_code(String income_item_code) {
		this.income_item_code = income_item_code;
	}

	public String getIncome_item_name() {
		return income_item_name;
	}

	public void setIncome_item_name(String income_item_name) {
		this.income_item_name = income_item_name;
	}

	public String getSpell_code() {
		return spell_code;
	}

	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}

	public String getWbx_code() {
		return wbx_code;
	}

	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}

	public String getData_source() {
		return data_source;
	}

	public void setData_source(String data_source) {
		this.data_source = data_source;
	}

	

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
