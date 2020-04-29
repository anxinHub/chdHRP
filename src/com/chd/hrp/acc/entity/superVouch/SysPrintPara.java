package com.chd.hrp.acc.entity.superVouch;

import java.io.Serializable;

public class SysPrintPara implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8007148345993227218L;

	
	private String mod_code;
	private String template_code;
	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private Long use_id;
	private String para_code;
	private String para_name;
	private String para_value;
	private int flag;
	private String flag_value;
	private int sort_code;
	private String para_json;
	public String getMod_code() {
		return mod_code;
	}
	public void setMod_code(String mod_code) {
		this.mod_code = mod_code;
	}
	public String getTemplate_code() {
		return template_code;
	}
	public void setTemplate_code(String template_code) {
		this.template_code = template_code;
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
	public String getPara_code() {
		return para_code;
	}
	public void setPara_code(String para_code) {
		this.para_code = para_code;
	}
	public String getPara_name() {
		return para_name;
	}
	public void setPara_name(String para_name) {
		this.para_name = para_name;
	}
	public String getPara_value() {
		return para_value;
	}
	public void setPara_value(String para_value) {
		this.para_value = para_value;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public Long getUse_id() {
		return use_id;
	}
	public void setUse_id(Long use_id) {
		this.use_id = use_id;
	}
	public int getSort_code() {
		return sort_code;
	}
	public void setSort_code(int sort_code) {
		this.sort_code = sort_code;
	}
	public String getFlag_value() {
		return flag_value;
	}
	public void setFlag_value(String flag_value) {
		this.flag_value = flag_value;
	}
	public String getPara_json() {
		return para_json;
	}
	public void setPara_json(String para_json) {
		this.para_json = para_json;
	}
	
	
}
