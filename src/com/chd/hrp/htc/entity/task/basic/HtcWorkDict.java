package com.chd.hrp.htc.entity.task.basic;

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

public class HtcWorkDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private long group_id;
	private long hos_id;
	private String copy_code;
	private String work_code;
	private String work_name;
	private String work_type_code;
	private String work_type_name;
	private String work_remark;
	private String spell_code;
	private String wbx_code;
	private Integer is_people;
	private Integer is_material;
	private Integer is_asset;
	public long getGroup_id() {
		return group_id;
	}
	public long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getWork_code() {
		return work_code;
	}
	public String getWork_name() {
		return work_name;
	}
	public String getWork_type_code() {
		return work_type_code;
	}
	public String getWork_type_name() {
		return work_type_name;
	}
	public String getWork_remark() {
		return work_remark;
	}
	public String getSpell_code() {
		return spell_code;
	}
	public String getWbx_code() {
		return wbx_code;
	}
	public Integer getIs_people() {
		return is_people;
	}
	public Integer getIs_material() {
		return is_material;
	}
	public Integer getIs_asset() {
		return is_asset;
	}
	public void setGroup_id(long group_id) {
		this.group_id = group_id;
	}
	public void setHos_id(long hos_id) {
		this.hos_id = hos_id;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public void setWork_code(String work_code) {
		this.work_code = work_code;
	}
	public void setWork_name(String work_name) {
		this.work_name = work_name;
	}
	public void setWork_type_code(String work_type_code) {
		this.work_type_code = work_type_code;
	}
	public void setWork_type_name(String work_type_name) {
		this.work_type_name = work_type_name;
	}
	public void setWork_remark(String work_remark) {
		this.work_remark = work_remark;
	}
	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}
	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
	public void setIs_people(Integer is_people) {
		this.is_people = is_people;
	}
	public void setIs_material(Integer is_material) {
		this.is_material = is_material;
	}
	public void setIs_asset(Integer is_asset) {
		this.is_asset = is_asset;
	}
}