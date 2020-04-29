/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 成本项目变更表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostItemDictNo implements Serializable {

	
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
	 * 成本项目ID
	 */
	private Long cost_item_id;
	/**
	 * 成本项目变更id
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
	 * 变更人
	 */
	private String user_code;
	/**
	 * 变更时间
	 */
	private Date create_date;
	/**
	 * 变更原因
	 */
	private String note;
	/**
	 * 上级编码
	 */
	private String supp_item_code;
	
	/**
	 * 上级编码
	 */
	private String supp_item_name;
	/**
	 * 成本类型ID
	 */
	private Long cost_type_id;
	
	
	private String cost_type_code;
	
	private String cost_type_name;
	/**
	 * 成本习性ID
	 */
	private Long nature_id;
	
	private String nature_name;
	/**
	 * 业务数据来源
	 */
	private String busi_data_source;
	
	private String Busi_data_source_name;
	
	/**
	 * /**
	 * 层次
	 */
	private Long item_grade;
	/**
	 */
	/**
	 * 0启用，1停用
	 */
	private Integer is_stop;
	
	private Integer is_last;
	
	private String para_type_code;
	
	private String para_type_name;
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
	public Long getGroup_id() {
		return group_id;
	}
	public Long getHos_id() {
		return hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public Long getCost_item_id() {
		return cost_item_id;
	}
	public Long getCost_item_no() {
		return cost_item_no;
	}
	public String getCost_item_code() {
		return cost_item_code;
	}
	public String getCost_item_name() {
		return cost_item_name;
	}
	public String getUser_code() {
		return user_code;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public String getNote() {
		return note;
	}
	public String getSupp_item_code() {
		return supp_item_code;
	}
	public String getSupp_item_name() {
		return supp_item_name;
	}
	public Long getCost_type_id() {
		return cost_type_id;
	}
	public String getCost_type_code() {
		return cost_type_code;
	}
	public String getCost_type_name() {
		return cost_type_name;
	}
	public Long getNature_id() {
		return nature_id;
	}
	public String getNature_name() {
		return nature_name;
	}
	public String getBusi_data_source() {
		return busi_data_source;
	}
	public String getBusi_data_source_name() {
		return Busi_data_source_name;
	}
	public Long getItem_grade() {
		return item_grade;
	}
	public Integer getIs_stop() {
		return is_stop;
	}
	public Integer getIs_last() {
		return is_last;
	}
	public String getPara_type_code() {
		return para_type_code;
	}
	public String getPara_type_name() {
		return para_type_name;
	}
	public String getSpell_code() {
		return spell_code;
	}
	public String getWbx_code() {
		return wbx_code;
	}
	public String getError_type() {
		return error_type;
	}
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public void setCost_item_id(Long cost_item_id) {
		this.cost_item_id = cost_item_id;
	}
	public void setCost_item_no(Long cost_item_no) {
		this.cost_item_no = cost_item_no;
	}
	public void setCost_item_code(String cost_item_code) {
		this.cost_item_code = cost_item_code;
	}
	public void setCost_item_name(String cost_item_name) {
		this.cost_item_name = cost_item_name;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void setSupp_item_code(String supp_item_code) {
		this.supp_item_code = supp_item_code;
	}
	public void setSupp_item_name(String supp_item_name) {
		this.supp_item_name = supp_item_name;
	}
	public void setCost_type_id(Long cost_type_id) {
		this.cost_type_id = cost_type_id;
	}
	public void setCost_type_code(String cost_type_code) {
		this.cost_type_code = cost_type_code;
	}
	public void setCost_type_name(String cost_type_name) {
		this.cost_type_name = cost_type_name;
	}
	public void setNature_id(Long nature_id) {
		this.nature_id = nature_id;
	}
	public void setNature_name(String nature_name) {
		this.nature_name = nature_name;
	}
	public void setBusi_data_source(String busi_data_source) { 
		this.busi_data_source = busi_data_source;
	}
	public void setBusi_data_source_name(String busi_data_source_name) {
		Busi_data_source_name = busi_data_source_name;
	}
	public void setItem_grade(Long item_grade) {
		this.item_grade = item_grade;
	}
	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}
	public void setIs_last(Integer is_last) {
		this.is_last = is_last;
	}
	public void setPara_type_code(String para_type_code) {
		this.para_type_code = para_type_code;
	}
	public void setPara_type_name(String para_type_name) {
		this.para_type_name = para_type_name;
	}
	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}
	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
	
	
}