/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 成本项目字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostItemDict implements Serializable {

	
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
	 * 成本分类ID
	 */
	private Long cost_type_id;
	
	private String cost_type_code;
	
	private String cost_type_name;
	/**
	 * 成本项目ID
	 */
	private Long cost_item_id;
	/**
	 * 成本项目编码
	 */
	private String cost_item_code;
	/**
	 * 成本项目名称
	 */
	private String cost_item_name;
	/**
	 * 上级编码
	 */
	private String supp_item_code;
	
	private String supp_item_name;
	/**
	 * 成本习性编码
	 */
	private String nature_code;
	
	private Long nature_id;
	
	private String nature_name;
	/**
	 * 成本项目来源
	 */
	private String busi_data_source;
	
	private String busi_data_source_name;
	/**
	 * 成本分摊类型
	 */
	private String para_type_code;
	
	private String para_type_name;
	/**
	 * 层级
	 */
	private Integer item_grade;
	/**
	 * 末级标志
	 */
	private Integer is_last;
	/**
	 * 停用标志
	 */
	private Integer is_stop;
	/**
	 * 拼音码
	 */
	private String spell_code;
	/**
	 * 五笔码
	 */
	private String wbx_code;
	/**
	 * 导入验证信息
	 */
	private String error_type;
	
	
	public String getNature_code() {
		return nature_code;
	}
	public void setNature_code(String nature_code) {
		this.nature_code = nature_code;
	}
	public String getCost_type_code() {
		return cost_type_code;
	}
	public void setCost_type_code(String cost_type_code) {
		this.cost_type_code = cost_type_code;
	}
	public String getCost_type_name() {
		return cost_type_name;
	}
	public void setCost_type_name(String cost_type_name) {
		this.cost_type_name = cost_type_name;
	}
	public String getSupp_item_name() {
		return supp_item_name;
	}
	public void setSupp_item_name(String supp_item_name) {
		this.supp_item_name = supp_item_name;
	}
	public String getNature_name() {
		return nature_name;
	}
	public void setNature_name(String nature_name) {
		this.nature_name = nature_name;
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
	 * 设置 成本分类ID
	 */
	public void setCost_type_id(Long value) {
		this.cost_type_id = value;
	}
	/**
	 * 获取 成本分类ID
	 */	
	public Long getCost_type_id() {
		return this.cost_type_id;
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
	 * 设置 成本项目编码
	 */
	public void setCost_item_code(String value) {
		this.cost_item_code = value;
	}
	/**
	 * 获取 成本项目编码
	 */	
	public String getCost_item_code() {
		return this.cost_item_code;
	}
	/**
	 * 设置 成本项目名称
	 */
	public void setCost_item_name(String value) {
		this.cost_item_name = value;
	}
	/**
	 * 获取 成本项目名称
	 */	
	public String getCost_item_name() {
		return this.cost_item_name;
	}
	/**
	 * 设置 上级编码
	 */
	public void setSupp_item_code(String value) {
		this.supp_item_code = value;
	}
	/**
	 * 获取 上级编码
	 */	
	public String getSupp_item_code() {
		return this.supp_item_code;
	}
	/**
	 * 设置 成本习性编码
	 */
	public void setNature_id(Long value) {
		this.nature_id = value;
	}
	/**
	 * 获取 成本习性编码
	 */	
	public Long getNature_id() {
		return this.nature_id;
	}
	/**
	 * 设置 层级
	 */
	public void setItem_grade(Integer value) {
		this.item_grade = value;
	}
	/**
	 * 获取 层级
	 */	
	public Integer getItem_grade() {
		return this.item_grade;
	}
	/**
	 * 设置 末级标志
	 */
	public void setIs_last(Integer value) {
		this.is_last = value;
	}
	/**
	 * 获取 末级标志
	 */	
	public Integer getIs_last() {
		return this.is_last;
	}
	/**
	 * 设置 停用标志
	 */
	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}
	/**
	 * 获取 停用标志
	 */	
	public Integer getIs_stop() {
		return this.is_stop;
	}
	/**
	 * 设置 拼音码
	 */
	public void setSpell_code(String value) {
		this.spell_code = value;
	}
	/**
	 * 获取 拼音码
	 */	
	public String getSpell_code() {
		return this.spell_code;
	}
	/**
	 * 设置 五笔码
	 */
	public void setWbx_code(String value) {
		this.wbx_code = value;
	}
	/**
	 * 获取 五笔码
	 */	
	public String getWbx_code() {
		return this.wbx_code;
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
	 * @return the busi_data_source
	 */
	public String getBusi_data_source() {
		return busi_data_source;
	}
	/**
	 * @param busi_data_source the busi_data_source to set
	 */
	public void setBusi_data_source(String busi_data_source) {
		this.busi_data_source = busi_data_source;
	}
	/**
	 * @return the busi_data_source_name
	 */
	public String getBusi_data_source_name() {
		return busi_data_source_name;
	}
	/**
	 * @param busi_data_source_name the busi_data_source_name to set
	 */
	public void setBusi_data_source_name(String busi_data_source_name) {
		this.busi_data_source_name = busi_data_source_name;
	}
	
    public String getPara_type_code() {
    	return para_type_code;
    }
	
    public void setPara_type_code(String para_type_code) {
    	this.para_type_code = para_type_code;
    }
	
    public String getPara_type_name() {
    	return para_type_name;
    }
	
    public void setPara_type_name(String para_type_name) {
    	this.para_type_name = para_type_name;
    }
	
	
}