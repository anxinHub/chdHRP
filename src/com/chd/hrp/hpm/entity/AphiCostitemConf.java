package com.chd.hrp.hpm.entity;

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

public class AphiCostitemConf implements Serializable {

	
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
	private Long dept_no;
	private Long dept_id;
	private String cost_item_code;
	private Integer is_acc;
	private Integer is_prim_cost;
	private Integer is_calc_cost;
	private String cost_iitem_name;
	private String dept_name;
	private String error_type;
	
	public String getCost_iitem_name() {
		return cost_iitem_name;
	}

	public void setCost_iitem_name(String cost_iitem_name) {
		this.cost_iitem_name = cost_iitem_name;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public void setCopy_code(String value) {
		this.copy_code = value;
	}
		
	public String getCopy_code() {
		return this.copy_code;
	}
	
	
	public Long getDept_no() {
		return dept_no;
	}

	public void setDept_no(Long dept_no) {
		this.dept_no = dept_no;
	}

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
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
	public void setIs_prim_cost(Integer value) {
		this.is_prim_cost = value;
	}
		
	public Integer getIs_prim_cost() {
		return this.is_prim_cost;
	}
	public void setIs_calc_cost(Integer value) {
		this.is_calc_cost = value;
	}
		
	public Integer getIs_calc_cost() {
		return this.is_calc_cost;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
	
}