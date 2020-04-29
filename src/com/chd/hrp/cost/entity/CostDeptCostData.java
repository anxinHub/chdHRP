/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.entity;

import java.io.Serializable;

/**
 * @Title. @Description. 科室成本核算表<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class CostDeptCostData implements Serializable {

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
	 * 统计年月
	 */
	private String acc_year;

	/**
	 * 统计年月
	 */
	private String acc_month;

	/**
	 * 科室ID
	 */
	private Long dept_id;
	/**
	 * 科室变更ID
	 */
	private Long dept_no;

	private String dept_code;

	private String dept_name;
	/**
	 * 成本项目ID
	 */
	private Long cost_item_id;
	/**
	 * 成本项目变更ID
	 */
	private Long cost_item_no;

	private String cost_item_code;

	private String cost_item_name;
	/**
	 * 资金来源
	 */
	private String source_id;

	private String source_code;

	private String source_name;
	/**
	 * 直接成本
	 */
	private double dir_amount;
	/**
	 * 管理分摊成本
	 */
	private double man_amount;
	/**
	 * 医辅分摊成本
	 */
	private double ass_amount;
	/**
	 * 医技分摊成本
	 */
	private double med_amount;
	/**
	 * 导入验证信息
	 */
	private String error_type;

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

	public String getCost_item_code() {
		return cost_item_code;
	}

	public void setCost_item_code(String cost_item_code) {
		this.cost_item_code = cost_item_code;
	}

	public String getCost_item_name() {
		return cost_item_name;
	}

	public void setCost_item_name(String cost_item_name) {
		this.cost_item_name = cost_item_name;
	}

	/**
	 * 设置 集团ID
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}

	/**
	 * 获取 集团ID
	 */
	public Long getGroup_id() {
		return this.group_id;
	}

	/**
	 * 设置 医院ID
	 */
	public void setHos_id(Long value) {
		this.hos_id = value;
	}

	/**
	 * 获取 医院ID
	 */
	public Long getHos_id() {
		return this.hos_id;
	}

	/**
	 * 设置 账套编码
	 */
	public void setCopy_code(String value) {
		this.copy_code = value;
	}

	/**
	 * 获取 账套编码
	 */
	public String getCopy_code() {
		return this.copy_code;
	}

	/**
	 * 设置 科室ID
	 */
	public void setDept_id(Long value) {
		this.dept_id = value;
	}

	/**
	 * 获取 科室ID
	 */
	public Long getDept_id() {
		return this.dept_id;
	}

	/**
	 * 设置 科室变更ID
	 */
	public void setDept_no(Long value) {
		this.dept_no = value;
	}

	/**
	 * 获取 科室变更ID
	 */
	public Long getDept_no() {
		return this.dept_no;
	}

	/**
	 * 设置 成本项目ID
	 */
	public void setCost_item_id(Long value) {
		this.cost_item_id = value;
	}

	/**
	 * 获取 成本项目ID
	 */
	public Long getCost_item_id() {
		return this.cost_item_id;
	}

	/**
	 * 设置 成本项目变更ID
	 */
	public void setCost_item_no(Long value) {
		this.cost_item_no = value;
	}

	/**
	 * 获取 成本项目变更ID
	 */
	public Long getCost_item_no() {
		return this.cost_item_no;
	}

	/**
	 * 设置 资金来源
	 */
	public void setSource_id(String value) {
		this.source_id = value;
	}

	/**
	 * 获取 资金来源
	 */
	public String getSource_id() {
		return this.source_id;
	}

	/**
	 * 设置 直接成本
	 */
	public void setDir_amount(double value) {
		this.dir_amount = value;
	}

	/**
	 * 获取 直接成本
	 */
	public double getDir_amount() {
		return this.dir_amount;
	}

	/**
	 * 设置 管理分摊成本
	 */
	public void setMan_amount(double value) {
		this.man_amount = value;
	}

	/**
	 * 获取 管理分摊成本
	 */
	public double getMan_amount() {
		return this.man_amount;
	}

	/**
	 * 设置 医辅分摊成本
	 */
	public void setAss_amount(double value) {
		this.ass_amount = value;
	}

	/**
	 * 获取 医辅分摊成本
	 */
	public double getAss_amount() {
		return this.ass_amount;
	}

	/**
	 * 设置 医技分摊成本
	 */
	public void setMed_amount(double value) {
		this.med_amount = value;
	}

	/**
	 * 获取 医技分摊成本
	 */
	public double getMed_amount() {
		return this.med_amount;
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

	public String getAcc_year() {
		return acc_year;
	}

	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}

	public String getAcc_month() {
		return acc_month;
	}

	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
	}

}