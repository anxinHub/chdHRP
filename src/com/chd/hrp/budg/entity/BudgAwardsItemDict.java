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
 * 期间属性(PERIOD_NATURE):取自系统字典表
01年度
02半年度
03季度
04月度
05其他
 * @Table:
 * BUDG_AWARDS_ITEM_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgAwardsItemDict implements Serializable {

	
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
	 * 奖金项目编码
	 */
	private String awards_item_code;
	
	/**
	 * 奖金项目名称
	 */
	private String awards_item_name;
	
	/**
	 * 期间属性
	 */
	private String period_nature;
	
	/**
	 * 0:不停用 1:停用
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
	* 设置 奖金项目编码
	* @param value 
	*/
	public void setAwards_item_code(String value) {
		this.awards_item_code = value;
	}
	
	/**
	* 获取 奖金项目编码
	* @return String
	*/
	public String getAwards_item_code() {
		return this.awards_item_code;
	}
	/**
	* 设置 奖金项目名称
	* @param value 
	*/
	public void setAwards_item_name(String value) {
		this.awards_item_name = value;
	}
	
	/**
	* 获取 奖金项目名称
	* @return String
	*/
	public String getAwards_item_name() {
		return this.awards_item_name;
	}
	/**
	* 设置 期间属性
	* @param value 
	*/
	public void setPeriod_nature(String value) {
		this.period_nature = value;
	}
	
	/**
	* 获取 期间属性
	* @return String
	*/
	public String getPeriod_nature() {
		return this.period_nature;
	}
	/**
	* 设置 0:不停用 1:停用
	* @param value 
	*/
	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}
	
	/**
	* 获取 0:不停用 1:停用
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
}