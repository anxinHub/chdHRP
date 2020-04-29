package com.chd.hrp.htc.entity.info.basic;

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

public class HtcIncomeItemDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集团ID
	 */
	private Long group_id;
	/**
	 * 医院ID
	 */
	private Long hos_id;
	/**
	 * 账套编码
	 */
	private String copy_code;
	
	private Long income_item_id;
	/**
	 * 收入项目编码
	 */
	private String income_item_code;
	/**
	 * 收入项目名称
	 */
	private String income_item_name;
	/**
	 * 停用标志
	 */
	private Integer is_stop;
	/**
	 * 拼音码
	 */
	private String spell_code;
	/**
	 * 五笔码
	 */
	private String wbx_code;
	public Long getGroup_id() {
		return group_id;
	}
	public Long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public Long getIncome_item_id() {
		return income_item_id;
	}
	public String getIncome_item_code() {
		return income_item_code;
	}
	public String getIncome_item_name() {
		return income_item_name;
	}
	public Integer getIs_stop() {
		return is_stop;
	}
	public String getSpell_code() {
		return spell_code;
	}
	public String getWbx_code() {
		return wbx_code;
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
	public void setIncome_item_id(Long income_item_id) {
		this.income_item_id = income_item_id;
	}
	public void setIncome_item_code(String income_item_code) {
		this.income_item_code = income_item_code;
	}
	public void setIncome_item_name(String income_item_name) {
		this.income_item_name = income_item_name;
	}
	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}
	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}
	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
	
	
	
}