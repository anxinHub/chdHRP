/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 成本类型字典<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostDeptTypeDict implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 成本类型ID
	 */
	private Long cost_type_id;
	/**
	 * 成本类型编码
	 */
	private String cost_type_code;
	/**
	 * 成本类型名称
	 */
	private String cost_type_name;
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
	 * 设置 成本类型ID
	 */
	public void setCost_type_id(Long value) {
		this.cost_type_id = value;
	}
	/**
	 * 获取 成本类型ID
	 */	
	public Long getCost_type_id() {
		return this.cost_type_id;
	}
	/**
	 * 设置 成本类型编码
	 */
	public void setCost_type_code(String value) {
		this.cost_type_code = value;
	}
	/**
	 * 获取 成本类型编码
	 */	
	public String getCost_type_code() {
		return this.cost_type_code;
	}
	/**
	 * 设置 成本类型名称
	 */
	public void setCost_type_name(String value) {
		this.cost_type_name = value;
	}
	/**
	 * 获取 成本类型名称
	 */	
	public String getCost_type_name() {
		return this.cost_type_name;
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