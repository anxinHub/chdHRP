package com.chd.hrp.ass.entity.guanLiReports;

import java.io.Serializable;
import java.util.Date;

public class AssDepreciationUseTime implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7989328482619033916L;

	/**
	 * 集体ID
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
	private String ass_name;
	
	/**
	 * 资金来源
	 */
	private Long source_id;
	
	/**
	 * 使用科室ID
	 */
	private Long use_dept_id;
	
	/**
	 * 使用科室NO
	 */
	private Long use_dept_no;
	
	/**
	 * 分摊比例
	 */
	private Double use_percent;
	
	/**
	 * 财务本期折旧
	 */
	private Double now_depre_amount;
	
	/**
	 * 财务累计折旧月份
	 */
	private Integer add_depre_month;
	
	/**
	 * 财务累计折旧金额
	 */
	private Double add_depre_amount;
	
	/**
	 * 财务净残值
	 */
	private Double remain_price;
	
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
	 * 仓库ID
	 */
	private Long store_id;
	
	/**
	 * 仓库NO
	 */
	private Long store_no;
	
	/**
	 * 原值
	 */
	private Double prim_money;
	
	/**
	 * 是否生成凭证
	 */
	private Integer is_rise_vou;
	
	/**
	 * 生成凭证时间
	 */
	private Date rise_vou_time;
	
	/**
	 * 是否计提成本
	 */
	private Integer is_take_cost;
	
	/**
	 * 计提成本时间
	 */
	private Date take_cost_time;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 集体ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集体ID
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
	* 设置 使用科室NO
	* @param value 
	*/
	public void setUse_dept_no(Long value) {
		this.use_dept_no = value;
	}
	
	/**
	* 获取 使用科室NO
	* @return Long
	*/
	public Long getUse_dept_no() {
		return this.use_dept_no;
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
	* 设置 财务本期折旧
	* @param value 
	*/
	public void setNow_depre_amount(Double value) {
		this.now_depre_amount = value;
	}
	
	/**
	* 获取 财务本期折旧
	* @return Double
	*/
	public Double getNow_depre_amount() {
		return this.now_depre_amount;
	}
	/**
	* 设置 财务累计折旧月份
	* @param value 
	*/
	public void setAdd_depre_month(Integer value) {
		this.add_depre_month = value;
	}
	
	/**
	* 获取 财务累计折旧月份
	* @return Integer
	*/
	public Integer getAdd_depre_month() {
		return this.add_depre_month;
	}
	/**
	* 设置 财务累计折旧金额
	* @param value 
	*/
	public void setAdd_depre_amount(Double value) {
		this.add_depre_amount = value;
	}
	
	/**
	* 获取 财务累计折旧金额
	* @return Double
	*/
	public Double getAdd_depre_amount() {
		return this.add_depre_amount;
	}
	/**
	* 设置 财务净残值
	* @param value 
	*/
	public void setRemain_price(Double value) {
		this.remain_price = value;
	}
	
	/**
	* 获取 财务净残值
	* @return Double
	*/
	public Double getRemain_price() {
		return this.remain_price;
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
	* 设置 仓库ID
	* @param value 
	*/
	public void setStore_id(Long value) {
		this.store_id = value;
	}
	
	/**
	* 获取 仓库ID
	* @return Long
	*/
	public Long getStore_id() {
		return this.store_id;
	}
	/**
	* 设置 仓库NO
	* @param value 
	*/
	public void setStore_no(Long value) {
		this.store_no = value;
	}
	
	/**
	* 获取 仓库NO
	* @return Long
	*/
	public Long getStore_no() {
		return this.store_no;
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
	* 设置 是否生成凭证
	* @param value 
	*/
	public void setIs_rise_vou(Integer value) {
		this.is_rise_vou = value;
	}
	
	/**
	* 获取 是否生成凭证
	* @return Integer
	*/
	public Integer getIs_rise_vou() {
		return this.is_rise_vou;
	}
	/**
	* 设置 生成凭证时间
	* @param value 
	*/
	public void setRise_vou_time(Date value) {
		this.rise_vou_time = value;
	}
	
	/**
	* 获取 生成凭证时间
	* @return Date
	*/
	public Date getRise_vou_time() {
		return this.rise_vou_time;
	}
	/**
	* 设置 是否计提成本
	* @param value 
	*/
	public void setIs_take_cost(Integer value) {
		this.is_take_cost = value;
	}
	
	/**
	* 获取 是否计提成本
	* @return Integer
	*/
	public Integer getIs_take_cost() {
		return this.is_take_cost;
	}
	/**
	* 设置 计提成本时间
	* @param value 
	*/
	public void setTake_cost_time(Date value) {
		this.take_cost_time = value;
	}
	
	/**
	* 获取 计提成本时间
	* @return Date
	*/
	public Date getTake_cost_time() {
		return this.take_cost_time;
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

	/**
	 * @return the ass_name
	 */
	public String getAss_name() {
		return ass_name;
	}

	/**
	 * @param ass_name the ass_name to set
	 */
	public void setAss_name(String ass_name) {
		this.ass_name = ass_name;
	}
	
}
