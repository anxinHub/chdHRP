/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.check.inassets;

import java.io.Serializable;
/**
 * 
 * @Description:
 * 051101 科室盘盈登记明细_无形资产
 * @Table:
 * ASS_CHECK_DP_DETAIL_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssCheckDpDetailInassets implements Serializable {

	
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
	 * 任务单号
	 */
	private String check_plan_no;
	
	/**
	 * 盘点单号
	 */
	private String check_no;
	
	/**
	 * 科室编码ID
	 */
	private Long dept_id;
	
	/**
	 * 科室编码NO
	 */
	private Long dept_no;
	
	private String dept_code;
	
	private String dept_name;
	
	/**
	 * 资产ID
	 */
	private String ass_id;
	
	/**
	 * 资产变更ID
	 */
	private Long ass_no;
	
	private String ass_code;
	
	private String ass_name;
	
	/**
	 * 卡片编码
	 */
	private String ass_card_no;
	
	/**
	 * 数量
	 */
	private Integer acc_amount;
	
	/**
	 * 资产原值
	 */
	private Double price;
	
	/**
	 * 累计折旧
	 */
	private Double add_depre;
	
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
	 * 盘盈原因
	 */
	private String p_reason;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	
	
	public String getDept_code() {
		return dept_code;
	}
	
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	
	public String getDept_name() {
		return dept_name;
	}
	
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
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
	* 设置 任务单号
	* @param value 
	*/
	public void setCheck_plan_no(String value) {
		this.check_plan_no = value;
	}
	
	/**
	* 获取 任务单号
	* @return String
	*/
	public String getCheck_plan_no() {
		return this.check_plan_no;
	}
	/**
	* 设置 盘点单号
	* @param value 
	*/
	public void setCheck_no(String value) {
		this.check_no = value;
	}
	
	/**
	* 获取 盘点单号
	* @return String
	*/
	public String getCheck_no() {
		return this.check_no;
	}
	/**
	* 设置 科室编码ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 科室编码ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 科室编码NO
	* @param value 
	*/
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	
	/**
	* 获取 科室编码NO
	* @return Long
	*/
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	* 设置 资产ID
	* @param value 
	*/
	public void setAss_id(String value) {
		this.ass_id = value;
	}
	
	/**
	* 获取 资产ID
	* @return Long
	*/
	public String getAss_id() {
		return this.ass_id;
	}
	/**
	* 设置 资产变更ID
	* @param value 
	*/
	public void setAss_no(Long value) {
		this.ass_no = value;
	}
	
	/**
	* 获取 资产变更ID
	* @return Long
	*/
	public Long getAss_no() {
		return this.ass_no;
	}
	/**
	* 设置 卡片编码
	* @param value 
	*/
	public void setAss_card_no(String value) {
		this.ass_card_no = value;
	}
	
	/**
	* 获取 卡片编码
	* @return String
	*/
	public String getAss_card_no() {
		return this.ass_card_no;
	}
	/**
	* 设置 数量
	* @param value 
	*/
	public void setAcc_amount(Integer value) {
		this.acc_amount = value;
	}
	
	/**
	* 获取 数量
	* @return Integer
	*/
	public Integer getAcc_amount() {
		return this.acc_amount;
	}
	/**
	* 设置 资产原值
	* @param value 
	*/
	public void setPrice(Double value) {
		this.price = value;
	}
	
	/**
	* 获取 资产原值
	* @return Double
	*/
	public Double getPrice() {
		return this.price;
	}
	/**
	* 设置 累计折旧
	* @param value 
	*/
	public void setAdd_depre(Double value) {
		this.add_depre = value;
	}
	
	/**
	* 获取 累计折旧
	* @return Double
	*/
	public Double getAdd_depre() {
		return this.add_depre;
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
	* 设置 盘盈原因
	* @param value 
	*/
	public void setP_reason(String value) {
		this.p_reason = value;
	}
	
	/**
	* 获取 盘盈原因
	* @return String
	*/
	public String getP_reason() {
		return this.p_reason;
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