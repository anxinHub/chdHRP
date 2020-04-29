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
 * 运营尺度
 * @Table:
 * BUDG_OPERATION_MEASURE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgOperationMeasure implements Serializable {

	
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
	 * 运营尺度编码
	 */
	private String measure_code;
	
	/**
	 * 运营尺度名称
	 */
	private String measure_name;
	private String measure_name_old;
	private String is_stop_name;
	public String getIs_stop_name() {
		return is_stop_name;
	}

	public void setIs_stop_name(String is_stop_name) {
		this.is_stop_name = is_stop_name;
	}
	/**
	 * 是否停用
	 */
	private Integer is_stop;
	private Integer is_stop_old;
	

  public String getMeasure_name_old() {
		return measure_name_old;
	}

	public void setMeasure_name_old(String measure_name_old) {
		this.measure_name_old = measure_name_old;
	}

	public Integer getIs_stop_old() {
		return is_stop_old;
	}

	public void setIs_stop_old(Integer is_stop_old) {
		this.is_stop_old = is_stop_old;
	}
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
	* 设置 运营尺度编码
	* @param value 
	*/
	public void setMeasure_code(String value) {
		this.measure_code = value;
	}
	
	/**
	* 获取 运营尺度编码
	* @return String
	*/
	public String getMeasure_code() {
		return this.measure_code;
	}
	/**
	* 设置 运营尺度名称
	* @param value 
	*/
	public void setMeasure_name(String value) {
		this.measure_name = value;
	}
	
	/**
	* 获取 运营尺度名称
	* @return String
	*/
	public String getMeasure_name() {
		return this.measure_name;
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