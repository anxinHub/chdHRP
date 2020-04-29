/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.depre.acc;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 资产折旧_其他固定资产
 * @Table:
 * ASS_DEPRE_ACC_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssDepreAccInitOther implements Serializable {

	
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
	 * 折旧年度
	 */
	private String depre_year;
	
	/**
	 * 折旧期间
	 */
	private String depre_month;
	
	/**
	 * 资产卡片号
	 */
	private String ass_card_no;
	
	/**
	 * 资金来源
	 */
	private Long source_id;
	
	/**
	 * 使用科室ID
	 */
	private Long use_dept_id;
	
	/**
	 * 分摊比例
	 */
	private Double use_percent;
	
	/**
	 * 原值
	 */
	private Double prim_money;
	
	/**
	 * 本期折旧
	 */
	private Double now_depre_amount;
	
	/**
	 * 累计折旧
	 */
	private Double add_depre_amount;
	
	/**
	 * 累计折旧月份
	 */
	private Integer add_depre_month;
	
	/**
	 * 资产净值
	 */
	private Double cur_money;
	
	/**
	 * 预留残值
	 */
	private Double fore_money;
	
	/**
	 * 操作员
	 */
	private String operator;
	
	/**
	 * 处理日期
	 */
	private Date deal_date;
	
	/**
	 * 使用折旧方法
	 */
	private String equi_depre_code;
	
	/**
	 * 摘要
	 */
	private String note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	private String source_code;
	
	private String source_name;
	
	private Long use_dept_no;
	
	private String use_dept_code;
	
	private String use_dept_name;
	
	
	private String equi_depre_name;
	
	
	public String getEqui_depre_name() {
		return equi_depre_name;
	}

	public void setEqui_depre_name(String equi_depre_name) {
		this.equi_depre_name = equi_depre_name;
	}
	
	
	public String getSource_code() {
		return source_code;
	}

	public void setSource_code(String source_code) {
		this.source_code = source_code;
	}

	public String getSource_name() {
		return source_name;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

	public Long getUse_dept_no() {
		return use_dept_no;
	}

	public void setUse_dept_no(Long use_dept_no) {
		this.use_dept_no = use_dept_no;
	}

	public String getUse_dept_code() {
		return use_dept_code;
	}

	public void setUse_dept_code(String use_dept_code) {
		this.use_dept_code = use_dept_code;
	}

	public String getUse_dept_name() {
		return use_dept_name;
	}

	public void setUse_dept_name(String use_dept_name) {
		this.use_dept_name = use_dept_name;
	}

	/**
	* 设置 集团ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集团ID
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 医院ID
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 医院ID
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 账套编码
	* @param value 
	*/
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 账套编码
	* @return String
	*/
	public String getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 折旧年度
	* @param value 
	*/
	public void setDepre_year(String value) {
		this.depre_year = value;
	}
	
	/**
	* 获取 折旧年度
	* @return String
	*/
	public String getDepre_year() {
		return this.depre_year;
	}
	/**
	* 设置 折旧期间
	* @param value 
	*/
	public void setDepre_month(String value) {
		this.depre_month = value;
	}
	
	/**
	* 获取 折旧期间
	* @return String
	*/
	public String getDepre_month() {
		return this.depre_month;
	}
	/**
	* 设置 资产卡片号
	* @param value 
	*/
	public void setAss_card_no(String value) {
		this.ass_card_no = value;
	}
	
	/**
	* 获取 资产卡片号
	* @return String
	*/
	public String getAss_card_no() {
		return this.ass_card_no;
	}
	/**
	* 设置 资金来源
	* @param value 
	*/
	public void setSource_id(Long value) {
		this.source_id = value;
	}
	
	/**
	* 获取 资金来源
	* @return Long
	*/
	public Long getSource_id() {
		return this.source_id;
	}
	/**
	* 设置 使用科室ID
	* @param value 
	*/
	public void setUse_dept_id(Long value) {
		this.use_dept_id = value;
	}
	
	/**
	* 获取 使用科室ID
	* @return Long
	*/
	public Long getUse_dept_id() {
		return this.use_dept_id;
	}
	/**
	* 设置 分摊比例
	* @param value 
	*/
	public void setUse_percent(Double value) {
		this.use_percent = value;
	}
	
	/**
	* 获取 分摊比例
	* @return Double
	*/
	public Double getUse_percent() {
		return this.use_percent;
	}
	/**
	* 设置 原值
	* @param value 
	*/
	public void setPrim_money(Double value) {
		this.prim_money = value;
	}
	
	/**
	* 获取 原值
	* @return Double
	*/
	public Double getPrim_money() {
		return this.prim_money;
	}
	/**
	* 设置 本期折旧
	* @param value 
	*/
	public void setNow_depre_amount(Double value) {
		this.now_depre_amount = value;
	}
	
	/**
	* 获取 本期折旧
	* @return Double
	*/
	public Double getNow_depre_amount() {
		return this.now_depre_amount;
	}
	/**
	* 设置 累计折旧
	* @param value 
	*/
	public void setAdd_depre_amount(Double value) {
		this.add_depre_amount = value;
	}
	
	/**
	* 获取 累计折旧
	* @return Double
	*/
	public Double getAdd_depre_amount() {
		return this.add_depre_amount;
	}
	/**
	* 设置 累计折旧月份
	* @param value 
	*/
	public void setAdd_depre_month(Integer value) {
		this.add_depre_month = value;
	}
	
	/**
	* 获取 累计折旧月份
	* @return Integer
	*/
	public Integer getAdd_depre_month() {
		return this.add_depre_month;
	}
	/**
	* 设置 资产净值
	* @param value 
	*/
	public void setCur_money(Double value) {
		this.cur_money = value;
	}
	
	/**
	* 获取 资产净值
	* @return Double
	*/
	public Double getCur_money() {
		return this.cur_money;
	}
	/**
	* 设置 预留残值
	* @param value 
	*/
	public void setFore_money(Double value) {
		this.fore_money = value;
	}
	
	/**
	* 获取 预留残值
	* @return Double
	*/
	public Double getFore_money() {
		return this.fore_money;
	}
	/**
	* 设置 操作员
	* @param value 
	*/
	public void setOperator(String value) {
		this.operator = value;
	}
	
	/**
	* 获取 操作员
	* @return String
	*/
	public String getOperator() {
		return this.operator;
	}
	/**
	* 设置 处理日期
	* @param value 
	*/
	public void setDeal_date(Date value) {
		this.deal_date = value;
	}
	
	/**
	* 获取 处理日期
	* @return Date
	*/
	public Date getDeal_date() {
		return this.deal_date;
	}
	/**
	* 设置 使用折旧方法
	* @param value 
	*/
	public void setEqui_depre_code(String value) {
		this.equi_depre_code = value;
	}
	
	/**
	* 获取 使用折旧方法
	* @return String
	*/
	public String getEqui_depre_code() {
		return this.equi_depre_code;
	}
	/**
	* 设置 摘要
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 摘要
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
}