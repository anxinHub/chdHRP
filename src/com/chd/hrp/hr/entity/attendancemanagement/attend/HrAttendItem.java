package com.chd.hrp.hr.entity.attendancemanagement.attend;

import java.io.Serializable;

/**
 * 考勤项目设置
 * 
 * @author Administrator
 *
 */
public class HrAttendItem implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;
	// 集团ID
	private Integer group_id;
	// 医院ID
	private Integer hos_id;
	
	// 项目编码
	private String attend_code;
	// 项目名称
	private String attend_name;
	// 项目简称
	private String attend_shortname;
	// 项目性质
	private String attend_types;
	private String attend_type_name;
	//是否在考勤表显示
	private Integer attend_result_is;
	private String attend_result_name;
	// 是否限额
	private Integer attend_ed_is;
	private String attend_ed_name;
	private Integer is_default;
	private String is_default_name;
	//额度取值方式
	private Integer attend_ed_type;
	private String ed_type_name;
	//计算公式
	private String attend_ed_jsgs;
	//对应关系
	private String attend_item;
	//考勤分组
	private String attend_code_fz;
	private String attend_name_fz;
	private Integer day_num;
	
	
	private Integer control_type;
	private Integer max_ed;
	private Integer is_jx;
	private Integer is_calculate;
	private String spell;
	private Integer is_stop;
	private String note;
	
	public Integer getControl_type() {
		return control_type;
	}

	public void setControl_type(Integer control_type) {
		this.control_type = control_type;
	}

	public Integer getMax_ed() {
		return max_ed;
	}

	public void setMax_ed(Integer max_ed) {
		this.max_ed = max_ed;
	}

	public Integer getIs_jx() {
		return is_jx;
	}

	public void setIs_jx(Integer is_jx) {
		this.is_jx = is_jx;
	}

	public Integer getIs_calculate() {
		return is_calculate;
	}

	public void setIs_calculate(Integer is_calculate) {
		this.is_calculate = is_calculate;
	}

	public String getSpell() {
		return spell;
	}

	public void setSpell(String spell) {
		this.spell = spell;
	}

	public Integer getIs_stop() {
		return is_stop;
	}

	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	// 错误信息
	private String error_type;

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

	public String getAttend_code() {
		return attend_code;
	}

	public void setAttend_code(String attend_code) {
		this.attend_code = attend_code;
	}

	public String getAttend_name() {
		return attend_name;
	}

	public void setAttend_name(String attend_name) {
		this.attend_name = attend_name;
	}

	public String getAttend_shortname() {
		return attend_shortname;
	}

	public void setAttend_shortname(String attend_shortname) {
		this.attend_shortname = attend_shortname;
	}

	
	public String getAttend_types() {
		return attend_types;
	}

	public void setAttend_types(String attend_types) {
		this.attend_types = attend_types;
	}


	

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}


	public String getAttend_type_name() {
		return attend_type_name;
	}

	public void setAttend_type_name(String attend_type_name) {
		this.attend_type_name = attend_type_name;
	}

	public Integer getAttend_ed_is() {
		return attend_ed_is;
	}

	public void setAttend_ed_is(Integer attend_ed_is) {
		this.attend_ed_is = attend_ed_is;
	}

	public String getAttend_ed_name() {
		return attend_ed_name;
	}

	public void setAttend_ed_name(String attend_ed_name) {
		this.attend_ed_name = attend_ed_name;
	}

	public Integer getAttend_ed_type() {
		return attend_ed_type;
	}

	public void setAttend_ed_type(Integer attend_ed_type) {
		this.attend_ed_type = attend_ed_type;
	}

	public String getEd_type_name() {
		return ed_type_name;
	}

	public void setEd_type_name(String ed_type_name) {
		this.ed_type_name = ed_type_name;
	}

	public String getAttend_ed_jsgs() {
		return attend_ed_jsgs;
	}

	public void setAttend_ed_jsgs(String attend_ed_jsgs) {
		this.attend_ed_jsgs = attend_ed_jsgs;
	}

	public Integer getAttend_result_is() {
		return attend_result_is;
	}

	public void setAttend_result_is(Integer attend_result_is) {
		this.attend_result_is = attend_result_is;
	}

	public String getAttend_result_name() {
		return attend_result_name;
	}

	public void setAttend_result_name(String attend_result_name) {
		this.attend_result_name = attend_result_name;
	}

	public String getAttend_item() {
		return attend_item;
	}

	public void setAttend_item(String attend_item) {
		this.attend_item = attend_item;
	}

	public String getAttend_code_fz() {
		return attend_code_fz;
	}

	public void setAttend_code_fz(String attend_code_fz) {
		this.attend_code_fz = attend_code_fz;
	}

	public String getAttend_name_fz() {
		return attend_name_fz;
	}

	public void setAttend_name_fz(String attend_name_fz) {
		this.attend_name_fz = attend_name_fz;
	}

	public Integer getDay_num() {
		return day_num;
	}

	public void setDay_num(Integer day_num) {
		this.day_num = day_num;
	}

	public Integer getIs_default() {
		return is_default;
	}

	public void setIs_default(Integer is_default) {
		this.is_default = is_default;
	}

	public String getIs_default_name() {
		return is_default_name;
	}

	public void setIs_default_name(String is_default_name) {
		this.is_default_name = is_default_name;
	}

	

	
}
