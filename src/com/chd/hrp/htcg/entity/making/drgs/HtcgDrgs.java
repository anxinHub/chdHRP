/*
 *
 */package com.chd.hrp.htcg.entity.making.drgs;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public class HtcgDrgs implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String drgs_code;
	private String drgs_name;
	private String drgs_type_code;
	private String drgs_type_name;
	private String spell_code;
	private String wbx_code;
	private String drgs_note;
	public Long getGroup_id() {
		return group_id;
	}
	public Long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getDrgs_code() {
		return drgs_code;
	}
	public String getDrgs_name() {
		return drgs_name;
	}
	public String getDrgs_type_code() {
		return drgs_type_code;
	}
	public String getDrgs_type_name() {
		return drgs_type_name;
	}
	public String getSpell_code() {
		return spell_code;
	}
	public String getWbx_code() {
		return wbx_code;
	}
	public String getDrgs_note() {
		return drgs_note;
	}
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public void setDrgs_code(String drgs_code) {
		this.drgs_code = drgs_code;
	}
	public void setDrgs_name(String drgs_name) {
		this.drgs_name = drgs_name;
	}
	public void setDrgs_type_code(String drgs_type_code) {
		this.drgs_type_code = drgs_type_code;
	}
	public void setDrgs_type_name(String drgs_type_name) {
		this.drgs_type_name = drgs_type_name;
	}
	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}
	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
	public void setDrgs_note(String drgs_note) {
		this.drgs_note = drgs_note;
	}
}