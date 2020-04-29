package com.chd.hrp.hpm.entity;

import java.io.Serializable;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class AphiIncomeitemSeq implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;

	private Long hos_id;
	
	
	
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

	private String copy_code;

	private Integer scheme_seq_no;

	private String income_item_code;

	private String income_item_name;

	private String spell_code;

	private String wbx_code;

	private String data_source;

	private Integer is_stop;

	public void setCopy_code(String value) {
		this.copy_code = value;
	}

	public String getCopy_code() {
		return this.copy_code;
	}

	public void setScheme_seq_no(Integer value) {
		this.scheme_seq_no = value;
	}

	public Integer getScheme_seq_no() {
		return this.scheme_seq_no;
	}

	public void setIncome_item_code(String value) {
		this.income_item_code = value;
	}

	public String getIncome_item_code() {
		return this.income_item_code;
	}

	public void setIncome_item_name(String value) {
		this.income_item_name = value;
	}

	public String getIncome_item_name() {
		return this.income_item_name;
	}

	public void setSpell_code(String value) {
		this.spell_code = value;
	}

	public String getSpell_code() {
		return this.spell_code;
	}

	public void setWbx_code(String value) {
		this.wbx_code = value;
	}

	public String getWbx_code() {
		return this.wbx_code;
	}

	public void setData_source(String value) {
		this.data_source = value;
	}

	public String getData_source() {
		return this.data_source;
	}

	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}

	public Integer getIs_stop() {
		return this.is_stop;
	}
}