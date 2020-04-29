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

public class HtcCostTypeDict implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long cost_type_id;

	private String cost_type_code;

	private String cost_type_name;

	private String spell_code;

	private String wbx_code;

	public Long getCost_type_id() {
		return cost_type_id;
	}

	public String getCost_type_code() {
		return cost_type_code;
	}

	public String getCost_type_name() {
		return cost_type_name;
	}

	public String getSpell_code() {
		return spell_code;
	}

	public String getWbx_code() {
		return wbx_code;
	}

	public void setCost_type_id(Long cost_type_id) {
		this.cost_type_id = cost_type_id;
	}

	public void setCost_type_code(String cost_type_code) {
		this.cost_type_code = cost_type_code;
	}

	public void setCost_type_name(String cost_type_name) {
		this.cost_type_name = cost_type_name;
	}

	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}

	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
	
	
}