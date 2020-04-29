/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 成本_收费类别字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostChargeKindArrt implements Serializable {

	
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
	 * 收入项目ID_门诊
	 */
	private Long income_item_id_in;
	
	private String income_item_code_in;
	
	private String income_item_name_in;
	
	/**
	 * 收入项目ID_住院
	 */
	private Long income_item_id_out;
	
	private String income_item_code_out;
	
	private String income_item_name_out;
	/**
	 * 收费类别ID
	 */
	private Long charge_kind_id;
	/**
	 * 收费类别编码
	 */
	private String charge_kind_code;
	/**
	 * 收费类别名称
	 */
	private String charge_kind_name;
	/**
	 * 收入类型ID
	 */
	private Long income_type_id;
	
	private String income_type_code;
	
	private String income_type_name;
	
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
	
	
	public String getIncome_type_code() {
		return income_type_code;
	}
	public void setIncome_type_code(String income_type_code) {
		this.income_type_code = income_type_code;
	}
	public String getIncome_item_code_in() {
		return income_item_code_in;
	}
	public void setIncome_item_code_in(String income_item_code_in) {
		this.income_item_code_in = income_item_code_in;
	}
	public String getIncome_item_code_out() {
		return income_item_code_out;
	}
	public void setIncome_item_code_out(String income_item_code_out) {
		this.income_item_code_out = income_item_code_out;
	}
	public String getIncome_item_name_in() {
		return income_item_name_in;
	}
	public void setIncome_item_name_in(String income_item_name_in) {
		this.income_item_name_in = income_item_name_in;
	}
	public String getIncome_item_name_out() {
		return income_item_name_out;
	}
	public void setIncome_item_name_out(String income_item_name_out) {
		this.income_item_name_out = income_item_name_out;
	}
	public String getIncome_type_name() {
		return income_type_name;
	}
	public void setIncome_type_name(String income_type_name) {
		this.income_type_name = income_type_name;
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
	 * 设置 收入项目ID_门诊
	 */
	public void setIncome_item_id_in(Long value) {
		this.income_item_id_in = value;
	}
	/**
	 * 获取 收入项目ID_门诊
	 */	
	public Long getIncome_item_id_in() {
		return this.income_item_id_in;
	}
	/**
	 * 设置 收入项目ID_住院
	 */
	public void setIncome_item_id_out(Long value) {
		this.income_item_id_out = value;
	}
	/**
	 * 获取 收入项目ID_住院
	 */	
	public Long getIncome_item_id_out() {
		return this.income_item_id_out;
	}
	/**
	 * 设置 收费类别ID
	 */
	public void setCharge_kind_id(Long value) {
		this.charge_kind_id = value;
	}
	/**
	 * 获取 收费类别ID
	 */	
	public Long getCharge_kind_id() {
		return this.charge_kind_id;
	}
	/**
	 * 设置 收费类别编码
	 */
	public void setCharge_kind_code(String value) {
		this.charge_kind_code = value;
	}
	/**
	 * 获取 收费类别编码
	 */	
	public String getCharge_kind_code() {
		return this.charge_kind_code;
	}
	/**
	 * 设置 收费类别名称
	 */
	public void setCharge_kind_name(String value) {
		this.charge_kind_name = value;
	}
	/**
	 * 获取 收费类别名称
	 */	
	public String getCharge_kind_name() {
		return this.charge_kind_name;
	}
	/**
	 * 设置 收入类型ID
	 */
	public void setIncome_type_id(Long value) {
		this.income_type_id = value;
	}
	/**
	 * 获取 收入类型ID
	 */	
	public Long getIncome_type_id() {
		return this.income_type_id;
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
}