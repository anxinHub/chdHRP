package com.chd.hrp.budg.entity;

import java.io.Serializable;
import java.util.*;

/**
 * 经费余额调整
 * @author Administrator
 *
 */
public class BudgProjRemainAdj implements Serializable{
	
	private static final long serialVersionUID = 5454155825314635342L;
   
	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String adj_code;
	private String remark;
	private Long remain_adj_sum;
	private Long maker;
	private String maker_name;
	private Date make_date;
	private Long checker;
	private String checker_name;
	private Date check_date;
	private String state;
	
	
	public String getMaker_name() {
		return maker_name;
	}
	public void setMaker_name(String maker_name) {
		this.maker_name = maker_name;
	}
	public String getChecker_name() {
		return checker_name;
	}
	public void setChecker_name(String checker_name) {
		this.checker_name = checker_name;
	}
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
	public String getCopy_code() {
		return copy_code;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public String getAdj_code() {
		return adj_code;
	}
	public void setAdj_code(String adj_code) {
		this.adj_code = adj_code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getRemain_adj_sum() {
		return remain_adj_sum;
	}
	public void setRemain_adj_sum(Long remain_adj_sum) {
		this.remain_adj_sum = remain_adj_sum;
	}
	public Long getMaker() {
		return maker;
	}
	public void setMaker(Long maker) {
		this.maker = maker;
	}
	public Date getMake_date() {
		return make_date;
	}
	public void setMake_date(Date make_date) {
		this.make_date = make_date;
	}
	public Long getChecker() {
		return checker;
	}
	public void setChecker(Long checker) {
		this.checker = checker;
	}
	public Date getCheck_date() {
		return check_date;
	}
	public void setCheck_date(Date check_date) {
		this.check_date = check_date;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
	public void setError_type(String string) {
		// TODO Auto-generated method stub
		
	}
	
}
