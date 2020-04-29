package com.chd.hrp.htc.entity.task.basic;

import java.io.Serializable;
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

public class HtcResCauseDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private long group_id;
	private long hos_id;
	private String copy_code;
	private String res_cause_code;
	private String res_cause_name;
	private String res_remark;
	private String spell_code;
	private String wbx_code;
	private double fun_value;
	public long getGroup_id() {
		return group_id;
	}
	public long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getRes_cause_code() {
		return res_cause_code;
	}
	public String getRes_cause_name() {
		return res_cause_name;
	}
	public String getRes_remark() {
		return res_remark;
	}
	public String getSpell_code() {
		return spell_code;
	}
	public String getWbx_code() {
		return wbx_code;
	}
	public double getFun_value() {
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
	public void setRes_cause_code(String res_cause_code) {
		this.res_cause_code = res_cause_code;
	}
	public void setRes_cause_name(String res_cause_name) {
		this.res_cause_name = res_cause_name;
	}
	public void setRes_remark(String res_remark) {
		this.res_remark = res_remark;
	}
	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}
	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
	public void setFun_value(double fun_value) {
		this.fun_value = fun_value;
	}
}