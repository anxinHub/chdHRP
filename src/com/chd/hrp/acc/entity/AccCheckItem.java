/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 核算项<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccCheckItem implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 核算项ID
	 */
	private Long check_item_id;
	/**
	 * 核算类ID
	 */
	private Long check_type_id;
	private String check_type_name;
	private String column_check;
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
	 * 核算项编码
	 */
	private String check_item_code;
	/**
	 * 核算项名称
	 */
	private String check_item_name;
	/**
	 * 排序号
	 */
	private Long sort_code;
	/**
	 * 拼音码
	 */
	private String spell_code;
	/**
	 * 五笔码
	 */
	private String wbx_code;
	/**
	 * 是否停用
	 */
	private Integer is_stop;
	
	private String  type_code;
	
	private String  type_name;
	
	private String error_type;
	
	/**
	 * 设置 核算项ID
	 */
	public void setCheck_item_id(Long value) {
		this.check_item_id = value;
	}
	/**
	 * 获取 核算项ID
	 */	
	public Long getCheck_item_id() {
		return this.check_item_id;
	}
	/**
	 * 设置 核算类ID
	 */
	public void setCheck_type_id(Long value) {
		this.check_type_id = value;
	}
	/**
	 * 获取 核算类ID
	 */	
	public Long getCheck_type_id() {
		return this.check_type_id;
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
	 * 设置 核算项编码
	 */
	public void setCheck_item_code(String value) {
		this.check_item_code = value;
	}
	/**
	 * 获取 核算项编码
	 */	
	public String getCheck_item_code() {
		return this.check_item_code;
	}
	/**
	 * 设置 核算项名称
	 */
	public void setCheck_item_name(String value) {
		this.check_item_name = value;
	}
	/**
	 * 获取 核算项名称
	 */	
	public String getCheck_item_name() {
		return this.check_item_name;
	}
	
	public Long getSort_code() {
		return sort_code;
	}
	public void setSort_code(Long sort_code) {
		this.sort_code = sort_code;
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
	public String getCheck_type_name() {
		return check_type_name;
	}
	public void setCheck_type_name(String check_type_name) {
		this.check_type_name = check_type_name;
	}
	public String getColumn_check() {
		return column_check;
	}
	public void setColumn_check(String column_check) {
		this.column_check = column_check;
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
	public String getError_type() {
		return error_type;
	}
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
	
}