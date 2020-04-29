/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;

/**
* @Title. @Description.
* 工资项目表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccWageItems implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集团ID
	 */
	private double group_id;
	/**
	 * 医院ID
	 */
	private double hos_id;
	/**
	 * 账套编码
	 */
	private String copy_code;
	/**
	 * 会计年度
	 */
	private String acc_year;
	/**
	 * 工资项ID
	 */
	private Long item_id;
	/**
	 * 工资项编码
	 */
	private String item_code;
	/**
	 * 工资套编码
	 */
	private String wage_code;
	/**
	 * 工资套编码
	 */
	private String wage_name;
	/**
	 * 工资项名称
	 */
	private String item_name;
	/**
	 * 工资项类型
	 */
	private String item_type;
	
	private String type_name;
	/**
	 * 计算方式
	 */
	private Integer item_cal;
	/**
	 * 工资项性质
	 */
	private String item_nature;
	
	private String nature_name;
	/**
	 * 是否继承上月
	 */
	private Integer is_jc;
	/**
	 * 是否参与合计
	 */
	private Integer is_sum;
	/**
	 * 拼音码
	 */
	private String spell_code;
	/**
	 * 五笔码
	 */
	private String wbx_code;
	/**
	 * 排序号
	 */
	private String sort_code;
	/**
	 * 是否停用
	 */
	private Integer is_stop;
	/**
	 * 备注
	 */
	private String note;
	
	/**
	 * 绑定工资发放表的项目字段
	 */
	private String column_item;
	
	private String error_type;
	
	private Integer data_type;
	
	/**
	 * 设置 集团ID
	 */
	public void setGroup_id(double value) {
		this.group_id = value;
	}
	/**
	 * 获取 集团ID
	 */	
	public double getGroup_id() {
		return this.group_id;
	}
	/**
	 * 设置 医院ID
	 */
	public void setHos_id(double value) {
		this.hos_id = value;
	}
	/**
	 * 获取 医院ID
	 */	
	public double getHos_id() {
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
	 * 设置 会计年度
	 */
	public void setAcc_year(String value) {
		this.acc_year = value;
	}
	/**
	 * 获取 会计年度
	 */	
	public String getAcc_year() {
		return this.acc_year;
	}
	/**
	 * 设置 工资项编码
	 */
	public void setItem_code(String value) {
		this.item_code = value;
	}
	/**
	 * 获取 工资项编码
	 */	
	public String getItem_code() {
		return this.item_code;
	}
	/**
	 * 设置 工资项名称
	 */
	public void setItem_name(String value) {
		this.item_name = value;
	}
	/**
	 * 获取 工资项名称
	 */	
	public String getItem_name() {
		return this.item_name;
	}
	/**
	 * 设置 工资项类型
	 */
	
	/**
	 * 设置 计算方式
	 */
	public void setItem_cal(Integer value) {
		this.item_cal = value;
	}
	public String getItem_type() {
		return item_type;
	}
	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}
	/**
	 * 获取 计算方式
	 */	
	public Integer getItem_cal() {
		return this.item_cal;
	}
	
	/**
	 * 设置 是否继承上月
	 */
	public void setIs_jc(Integer value) {
		this.is_jc = value;
	}
	/**
	 * 设置 工资项性质
	 */
	public String getItem_nature() {
		return item_nature;
	}
	public void setItem_nature(String item_nature) {
		this.item_nature = item_nature;
	}
	/**
	 * 获取 是否继承上月
	 */	
	public Integer getIs_jc() {
		return this.is_jc;
	}
	/**
	 * 设置 是否参与合计
	 */
	public void setIs_sum(Integer value) {
		this.is_sum = value;
	}
	/**
	 * 获取 是否参与合计
	 */	
	public Integer getIs_sum() {
		return this.is_sum;
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
	 * 设置 是否停用
	 */
	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}
	/**
	 * 获取 是否停用
	 */	
	public Integer getIs_stop() {
		return this.is_stop;
	}
	public Long getItem_id() {
		return item_id;
	}
	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}
	public String getWage_code() {
		return wage_code;
	}
	public void setWage_code(String wage_code) {
		this.wage_code = wage_code;
	}
	public String getSort_code() {
		return sort_code;
	}
	public void setSort_code(String sort_code) {
		this.sort_code = sort_code;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getColumn_item() {
		return column_item;
	}
	public void setColumn_item(String column_item) {
		this.column_item = column_item;
	}
	public String getWage_name() {
		return wage_name;
	}
	public void setWage_name(String wage_name) {
		this.wage_name = wage_name;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public String getNature_name() {
		return nature_name;
	}
	public void setNature_name(String nature_name) {
		this.nature_name = nature_name;
	}
	public String getError_type() {
		return error_type;
	}
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	public Integer getData_type() {
		return data_type;
	}
	public void setData_type(Integer data_type) {
		this.data_type = data_type;
	}
	
}