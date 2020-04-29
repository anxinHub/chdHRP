package com.chd.hrp.hr.entity.salarymanagement.socialSecurityManage;
/**
 * 【薪资管理-社保管理】：社保险种
 * @author yang
 *
 */
public class HrInsurKind {

	private Integer group_id;
	private Integer hos_id;
	
	private String field_tab_code;
	
	private String insure_code;
	private String insure_name;
	
	private Integer is_innr;
	private Integer is_stop;
	private Integer is_last;
	private String is_innr_cn;
	private String is_stop_cn;
	private String is_last_cn;
	
	private String note;
	
	private String spell_code;
	private String wbx_code;
	
	public HrInsurKind(){}

	public Integer getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}

	public Integer getHos_id() {
		return hos_id;
	}

	public void setHos_id(Integer hos_id) {
		this.hos_id = hos_id;
	}

	public String getField_tab_code() {
		return field_tab_code;
	}

	public void setField_tab_code(String field_tab_code) {
		this.field_tab_code = field_tab_code;
	}

	public String getInsure_code() {
		return insure_code;
	}

	public void setInsure_code(String insure_code) {
		this.insure_code = insure_code;
	}

	public String getInsure_name() {
		return insure_name;
	}

	public void setInsure_name(String insure_name) {
		this.insure_name = insure_name;
	}

	public Integer getIs_innr() {
		return is_innr;
	}

	public void setIs_innr(Integer is_innr) {
		this.is_innr = is_innr;
	}

	public Integer getIs_stop() {
		return is_stop;
	}

	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}

	public Integer getIs_last() {
		return is_last;
	}

	public void setIs_last(Integer is_last) {
		this.is_last = is_last;
	}

	public String getIs_innr_cn() {
		return is_innr_cn;
	}

	public void setIs_innr_cn(String is_innr_cn) {
		this.is_innr_cn = is_innr_cn;
	}

	public String getIs_stop_cn() {
		return is_stop_cn;
	}

	public void setIs_stop_cn(String is_stop_cn) {
		this.is_stop_cn = is_stop_cn;
	}

	public String getIs_last_cn() {
		return is_last_cn;
	}

	public void setIs_last_cn(String is_last_cn) {
		this.is_last_cn = is_last_cn;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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
}
