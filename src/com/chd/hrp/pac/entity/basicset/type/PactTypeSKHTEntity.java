package com.chd.hrp.pac.entity.basicset.type;

import java.io.Serializable;
import java.util.Date;

public class PactTypeSKHTEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1349165860744633181L;
	private Integer group_id;
	private Integer hos_id;
	private String copy_code;
	private String type_code;
	private String type_name;
	private String mark;
	private Integer dept_id;
	private Date start_date;
	private Integer is_stop;
	private String spell_code;
	private String wbx_code;
	private String note;
	private String pact_nature;
	private String auto_vouch_type;
	
	private String dept_name;
	private String nature_name;
	private String vouch_name;
	private String subject_type;
	

	public String getVouch_name() {
		return vouch_name;
	}

	public void setVouch_name(String vouch_name) {
		this.vouch_name = vouch_name;
	}

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

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Integer getDept_id() {
		return dept_id;
	}

	public void setDept_id(Integer dept_id) {
		this.dept_id = dept_id;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Integer getIs_stop() {
		return is_stop;
	}

	public void setIs_stop(Integer is_stop) {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setAuto_vouch_type(String auto_vouch_type) {
		this.auto_vouch_type = auto_vouch_type;
	}

	public String getPact_nature() {
		return pact_nature;
	}

	public void setPact_nature(String pact_nature) {
		this.pact_nature = pact_nature;
	}

	public String getAuto_vouch_type() {
		return auto_vouch_type;
	}

	public void setVouch_type_fkht(String auto_vouch_type) {
		this.auto_vouch_type = auto_vouch_type;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getNature_name() {
		return nature_name;
	}

	public void setNature_name(String nature_name) {
		this.nature_name = nature_name;
	}

	public String getSubject_type() {
		return subject_type;
	}

	public void setSubject_type(String subject_type) {
		this.subject_type = subject_type;
	}
	
}
