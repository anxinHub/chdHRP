/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.plan;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050302 集团与医院购置计划关系表
 * @Table:
 * ASS_PLAN_GROUP_HOS
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssPlanGroupHos implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集体ID
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
	 * 集团计划号
	 */
	private Long group_plan_id;
	
	/**
	 * 医院计划号
	 */
	private Long hos_plan_id;
	
	/**
	 * 集体计划明细ID
	 */
	private Long hos_group_id;
	
	/**
	 * 医院计划明细ID
	 */
	private Long hos_hos_id;
	

  /**
	 * 导入验证信息
	 */
	private String hos_copy_code;
	
	/**
	* 设置 集体ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集体ID
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
	* 设置 集团计划号
	* @param value 
	*/
	public void setGroup_plan_id(Long value) {
		this.group_plan_id = value;
	}
	
	/**
	* 获取 集团计划号
	* @return Long
	*/
	public Long getGroup_plan_id() {
		return this.group_plan_id;
	}
	/**
	* 设置 医院计划号
	* @param value 
	*/
	public void setHos_plan_id(Long value) {
		this.hos_plan_id = value;
	}
	
	/**
	* 获取 医院计划号
	* @return Long
	*/
	public Long getHos_plan_id() {
		return this.hos_plan_id;
	}

	public Long getHos_group_id() {
		return hos_group_id;
	}

	public void setHos_group_id(Long hos_group_id) {
		this.hos_group_id = hos_group_id;
	}

	public Long getHos_hos_id() {
		return hos_hos_id;
	}

	public void setHos_hos_id(Long hos_hos_id) {
		this.hos_hos_id = hos_hos_id;
	}

	public String getHos_copy_code() {
		return hos_copy_code;
	}

	public void setHos_copy_code(String hos_copy_code) {
		this.hos_copy_code = hos_copy_code;
	}
	
}