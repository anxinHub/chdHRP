/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 是否结转（IS_CARRIED）：0否，1是
是否停用（IS_STOP):0否，1是
数据性质（DATA_NATURE）取自系统字典表：01可累加02状态值

 * @Table:
 * BUDG_INDEX_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgIndexDict implements Serializable {

	
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
	 * 指标编码
	 */
	private String index_code;
	
	/**
	 * 指标名称
	 */
	private String index_name;
	
	/**
	 * 代码组编码:unittype,为用户自定义项目

	 */
	private String unit;
	
	/**
	 * 数据精度
	 */
	private Integer data_precision;
	
	/**
	 * 数据性质
	 */
	private String data_nature;
	
	/**
	 * 是否结转
	 */
	private Integer is_carried;
	private String is_carried_name ;
	
	/**
	 * 是否停用
	 */
	private Integer is_stop;
	private String is_stop_name;
	
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
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集团ID
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 医院ID
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 医院ID
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 账套编码
	* @param value 
	*/
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 账套编码
	* @return String
	*/
	public String getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 指标编码
	* @param value 
	*/
	public void setIndex_code(String value) {
		this.index_code = value;
	}
	
	/**
	* 获取 指标编码
	* @return String
	*/
	public String getIndex_code() {
		return this.index_code;
	}
	/**
	* 设置 指标名称
	* @param value 
	*/
	public void setIndex_name(String value) {
		this.index_name = value;
	}
	
	/**
	* 获取 指标名称
	* @return String
	*/
	public String getIndex_name() {
		return this.index_name;
	}
	/**
	* 设置 代码组编码:unittype,为用户自定义项目

	* @param value 
	*/
	public void setUnit(String value) {
		this.unit = value;
	}
	
	/**
	* 获取 代码组编码:unittype,为用户自定义项目

	* @return String
	*/
	public String getUnit() {
		return this.unit;
	}
	/**
	* 设置 数据精度
	* @param value 
	*/
	public void setData_precision(Integer value) {
		this.data_precision = value;
	}
	
	/**
	* 获取 数据精度
	* @return Integer
	*/
	public Integer getData_precision() {
		return this.data_precision;
	}
	/**
	* 设置 数据性质
	* @param value 
	*/
	public void setData_nature(String value) {
		this.data_nature = value;
	}
	
	/**
	* 获取 数据性质
	* @return String
	*/
	public String getData_nature() {
		return this.data_nature;
	}
	/**
	* 设置 是否结转
	* @param value 
	*/
	public void setIs_carried(Integer value) {
		this.is_carried = value;
	}
	
	/**
	* 获取 是否结转
	* @return Integer
	*/
	public Integer getIs_carried() {
		return this.is_carried;
	}
	/**
	* 设置 是否停用
	* @param value 
	*/
	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}
	
	/**
	* 获取 是否停用
	* @return Integer
	*/
	public Integer getIs_stop() {
		return this.is_stop;
	}
	/**
	* 设置 拼音码
	* @param value 
	*/
	public void setSpell_code(String value) {
		this.spell_code = value;
	}
	
	/**
	* 获取 拼音码
	* @return String
	*/
	public String getSpell_code() {
		return this.spell_code;
	}
	/**
	* 设置 五笔码
	* @param value 
	*/
	public void setWbx_code(String value) {
		this.wbx_code = value;
	}
	
	/**
	* 获取 五笔码
	* @return String
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

	public String getIs_carried_name() {
		return is_carried_name;
	}

	public void setIs_carried_name(String is_carried_name) {
		this.is_carried_name = is_carried_name;
	}

	public String getIs_stop_name() {
		return is_stop_name;
	}

	public void setIs_stop_name(String is_stop_name) {
		this.is_stop_name = is_stop_name;
	}
	
	
}