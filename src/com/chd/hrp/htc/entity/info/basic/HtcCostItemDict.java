package com.chd.hrp.htc.entity.info.basic;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public class HtcCostItemDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long cost_item_no;
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
	
	
	public Long getCost_item_no() {
		return cost_item_no;
	}
	public void setCost_item_no(Long cost_item_no) {
		this.cost_item_no = cost_item_no;
	}
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
	public Long getCost_type_id() {
		return cost_type_id;
	}
	public void setCost_type_id(Long cost_type_id) {
		this.cost_type_id = cost_type_id;
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
	public Long getCost_item_id() {
		return cost_item_id;
	}
	public void setCost_item_id(Long cost_item_id) {
		this.cost_item_id = cost_item_id;
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
	public String getSupp_item_code() {
		return supp_item_code;
	}
	public void setSupp_item_code(String supp_item_code) {
		this.supp_item_code = supp_item_code;
	}
	public String getSupp_item_name() {
		return supp_item_name;
	}
	public void setSupp_item_name(String supp_item_name) {
		this.supp_item_name = supp_item_name;
	}
	public String getNature_code() {
		return nature_code;
	}
	public void setNature_code(String nature_code) {
		this.nature_code = nature_code;
	}
	public Long getNature_id() {
		return nature_id;
	}
	public void setNature_id(Long nature_id) {
		this.nature_id = nature_id;
	}
	public String getNature_name() {
		return nature_name;
	}
	public void setNature_name(String nature_name) {
		this.nature_name = nature_name;
	}
	public String getBusi_data_source() {
		return busi_data_source;
	}
	public void setBusi_data_source(String busi_data_source) {
		this.busi_data_source = busi_data_source;
	}
	public String getBusi_data_source_name() {
		return busi_data_source_name;
	}
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
	public Integer getItem_grade() {
		return item_grade;
	}
	public void setItem_grade(Integer item_grade) {
		this.item_grade = item_grade;
	}
	public Integer getIs_last() {
		return is_last;
	}
	public void setIs_last(Integer is_last) {
		this.is_last = is_last;
	}
	public Integer getIs_stop() {
		return is_stop;
	}
	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}
	public String getSpell_code() {
		return spell_code;
	}
	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}
	public String getWbx_code() {
		return wbx_code;
	}
	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
}