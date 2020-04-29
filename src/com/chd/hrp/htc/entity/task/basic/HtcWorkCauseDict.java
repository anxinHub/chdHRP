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

public class HtcWorkCauseDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private long group_id;
	private long hos_id;
	private String copy_code;
	private String work_cause_code;
	private String work_cause_name;
	private String work_remark;
	private String spell_code;
	private String wbx_code;
	private Double fun_value;
	public long getGroup_id() {
		return group_id;
	}
	public long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getWork_cause_code() {
		return work_cause_code;
	}
	public String getWork_cause_name() {
		return work_cause_name;
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
	public Double getFun_value() {
		return fun_value;
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
	public void setWork_cause_code(String work_cause_code) {
		this.work_cause_code = work_cause_code;
	}
	public void setWork_cause_name(String work_cause_name) {
		this.work_cause_name = work_cause_name;
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
	public void setFun_value(Double fun_value) {
		this.fun_value = fun_value;
	}
}