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

public class AphiCostitemPercSeq implements Serializable {

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

	private String cost_item_code;

	private Integer is_acc;

	private double cost_percent;

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

	public void setCost_item_code(String value) {
		this.cost_item_code = value;
	}

	public String getCost_item_code() {
		return this.cost_item_code;
	}

	public void setIs_acc(Integer value) {
		this.is_acc = value;
	}

	public Integer getIs_acc() {
		return this.is_acc;
	}

	public void setCost_percent(double value) {
		this.cost_percent = value;
	}

	public double getCost_percent() {
		return this.cost_percent;
	}
}