package com.chd.hrp.hr.entity.orangnize;

import java.io.Serializable;

/**
 * 能力素质指标
 * 
 * @author Administrator
 *
 */
public class HrCompetencyTarget implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;
    
	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String indicator_code;
	private String indicator_name;
	private int is_stop;
	private String spell_code;
	private String wbx_code;
	private String note;
	private String error_type;

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

	public String getIndicator_code() {
		return indicator_code;
	}

	public void setIndicator_code(String indicator_code) {
		this.indicator_code = indicator_code;
	}

	public String getIndicator_name() {
		return indicator_name;
	}

	public void setIndicator_name(String indicator_name) {
		this.indicator_name = indicator_name;
	}

	public int getIs_stop() {
		return is_stop;
	}

	public void setIs_stop(int is_stop) {
		this.is_stop = is_stop;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

}
