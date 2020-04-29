/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.entity;

import java.io.Serializable;

/**
 * @Title. @Description. 科室直接成本表<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class CostDeptDriData implements Serializable {

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
	private String year_month;

	/**
	 * 统计年
	 */
	private String acc_year;

	/**
	 * 统计月
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

	/**
	 * 科室编码
	 */
	private String dept_code;

	/**
	 * 科室名称
	 */
	private String dept_name;

	/**
	 * 成本项目ID
	 */
	private Long cost_item_id;

	/**
	 * 成本项目变更ID
	 */
	private Long cost_item_no;

	/**
	 * 成本项目编码
	 */
	private String cost_item_code;

	/**
	 * 成本项目名称
	 */
	private String cost_item_name;

	/**
	 * 资金来源ID
	 */
	private Long source_id;

	/**
	 * 资金来源编码
	 */
	private String source_code;

	/**
	 * 资金来源名称
	 */
	private String source_name;

	/**
	 * 直接成本
	 */
	private Double dir_amount;

	/**
	 * 导入验证信息
	 */
	private String error_type;

	private Long dept_level;

	private Long item_grade;

	/**
	 * 分摊类型
	 */
	private String para_type_code;

	private String para_type_name;

	/**
	 * 分摊性质
	 */
	private String para_code;

	private String para_name;

	/**
	 * 科室类型
	 */
	private String type_code;

	private String type_name;

	/**
	 * 科室性质
	 */
	private String natur_code;

	private String natur_name;
 
	// 平均分摊标志
	private int peer_flag;

	/**
	 * 分摊参数
	 */
	private String para_value;

	private String para_value_name;

	public String getPara_value() {
		return para_value;
	}

	public void setPara_value(String para_value) {
		this.para_value = para_value;
	}

	public String getPara_value_name() {
		return para_value_name;
	}

	public void setPara_value_name(String para_value_name) {
		this.para_value_name = para_value_name;
	}

	/**
	 * 设置 集团ID
	 */
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

	public String getYear_month() {
		return year_month;
	}

	public void setYear_month(String year_month) {
		this.year_month = year_month;
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

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public Long getDept_no() {
		return dept_no;
	}

	public void setDept_no(Long dept_no) {
		this.dept_no = dept_no;
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

	public Long getCost_item_id() {
		return cost_item_id;
	}

	public void setCost_item_id(Long cost_item_id) {
		this.cost_item_id = cost_item_id;
	}

	public Long getCost_item_no() {
		return cost_item_no;
	}

	public void setCost_item_no(Long cost_item_no) {
		this.cost_item_no = cost_item_no;
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

	public Long getSource_id() {
		return source_id;
	}

	public void setSource_id(Long source_id) {
		this.source_id = source_id;
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

	public Double getDir_amount() {
		return dir_amount;
	}

	public void setDir_amount(Double dir_amount) {
		this.dir_amount = dir_amount;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	/**
	 * @return the dept_level
	 */
	public Long getDept_level() {
		return dept_level;
	}

	/**
	 * @param dept_level
	 *            the dept_level to set
	 */
	public void setDept_level(Long dept_level) {
		this.dept_level = dept_level;
	}

	/**
	 * @return the item_grade
	 */
	public Long getItem_grade() {
		return item_grade;
	}

	/**
	 * @param item_grade
	 *            the item_grade to set
	 */
	public void setItem_grade(Long item_grade) {
		this.item_grade = item_grade;
	}

	public String getPara_type_code() {
		return para_type_code;
	}

	public void setPara_type_code(String para_type_code) {
		this.para_type_code = para_type_code;
	}

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	public String getNatur_code() {
		return natur_code;
	}

	public void setNatur_code(String natur_code) {
		this.natur_code = natur_code;
	}

	public int getPeer_flag() {
		return peer_flag;
	}

	public void setPeer_flag(int peer_flag) {
		this.peer_flag = peer_flag;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getNatur_name() {
		return natur_name;
	}

	public void setNatur_name(String natur_name) {
		this.natur_name = natur_name;
	}

	public String getPara_type_name() {
		return para_type_name;
	}

	public void setPara_type_name(String para_type_name) {
		this.para_type_name = para_type_name;
	}

	public String getPara_code() {
		return para_code;
	}

	public void setPara_code(String para_code) {
		this.para_code = para_code;
	}

	public String getPara_name() {
		return para_name;
	}

	public void setPara_name(String para_name) {
		this.para_name = para_name;
	}

}