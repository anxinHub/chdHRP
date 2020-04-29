/** 
* 2015-2-2 
* SysUserService.java 
* author:alfred
*/ 
package com.chd.hrp.hpm.entity; 

import java.io.Serializable;

public class AphiItem implements Serializable{

	/**
	 * 收入项目表INCOMEITEM
	 */
	private static final long serialVersionUID = 1L;
	
	private  Long group_id;
	
	private Long hos_id;
	
	private  String copy_code;
	
	private  String item_code;
	
	private  String item_name; 
	
	private  String item_note;
	
	private  String spell_code;
	
	private  String wbx_code;
	
	private  String app_mod_code;
	
	private String app_mod_name;
	
	private  Integer is_avg;
	
	private  Integer is_two_audit;
	
	private  Integer is_stop;
	
	private String acct_year;
	
	private String acct_month;
	
	private String dept_code;
	
	private Integer is_sum;
	
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

	public String getApp_mod_name() {
		return app_mod_name;
	}

	public void setApp_mod_name(String app_mod_name) {
		this.app_mod_name = app_mod_name;
	}

	
	
    public Integer getIs_avg() {
    	return is_avg;
    }

	
    public void setIs_avg(Integer is_avg) {
    	this.is_avg = is_avg;
    }

	
    public Integer getIs_stop() {
    	return is_stop;
    }

	
    public void setIs_stop(Integer is_stop) {
    	this.is_stop = is_stop;
    }

	public String getApp_mod_code() {
		return app_mod_code;
	}

	public void setApp_mod_code(String app_mod_code) {
		this.app_mod_code = app_mod_code;
	}

	public String getWbx_code() {
		return wbx_code;
	}

	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}

	public String getSpell_code() {
		return spell_code;
	}

	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}

	public String getItem_note() {
		return item_note;
	}

	public void setItem_note(String item_note) {
		this.item_note = item_note;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	

	

	
	public String getAcct_year() {
		return acct_year;
	}

	public void setAcct_year(String acct_year) {
		this.acct_year = acct_year;
	}

	public String getAcct_month() {
		return acct_month;
	}

	public void setAcct_month(String acct_month) {
		this.acct_month = acct_month;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public Integer getIs_sum() {
		return is_sum;
	}

	public void setIs_sum(Integer is_sum) {
		this.is_sum = is_sum;
	}

	public Integer getIs_two_audit() {
		return is_two_audit;
	}

	public void setIs_two_audit(Integer is_two_audit) {
		this.is_two_audit = is_two_audit;
	}
	
}
