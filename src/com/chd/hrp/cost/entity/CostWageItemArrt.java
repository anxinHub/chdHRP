/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 成本_工资项属性表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostWageItemArrt implements Serializable {

	
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
	 * 工资项编码
	 */
	private String wage_item_code;
	/**
	 * 工资项名称
	 */
	private String wage_item_name;
	/**
	 * 是否停用
	 */
	private Integer is_stop;
	/**
	 * 备注
	 */
	private String remark;
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
	 * 设置 工资项编码
	 */
	public void setWage_item_code(String value) {
		this.wage_item_code = value;
	}
	/**
	 * 获取 工资项编码
	 */	
	public String getWage_item_code() {
		return this.wage_item_code;
	}
	/**
	 * 设置 工资项名称
	 */
	public void setWage_item_name(String value) {
		this.wage_item_name = value;
	}
	/**
	 * 获取 工资项名称
	 */	
	public String getWage_item_name() {
		return this.wage_item_name;
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
	/**
	 * 设置 备注
	 */
	public void setRemark(String value) {
		this.remark = value;
	}
	/**
	 * 获取 备注
	 */	
	public String getRemark() {
		return this.remark;
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