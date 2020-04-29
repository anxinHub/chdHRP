
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 8806 奖金科室映射表
 * @Table:
 * APHI_DEPT_MAPING
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class PrmDeptMaping implements Serializable {

	
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
	 * 部门ID
	 */
	private Double dept_id;
	
	
	/**
	 * 平台科室ID
	 */
	private String sys_dept_id;
	
	/**
	 * 01:平行 02:拆分 C03:合并
	 */
	private String ref_code;
	
	/**
	 * 拆分比例
	 */
	private Double spilt_perc;
	
	private String dept_code;
	
	private String dept_name;
	
	private String sys_dept_code;
	
	private String sys_dept_name;
	
	private String ref_name;
	

	public String getRef_name() {
		return ref_name;
	}

	public void setRef_name(String ref_name) {
		this.ref_name = ref_name;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getSys_dept_code() {
		return sys_dept_code;
	}

	public void setSys_dept_code(String sys_dept_code) {
		this.sys_dept_code = sys_dept_code;
	}

	public String getSys_dept_name() {
		return sys_dept_name;
	}

	public void setSys_dept_name(String sys_dept_name) {
		this.sys_dept_name = sys_dept_name;
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
	* 设置 部门ID
	* @param value 
	*/
	public void setDept_id(Double value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 部门ID
	* @return Double
	*/
	public Double getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 平台科室ID
	* @param value 
	*/
	public void setSys_dept_id(String value) {
		this.sys_dept_id = value;
	}
	
	/**
	* 获取 平台科室ID
	* @return String
	*/
	public String getSys_dept_id() {
		return this.sys_dept_id;
	}
	/**
	* 设置 01:平行 02:拆分 C03:合并
	* @param value 
	*/
	public void setRef_code(String value) {
		this.ref_code = value;
	}
	
	/**
	* 获取 01:平行 02:拆分 C03:合并
	* @return String
	*/
	public String getRef_code() {
		return this.ref_code;
	}
	/**
	* 设置 拆分比例
	* @param value 
	*/
	public void setSpilt_perc(Double value) {
		this.spilt_perc = value;
	}
	
	/**
	* 获取 拆分比例
	* @return Double
	*/
	public Double getSpilt_perc() {
		return this.spilt_perc;
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