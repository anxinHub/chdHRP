/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.htc.entity.info.basic;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 成本项目变更表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class HtcCostItemDictNo implements Serializable {

	
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
	 * 成本类型ID
	 */
	private Long cost_type_id;
	/**
	 * 成本习性ID
	 */
	private Long nature_id;
	/**
	 * 业务数据来源
	 */
	private String busi_data_source;
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
	 * 设置 成本项目变更id
	 */
	public void setCost_item_no(Long value) {
		this.cost_item_no = value;
	}
	/**
	 * 获取 成本项目变更id
	 */	
	public Long getCost_item_no() {
		return this.cost_item_no;
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
	 * 设置 变更人
	 */
	public void setUser_code(String value) {
		this.user_code = value;
	}
	/**
	 * 获取 变更人
	 */	
	public String getUser_code() {
		return this.user_code;
	}
	/**
	 * 设置 变更时间
	 */
	public void setCreate_date(Date value) {
		this.create_date = value;
	}
	/**
	 * 获取 变更时间
	 */	
	public Date getCreate_date() {
		return this.create_date;
	}
	/**
	 * 设置 变更原因
	 */
	public void setNote(String value) {
		this.note = value;
	}
	/**
	 * 获取 变更原因
	 */	
	public String getNote() {
		return this.note;
	}
	/**
	 * 设置 0启用，1停用
	 */
	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}
	/**
	 * 获取 0启用，1停用
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
	 * @return the supp_item_code
	 */
	public String getSupp_item_code() {
		return supp_item_code;
	}
	/**
	 * @param supp_item_code the supp_item_code to set
	 */
	public void setSupp_item_code(String supp_item_code) {
		this.supp_item_code = supp_item_code;
	}
	/**
	 * @return the cost_type_id
	 */
	public Long getCost_type_id() {
		return cost_type_id;
	}
	/**
	 * @param cost_type_id the cost_type_id to set
	 */
	public void setCost_type_id(Long cost_type_id) {
		this.cost_type_id = cost_type_id;
	}
	/**
	 * @return the nature_id
	 */
	public Long getNature_id() {
		return nature_id;
	}
	/**
	 * @param nature_id the nature_id to set
	 */
	public void setNature_id(Long nature_id) {
		this.nature_id = nature_id;
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
	 * @return the item_grade
	 */
	public Long getItem_grade() {
		return item_grade;
	}
	/**
	 * @param item_grade the item_grade to set
	 */
	public void setItem_grade(Long item_grade) {
		this.item_grade = item_grade;
	}
	
	
}