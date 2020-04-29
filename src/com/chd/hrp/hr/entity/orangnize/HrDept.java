package com.chd.hrp.hr.entity.orangnize;

import java.io.Serializable;

/**
 * 
 * @author Administrator 部门
 */
public class HrDept implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private Long dept_id;
	private String dept_code;
	private String kind_code;
	private String type_code;
	private String type_name;
	private String natur_code;
	private String natur_name;
	private String kind_name;
	private String dept_name;

	public int getIs_disable() {
		return is_disable;
	}

	public void setIs_disable(int is_disable) {
		this.is_disable = is_disable;
	}

	private String super_code;
	private Long super_id;
	private String super_name;
	private String udefine_code;
	private Long sort_code;
	private int is_stop;
	private int is_disable;
	private int is_last;
	private String spell_code;
	private String wbx_code;
	private String note;
	private int dept_level;

	private String error_type;

	public Long getSuper_id() {
		return super_id;
	}

	public void setSuper_id(Long super_id) {
		this.super_id = super_id;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	public String getKind_name() {
		return kind_name;
	}

	public void setKind_name(String kind_name) {
		this.kind_name = kind_name;
	}

	public String getSuper_name() {
		return super_name;
	}

	public void setSuper_name(String super_name) {
		this.super_name = super_name;
	}

	public void setGroup_id(Long value) {
		this.group_id = value;
	}

	public Long getGroup_id() {
		return this.group_id;
	}

	public void setHos_id(Long value) {
		this.hos_id = value;
	}

	public Long getHos_id() {
		return this.hos_id;
	}

	public void setDept_id(Long value) {
		this.dept_id = value;
	}

	public Long getDept_id() {
		return this.dept_id;
	}

	public void setDept_code(String value) {
		this.dept_code = value;
	}

	public String getDept_code() {
		return this.dept_code;
	}

	public void setKind_code(String value) {
		this.kind_code = value;
	}

	public String getKind_code() {
		return this.kind_code;
	}

	public void setDept_name(String value) {
		this.dept_name = value;
	}

	public String getDept_name() {
		return this.dept_name;
	}

	public void setSuper_code(String value) {
		this.super_code = value;
	}

	public String getSuper_code() {
		return this.super_code;
	}

	public void setUdefine_code(String value) {
		this.udefine_code = value;
	}

	public String getUdefine_code() {
		return this.udefine_code;
	}

	public void setSort_code(Long value) {
		this.sort_code = value;
	}

	public Long getSort_code() {
		return this.sort_code;
	}

	public void setIs_stop(int value) {
		this.is_stop = value;
	}

	public int getIs_stop() {
		return this.is_stop;
	}

	public void setIs_last(int value) {
		this.is_last = value;
	}

	public int getIs_last() {
		return this.is_last;
	}

	public String getSpell_code() {
		return spell_code;
	}

	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}

	public void setWbx_code(String value) {
		this.wbx_code = value;
	}

	public String getWbx_code() {
		return this.wbx_code;
	}

	public void setNote(String value) {
		this.note = value;
	}

	public String getNote() {
		return this.note;
	}

	public void setDept_level(int value) {
		this.dept_level = value;
	}

	public int getDept_level() {
		return this.dept_level;
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

	public String getNatur_code() {
		return natur_code;
	}

	public void setNatur_code(String natur_code) {
		this.natur_code = natur_code;
	}

	public String getNatur_name() {
		return natur_name;
	}

	public void setNatur_name(String natur_name) {
		this.natur_name = natur_name;
	}

}
