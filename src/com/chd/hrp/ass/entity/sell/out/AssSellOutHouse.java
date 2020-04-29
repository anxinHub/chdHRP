/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.sell.out;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050901 资产有偿调拨出库单主表（房屋及建筑物）
 * @Table:
 * ASS_SELL_OUT_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssSellOutHouse implements Serializable {

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
	 * 调拨单号
	 */
	private String sell_out_no;

	/**
	 * 调入集团
	 */
	private Long in_group_id;

	private String in_group_name;

	/**
	 * 调入单位
	 */
	private Long in_hos_id;

	private String in_hos_name;

	/**
	 * 资产原值
	 */
	private Double price;

	/**
	 * 累计折旧
	 */
	private Double add_depre;

	/**
	 * 资产净值
	 */
	private Double cur_money;

	/**
	 * 预留残值
	 */
	private Double fore_money;

	/**
	 * 调拨价格
	 */
	private Double sell_price;

	/**
	 * sell_price - CUR_MONEY+FORE_MONEY
	 */
	private Double dispose_income;

	/**
	 * 应缴税费
	 */
	private Double dispose_tax;

	/**
	 * 制单人
	 */
	private Long create_emp;

	private String create_emp_name;

	/**
	 * 制单日期
	 */
	private Date create_date;

	/**
	 * 审核人
	 */
	private Long audit_emp;

	private String audit_emp_name;

	/**
	 * 审核日期
	 */
	private Date audit_date;

	/**
	 * 状态
	 */
	private Integer state;

	private String state_name;

	/**
	 * 备注
	 */
	private String note;

	/**
	 * 导入验证信息
	 */
	private String error_type;

	private String bus_type_code;

	private String bus_type_name;

	/**
	 * 设置 集团ID
	 * 
	 * @param value
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}

	/**
	 * 获取 集团ID
	 * 
	 * @return Long
	 */
	public Long getGroup_id() {
		return this.group_id;
	}

	/**
	 * 设置 医院ID
	 * 
	 * @param value
	 */
	public void setHos_id(Long value) {
		this.hos_id = value;
	}

	/**
	 * 获取 医院ID
	 * 
	 * @return Long
	 */
	public Long getHos_id() {
		return this.hos_id;
	}

	/**
	 * 设置 账套编码
	 * 
	 * @param value
	 */
	public void setCopy_code(String value) {
		this.copy_code = value;
	}

	/**
	 * 获取 账套编码
	 * 
	 * @return String
	 */
	public String getCopy_code() {
		return this.copy_code;
	}

	/**
	 * 设置 调拨单号
	 * 
	 * @param value
	 */
	public void setSell_out_no(String value) {
		this.sell_out_no = value;
	}

	/**
	 * 获取 调拨单号
	 * 
	 * @return String
	 */
	public String getSell_out_no() {
		return this.sell_out_no;
	}

	/**
	 * 设置 调入集团
	 * 
	 * @param value
	 */
	public void setIn_group_id(Long value) {
		this.in_group_id = value;
	}

	/**
	 * 获取 调入集团
	 * 
	 * @return Long
	 */
	public Long getIn_group_id() {
		return this.in_group_id;
	}

	/**
	 * 设置 调入单位
	 * 
	 * @param value
	 */
	public void setIn_hos_id(Long value) {
		this.in_hos_id = value;
	}

	/**
	 * 获取 调入单位
	 * 
	 * @return Long
	 */
	public Long getIn_hos_id() {
		return this.in_hos_id;
	}

	/**
	 * 设置 资产原值
	 * 
	 * @param value
	 */
	public void setPrice(Double value) {
		this.price = value;
	}

	/**
	 * 获取 资产原值
	 * 
	 * @return Double
	 */
	public Double getPrice() {
		return this.price;
	}

	/**
	 * 设置 累计折旧
	 * 
	 * @param value
	 */
	public void setAdd_depre(Double value) {
		this.add_depre = value;
	}

	/**
	 * 获取 累计折旧
	 * 
	 * @return Double
	 */
	public Double getAdd_depre() {
		return this.add_depre;
	}

	/**
	 * 设置 资产净值
	 * 
	 * @param value
	 */
	public void setCur_money(Double value) {
		this.cur_money = value;
	}

	/**
	 * 获取 资产净值
	 * 
	 * @return Double
	 */
	public Double getCur_money() {
		return this.cur_money;
	}

	/**
	 * 设置 预留残值
	 * 
	 * @param value
	 */
	public void setFore_money(Double value) {
		this.fore_money = value;
	}

	/**
	 * 获取 预留残值
	 * 
	 * @return Double
	 */
	public Double getFore_money() {
		return this.fore_money;
	}

	/**
	 * 设置 调拨价格
	 * 
	 * @param value
	 */
	public void setSell_price(Double value) {
		this.sell_price = value;
	}

	/**
	 * 获取 调拨价格
	 * 
	 * @return Double
	 */
	public Double getSell_price() {
		return this.sell_price;
	}

	/**
	 * 设置 sell_price - CUR_MONEY+FORE_MONEY
	 * 
	 * @param value
	 */
	public void setDispose_income(Double value) {
		this.dispose_income = value;
	}

	/**
	 * 获取 sell_price - CUR_MONEY+FORE_MONEY
	 * 
	 * @return Double
	 */
	public Double getDispose_income() {
		return this.dispose_income;
	}

	/**
	 * 设置 应缴税费
	 * 
	 * @param value
	 */
	public void setDispose_tax(Double value) {
		this.dispose_tax = value;
	}

	/**
	 * 获取 应缴税费
	 * 
	 * @return Double
	 */
	public Double getDispose_tax() {
		return this.dispose_tax;
	}

	/**
	 * 设置 制单人
	 * 
	 * @param value
	 */
	public void setCreate_emp(Long value) {
		this.create_emp = value;
	}

	/**
	 * 获取 制单人
	 * 
	 * @return Long
	 */
	public Long getCreate_emp() {
		return this.create_emp;
	}

	/**
	 * 设置 制单日期
	 * 
	 * @param value
	 */
	public void setCreate_date(Date value) {
		this.create_date = value;
	}

	/**
	 * 获取 制单日期
	 * 
	 * @return Date
	 */
	public Date getCreate_date() {
		return this.create_date;
	}

	/**
	 * 设置 审核人
	 * 
	 * @param value
	 */
	public void setAudit_emp(Long value) {
		this.audit_emp = value;
	}

	/**
	 * 获取 审核人
	 * 
	 * @return Long
	 */
	public Long getAudit_emp() {
		return this.audit_emp;
	}

	/**
	 * 设置 审核日期
	 * 
	 * @param value
	 */
	public void setAudit_date(Date value) {
		this.audit_date = value;
	}

	/**
	 * 获取 审核日期
	 * 
	 * @return Date
	 */
	public Date getAudit_date() {
		return this.audit_date;
	}

	/**
	 * 设置 备注
	 * 
	 * @param value
	 */
	public void setNote(String value) {
		this.note = value;
	}

	/**
	 * 获取 备注
	 * 
	 * @return String
	 */
	public String getNote() {
		return this.note;
	}

	/**
	 * 设置 状态
	 * 
	 * @param value
	 */
	public void setState(Integer value) {
		this.state = value;
	}

	/**
	 * 获取 状态
	 * 
	 * @return Integer
	 */
	public Integer getState() {
		return this.state;
	}

	/**
	 * 设置 导入验证信息
	 */
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	/**
	 * 获取 导入验证信息
	 */
	public String getError_type() {
		return error_type;
	}

	public String getIn_group_name() {
		return in_group_name;
	}

	public void setIn_group_name(String in_group_name) {
		this.in_group_name = in_group_name;
	}

	public String getIn_hos_name() {
		return in_hos_name;
	}

	public void setIn_hos_name(String in_hos_name) {
		this.in_hos_name = in_hos_name;
	}

	public String getCreate_emp_name() {
		return create_emp_name;
	}

	public void setCreate_emp_name(String create_emp_name) {
		this.create_emp_name = create_emp_name;
	}

	public String getAudit_emp_name() {
		return audit_emp_name;
	}

	public void setAudit_emp_name(String audit_emp_name) {
		this.audit_emp_name = audit_emp_name;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public String getBus_type_code() {
		return bus_type_code;
	}

	public void setBus_type_code(String bus_type_code) {
		this.bus_type_code = bus_type_code;
	}

	public String getBus_type_name() {
		return bus_type_name;
	}

	public void setBus_type_name(String bus_type_name) {
		this.bus_type_name = bus_type_name;
	}

}