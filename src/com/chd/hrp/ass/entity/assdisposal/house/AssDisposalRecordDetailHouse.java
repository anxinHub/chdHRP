/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.entity.assdisposal.house;

import java.io.Serializable;

/**
 * 
 * @Description: 050701 资产处置记录明细(土地) 
 * @Table: ASS_DISPOSAL_R_DETAIL_LAND
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

public class AssDisposalRecordDetailHouse implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5109569854157995702L;

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
	 * 处置单号
	 */
	private String dis_r_no;

	/**
	 * 卡片编号
	 */
	private String ass_card_no;
	
	private Integer ass_id;
	private Integer ass_no;
	private String ass_code;
	private String ass_name;

	/**
	 * 资产原值
	 */
	private Double price;

	/**
	 * 累计折旧
	 */
	private Double add_depre;

	/**
	 * 累计分摊
	 */
	private Double add_manage_depre;

	/**
	 * 资产净值
	 */
	private Double cur_money;

	/**
	 * 预留残值
	 */
	private Double fore_money;
	
	private Double dispose_cost;
	
	private Double dispose_income;
	
	private Double dispose_tax;

	/**
	 * 备注
	 */
	private String note;

	/**
	 * 导入验证信息
	 */
	private String error_type;
	
	private String dept_name;
	
	

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

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
	 * 设置 处置单号
	 * 
	 * @param value
	 */
	public void setDis_r_no(String value) {
		this.dis_r_no = value;
	}

	/**
	 * 获取 处置单号
	 * 
	 * @return String
	 */
	public String getDis_r_no() {
		return this.dis_r_no;
	}

	/**
	 * 设置 卡片编号
	 * 
	 * @param value
	 */
	public void setAss_card_no(String value) {
		this.ass_card_no = value;
	}

	/**
	 * 获取 卡片编号
	 * 
	 * @return String
	 */
	public String getAss_card_no() {
		return this.ass_card_no;
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
	 * 设置 累计分摊
	 * 
	 * @param value
	 */
	public void setAdd_manage_depre(Double value) {
		this.add_manage_depre = value;
	}

	/**
	 * 获取 累计分摊
	 * 
	 * @return Double
	 */
	public Double getAdd_manage_depre() {
		return this.add_manage_depre;
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

	public Integer getAss_id() {
		return ass_id;
	}

	public void setAss_id(Integer ass_id) {
		this.ass_id = ass_id;
	}

	public Integer getAss_no() {
		return ass_no;
	}

	public void setAss_no(Integer ass_no) {
		this.ass_no = ass_no;
	}

	public String getAss_code() {
		return ass_code;
	}

	public void setAss_code(String ass_code) {
		this.ass_code = ass_code;
	}

	public String getAss_name() {
		return ass_name;
	}

	public void setAss_name(String ass_name) {
		this.ass_name = ass_name;
	}

	public Double getDispose_cost() {
		return dispose_cost;
	}

	public void setDispose_cost(Double dispose_cost) {
		this.dispose_cost = dispose_cost;
	}

	public Double getDispose_income() {
		return dispose_income;
	}

	public void setDispose_income(Double dispose_income) {
		this.dispose_income = dispose_income;
	}

	public Double getDispose_tax() {
		return dispose_tax;
	}

	public void setDispose_tax(Double dispose_tax) {
		this.dispose_tax = dispose_tax;
	}
	
	
}