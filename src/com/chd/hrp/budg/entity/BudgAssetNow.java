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
 * 

 * @Table:
 * BUDG_ASSET_NOW
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgAssetNow implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 资产性质
	 */
	private String asset_nature;
	
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
	 * 预算年度
	 */
	private String budg_year;
	
	/**
	 * 月份
	 */
	private String month;
	
	/**
	 * 科室
	 */
	private Long dept_id;
	
	/**
	 * 科室编码
	 */
	private String dept_code;
	
	/**
	 * 科室名称
	 */
	private String dept_name;
	
	/**
	 * 卡片编号
	 */
	private String ass_card_no;
	
	/**
	 * 资产编码
	 */
	private String ass_code;
	
	/**
	 * 资产名称
	 */
	private String ass_name;
	
	/**
	 * 资金来源ID
	 */
	private Long source_id;
	
	/**
	 * 资金来源编码
	 */
	private String source_code;
	
	/**
	 * 资金来源编码名称
	 */
	private String source_name;
	/**
	 * 折旧计算值
	 */
	private Double depr_count;
	
	/**
	 * 折旧预算
	 */
	private Double depr_budg;
	
	/**
	 * 资产卡片号
	 */
	private String asset_card_code;
	
	/**
	 * 折旧计算值
	 */
	private Double deprec_count;
	
	/**
	 * 折旧预算
	 */
	private Double deprec_budg;
	
	/**
	 * 折旧预算_财政
	 */
	private Double financial_deprec;
	
	/**
	 * 折旧预算_科研
	 */
	private Double reserch_deprec;
	
	/**
	 * 折旧预算_教学
	 */
	private Double education_deprec;
	
	/**
	 * 折旧预算_自筹
	 */
	private Double self_collect_deprec;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 资产性质
	* @param value 
	*/
	public void setAsset_nature(String value) {
		this.asset_nature = value;
	}
	
	/**
	* 获取 资产性质
	* @return Integer
	*/
	public String getAsset_nature() {
		return this.asset_nature;
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
	* 设置 预算年度
	* @param value 
	*/
	public void setBudg_year(String value) {
		this.budg_year = value;
	}
	
	/**
	* 获取 预算年度
	* @return String
	*/
	public String getBudg_year() {
		return this.budg_year;
	}
	
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public String getAss_card_no() {
		return ass_card_no;
	}

	public void setAss_card_no(String ass_card_no) {
		this.ass_card_no = ass_card_no;
	}

	public Long getSource_id() {
		return source_id;
	}

	public void setSource_id(Long source_id) {
		this.source_id = source_id;
	}

	public Double getDepr_count() {
		return depr_count;
	}

	public void setDepr_count(Double depr_count) {
		this.depr_count = depr_count;
	}

	public Double getDepr_budg() {
		return depr_budg;
	}

	public void setDepr_budg(Double depr_budg) {
		this.depr_budg = depr_budg;
	}

	/**
	* 设置 资产卡片号
	* @param value 
	*/
	public void setAsset_card_code(String value) {
		this.asset_card_code = value;
	}
	
	/**
	* 获取 资产卡片号
	* @return String
	*/
	public String getAsset_card_code() {
		return this.asset_card_code;
	}
	/**
	* 设置 折旧计算值
	* @param value 
	*/
	public void setDeprec_count(Double value) {
		this.deprec_count = value;
	}
	
	/**
	* 获取 折旧计算值
	* @return Double
	*/
	public Double getDeprec_count() {
		return this.deprec_count;
	}
	/**
	* 设置 折旧预算
	* @param value 
	*/
	public void setDeprec_budg(Double value) {
		this.deprec_budg = value;
	}
	
	/**
	* 获取 折旧预算
	* @return Double
	*/
	public Double getDeprec_budg() {
		return this.deprec_budg;
	}
	/**
	* 设置 折旧预算_财政
	* @param value 
	*/
	public void setFinancial_deprec(Double value) {
		this.financial_deprec = value;
	}
	
	/**
	* 获取 折旧预算_财政
	* @return Double
	*/
	public Double getFinancial_deprec() {
		return this.financial_deprec;
	}
	/**
	* 设置 折旧预算_科研
	* @param value 
	*/
	public void setReserch_deprec(Double value) {
		this.reserch_deprec = value;
	}
	
	/**
	* 获取 折旧预算_科研
	* @return Double
	*/
	public Double getReserch_deprec() {
		return this.reserch_deprec;
	}
	/**
	* 设置 折旧预算_教学
	* @param value 
	*/
	public void setEducation_deprec(Double value) {
		this.education_deprec = value;
	}
	
	/**
	* 获取 折旧预算_教学
	* @return Double
	*/
	public Double getEducation_deprec() {
		return this.education_deprec;
	}
	/**
	* 设置 折旧预算_自筹
	* @param value 
	*/
	public void setSelf_collect_deprec(Double value) {
		this.self_collect_deprec = value;
	}
	
	/**
	* 获取 折旧预算_自筹
	* @return Double
	*/
	public Double getSelf_collect_deprec() {
		return this.self_collect_deprec;
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
	
	
	
	
}