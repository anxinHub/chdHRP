package com.chd.hrp.htc.entity.task.basic;

import java.io.Serializable;
/**
 * 2015-3-18 author:alfred
 */

public class HtcBonusItemDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private long group_id;
	private long hos_id;
	private String copy_code;
	private String acc_year;
	private String bonus_item_code;
	private String bonus_item_name;
	private Integer sort_code;
	private String spell_code;
	private String wbx_code;
	private String is_stop;
	private String remark;
	public long getGroup_id() {
		return group_id;
	}
	public long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public String getAcc_year() {
		return acc_year;
	}
	public String getBonus_item_code() {
		return bonus_item_code;
	}
	public String getBonus_item_name() {
		return bonus_item_name;
	}
	public Integer getSort_code() {
		return sort_code;
	}
	public String getSpell_code() {
		return spell_code;
	}
	public String getWbx_code() {
		return wbx_code;
	}
	public String getIs_stop() {
		return is_stop;
	}
	public String getRemark() {
		return remark;
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
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	public void setBonus_item_code(String bonus_item_code) {
		this.bonus_item_code = bonus_item_code;
	}
	public void setBonus_item_name(String bonus_item_name) {
		this.bonus_item_name = bonus_item_name;
	}
	public void setSort_code(Integer sort_code) {
		this.sort_code = sort_code;
	}
	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}
	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
	public void setIs_stop(String is_stop) {
		this.is_stop = is_stop;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}