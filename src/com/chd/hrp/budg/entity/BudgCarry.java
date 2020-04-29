/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 资金预算结账
 * @Table:
 * BUDG_CARRY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgCarry implements Serializable {

	
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
	
	/**
	 * 执行表 预算年度
	 */
	private String year;
	
	/**
	 * 月份
	 */
	private String month;
	
	/**
	 * 结转标识
	 */
	private Double cash_flag;
	
	/**
	 * 结转人
	 */
	private String cash_user;
	
	/**
	 * 结转日期
	 */
	private Date cash_date;
	

   /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	 * 业务预算结账标记
	 */
	private int work_flag;
	
	/**
	 * 业务预算结账人
	 */
	private String work_user;
	
	/**
	 * 业务预算结账日期
	 */
	private Date work_date;
	
	/**
	 * 收入预算结账标记
	 */
	private int income_flag;
	
	/**
	 * 收入预算结账人
	 */
	private String income_user;
	
	/**
	 * 收入预算结账日期
	 */
	private Date income_date;
	
	
	/**
	 * 支出预算结账标记
	 */
	private int cost_flag;
	
	/**
	 * 支出预算结账人
	 */
	private String cost_user;
	
	/**
	 * 支出预算结账日期
	 */
	private Date cost_date;
	
	/**
	 * 采购预算结账标记
	 */
	private int pur_flag;
	
	/**
	 * 采购预算结账人
	 */
	private String pur_user;
	
	/**
	 * 采购预算结账日期
	 */
	private Date pur_date;
	
	/**
	 * 获取 集团ID
	 */
	public Long getGroup_id() {
		return group_id;
	}
	
	/**
	 * 设置 集团ID
	 */
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	
	/**
	 * 获取 医院ID
	 */
	public Long getHos_id() {
		return hos_id;
	}
	
	/**
	 * 设置 集团ID
	 */
	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}
	
	/**
	 * 获取 账套编码
	 */
	public String getCopy_code() {
		return copy_code;
	}
	
	/**
	 * 设置 账套编码
	 */
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	
	/**
	 * 获取 年度
	 */
	public String getYear() {
		return year;
	}
	
	/**
	 * 设置 年度
	 */
	public void setYear(String year) {
		this.year = year;
	}
	
	/**
	 * 获取 月份
	 */
	public String getMonth() {
		return month;
	}
	
	/**
	 * 设置 月份
	 */
	public void setMonth(String month) {
		this.month = month;
	}
	
	public Double getCash_flag() {
		return cash_flag;
	}
	
	
	public void setCash_flag(Double cash_flag) {
		this.cash_flag = cash_flag;
	}
	
	
	public String getCash_user() {
		return cash_user;
	}
	
	
	public void setCash_user(String cash_user) {
		this.cash_user = cash_user;
	}
	
	
	public Date getCash_date() {
		return cash_date;
	}
	
	
	public void setCash_date(Date cash_date) {
		this.cash_date = cash_date;
	}
	
	
	public String getError_type() {
		return error_type;
	}
	
	
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
	/**
	 * 获取 业务预算结账标记
	 */
	public int getWork_flag() {
		return work_flag;
	}
	
	/**
	 * 设置 业务预算结账标记
	 */
	public void setWork_flag(int work_flag) {
		this.work_flag = work_flag;
	}
	
	/**
	 * 获取 业务预算结账人
	 */
	public String getWork_user() {
		return work_user;
	}
	
	/**
	 * 设置 业务预算结账人
	 */
	public void setWork_user(String work_user) {
		this.work_user = work_user;
	}
	
	/**
	 * 获取 业务预算结账时间
	 */
	public Date getWork_date() {
		return work_date;
	}
	
	/**
	 * 设置 业务预算结账时间
	 */
	public void setWork_date(Date work_date) {
		this.work_date = work_date;
	}

	/**
	 * 获取 收入预算结账标记
	 */
	public int getIncome_flag() {
		return income_flag;
	}

	/**
	 * 设置 收入预算结账标记
	 */
	public void setIncome_flag(int income_flag) {
		this.income_flag = income_flag;
	}

	/**
	 * 获取 收入预算结账人
	 */
	public String getIncome_user() {
		return income_user;
	}

	/**
	 * 设置 收入预算结账人
	 */
	public void setIncome_user(String income_user) {
		this.income_user = income_user;
	}

	/**
	 * 获取 收入预算结账时间
	 */
	public Date getIncome_date() {
		return income_date;
	}

	/**
	 * 设置 收入预算结账时间
	 */
	public void setIncome_date(Date income_date) {
		this.income_date = income_date;
	}

	/**
	 * 获取 支出预算结账标记
	 */
	public int getCost_flag() {
		return cost_flag;
	}

	/**
	 * 设置 支出预算结账标记
	 */
	public void setCost_flag(int cost_flag) {
		this.cost_flag = cost_flag;
	}

	/**
	 * 获取 支出预算结账人
	 */
	public String getCost_user() {
		return cost_user;
	}

	/**
	 * 设置 支出预算结账人
	 */
	public void setCost_user(String cost_user) {
		this.cost_user = cost_user;
	}

	/**
	 * 获取 支出预算结账时间
	 */
	public Date getCost_date() {
		return cost_date;
	}

	/**
	 * 设置 支出预算结账时间
	 */
	public void setCost_date(Date cost_date) {
		this.cost_date = cost_date;
	}

	
	/**
	 * 获取 采购预算结账标记
	 */
	public int getPur_flag() {
		return pur_flag;
	}
	
	/**
	 * 设置 采购预算结账标记
	 */
	public void setPur_flag(int pur_flag) {
		this.pur_flag = pur_flag;
	}
	
	/**
	 * 获取 采购预算结账人
	 */
	public String getPur_user() {
		return pur_user;
	}
	
	/**
	 * 设置 采购预算结账人
	 */
	public void setPur_user(String pur_user) {
		this.pur_user = pur_user;
	}
	
	/**
	 * 获取 采购预算结账日期
	 */
	public Date getPur_date() {
		return pur_date;
	}
	
	/**
	 * 设置 采购预算结账日期
	 */
	public void setPur_date(Date pur_date) {
		this.pur_date = pur_date;
	}
	
}