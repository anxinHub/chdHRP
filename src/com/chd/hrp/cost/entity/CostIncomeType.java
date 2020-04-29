/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 成本_收入类型(01: 药品收入02:  医疗收入03: 材料收入)<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostIncomeType implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	private Long income_type_id;
	/**
	 * 收入类型编码
	 */
	private String income_type_code;
	/**
	 * 收入类型名称
	 */
	private String income_type_name;
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
	
	public Long getIncome_type_id() {
		return income_type_id;
	}
	public void setIncome_type_id(Long income_type_id) {
		this.income_type_id = income_type_id;
	}
	/**
	 * 设置 收入类型编码
	 */
	public void setIncome_type_code(String value) {
		this.income_type_code = value;
	}
	/**
	 * 获取 收入类型编码
	 */	
	public String getIncome_type_code() {
		return this.income_type_code;
	}
	/**
	 * 设置 收入类型名称
	 */
	public void setIncome_type_name(String value) {
		this.income_type_name = value;
	}
	/**
	 * 获取 收入类型名称
	 */	
	public String getIncome_type_name() {
		return this.income_type_name;
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
}