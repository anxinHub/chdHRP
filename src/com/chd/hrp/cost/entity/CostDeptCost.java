/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 成本_科室成本核算总表
 * @Table:
 * COST_DEPT_COST
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class CostDeptCost implements Serializable {

	
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
	 * 统计年份
	 */
	private String acc_year;
	
	/**
	 * 统计月份
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
	 * 科室编码
	 */
	private String cost_item_code;
	/**
	 * 科室名称
	 */
	private String cost_item_name;
	
	/**
	 * 资金来源
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
	 * 分摊管理直接成本
	 */
	private Double dir_man_amount;
	
	/**
	 * 分摊医辅直接成本
	 */
	private Double dir_ass_amount;
	
	/**
	 * 分摊医技直接成本
	 */
	private Double dir_med_amount;
	
	/**
	 * 间接分摊医辅管理成本
	 */
	private Double indir_ass_man_amount;
	
	/**
	 * 间接分摊医技管理成本
	 */
	private Double indir_med_man_amount;
	
	/**
	 * 间接分摊医技医辅管理成本
	 */
	private Double indir_ass_med_man_amount;
	
	/**
	 * 间接分摊医技医辅成本
	 */
	private Double indir_med_ass_amount;
	
	private String type_code;
	private String type_name;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
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
	* 设置 统计年份
	* @param value 
	*/
	public void setAcc_year(String value) {
		this.acc_year = value;
	}
	
	/**
	* 获取 统计年份
	* @return String
	*/
	public String getAcc_year() {
		return this.acc_year;
	}
	/**
	* 设置 统计月份
	* @param value 
	*/
	public void setAcc_month(String value) {
		this.acc_month = value;
	}
	
	/**
	* 获取 统计月份
	* @return String
	*/
	public String getAcc_month() {
		return this.acc_month;
	}
	/**
	* 设置 科室ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 科室ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 科室变更ID
	* @param value 
	*/
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	
	/**
	* 获取 科室变更ID
	* @return Long
	*/
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	* 设置 成本项目ID
	* @param value 
	*/
	public void setCost_item_id(Long value) {
		this.cost_item_id = value;
	}
	
	/**
	* 获取 成本项目ID
	* @return Long
	*/
	public Long getCost_item_id() {
		return this.cost_item_id;
	}
	/**
	* 设置 成本项目变更ID
	* @param value 
	*/
	public void setCost_item_no(Long value) {
		this.cost_item_no = value;
	}
	
	/**
	* 获取 成本项目变更ID
	* @return Long
	*/
	public Long getCost_item_no() {
		return this.cost_item_no;
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
	* 设置 直接成本
	* @param value 
	*/
	public void setDir_amount(Double value) {
		this.dir_amount = value;
	}
	
	/**
	* 获取 直接成本
	* @return Double
	*/
	public Double getDir_amount() {
		return this.dir_amount;
	}
	/**
	* 设置 分摊管理直接成本
	* @param value 
	*/
	public void setDir_man_amount(Double value) {
		this.dir_man_amount = value;
	}
	
	/**
	* 获取 分摊管理直接成本
	* @return Double
	*/
	public Double getDir_man_amount() {
		return this.dir_man_amount;
	}
	/**
	* 设置 分摊医辅直接成本
	* @param value 
	*/
	public void setDir_ass_amount(Double value) {
		this.dir_ass_amount = value;
	}
	
	/**
	* 获取 分摊医辅直接成本
	* @return Double
	*/
	public Double getDir_ass_amount() {
		return this.dir_ass_amount;
	}
	/**
	* 设置 分摊医技直接成本
	* @param value 
	*/
	public void setDir_med_amount(Double value) {
		this.dir_med_amount = value;
	}
	
	/**
	* 获取 分摊医技直接成本
	* @return Double
	*/
	public Double getDir_med_amount() {
		return this.dir_med_amount;
	}
	/**
	* 设置 间接分摊医辅管理成本
	* @param value 
	*/
	public void setIndir_ass_man_amount(Double value) {
		this.indir_ass_man_amount = value;
	}
	
	/**
	* 获取 间接分摊医辅管理成本
	* @return Double
	*/
	public Double getIndir_ass_man_amount() {
		return this.indir_ass_man_amount;
	}
	/**
	* 设置 间接分摊医技管理成本
	* @param value 
	*/
	public void setIndir_med_man_amount(Double value) {
		this.indir_med_man_amount = value;
	}
	
	/**
	* 获取 间接分摊医技管理成本
	* @return Double
	*/
	public Double getIndir_med_man_amount() {
		return this.indir_med_man_amount;
	}
	/**
	* 设置 间接分摊医技医辅管理成本
	* @param value 
	*/
	public void setIndir_ass_med_man_amount(Double value) {
		this.indir_ass_med_man_amount = value;
	}
	
	/**
	* 获取 间接分摊医技医辅管理成本
	* @return Double
	*/
	public Double getIndir_ass_med_man_amount() {
		return this.indir_ass_med_man_amount;
	}
	/**
	* 设置 间接分摊医技医辅成本
	* @param value 
	*/
	public void setIndir_med_ass_amount(Double value) {
		this.indir_med_ass_amount = value;
	}
	
	/**
	* 获取 间接分摊医技医辅成本
	* @return Double
	*/
	public Double getIndir_med_ass_amount() {
		return this.indir_med_ass_amount;
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

	
    public String getType_code() {
    	return type_code;
    }

	
    public void setType_code(String type_code) {
    	this.type_code = type_code;
    }

	
    public String getType_name() {
    	return type_name;
    }

	
    public void setType_name(String type_name) {
    	this.type_name = type_name;
    }
	
	
	
	
}