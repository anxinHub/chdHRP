package com.chd.hrp.prm.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public class PrmEmpDuty implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;
	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String duty_code;
	private String duty_name;
	private String duty_note;
	private String spell_code;
	private String wbx_code;
	private Integer is_stop;
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
	public String getDuty_code() {
		return duty_code;
	}
	public void setDuty_code(String duty_code) {
		this.duty_code = duty_code;
	}
	public String getDuty_name() {
		return duty_name;
	}
	public void setDuty_name(String duty_name) {
		this.duty_name = duty_name;
	}
	public String getDuty_note() {
		return duty_note;
	}
	public void setDuty_note(String duty_note) {
		this.duty_note = duty_note;
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
	public Integer getIs_stop() {
		return is_stop;
	}
	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}
	public String getError_type() {
		return error_type;
	}
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	private String error_type;

	
	
}