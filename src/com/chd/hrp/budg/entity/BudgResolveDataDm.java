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
 * 自定义分解系数(科室月)
 * @Table:
 * BUDG_RESOLVE_DATA_DM
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */


public class BudgResolveDataDm implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5586645734736849563L;

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
	 * 分解系数编码
	 */
	private String resolve_data_code;
	
	/**
	 * 科室
	 */
	private Long dept_id;
	
	/**
	 * 月份
	 */
	private String month;
	
	/**
	 * 数值
	 */
	private Double value;

   /**
	 * 导入验证信息
	 */
	private String error_type;
	

	
	/**
	 * 获取 集团ID
	 */
	public Long getGroup_id() {
		return group_id;
	}
	
	/**
	 * 设置 集团ID
	 */
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	
	/**
	 * 获取 医院ID
	 */
	public Long getHos_id() {
		return hos_id;
	}
	
	/**
	 * 设置 集团ID
	 */
	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}
	
	/**
	 * 获取 账套编码
	 */
	public String getCopy_code() {
		return copy_code;
	}
	
	/**
	 * 设置 账套编码
	 */
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public String getResolve_data_code() {
		return resolve_data_code;
	}

	public void setResolve_data_code(String resolve_data_code) {
		this.resolve_data_code = resolve_data_code;
	}

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getError_type() {
		return error_type;
	}
	
	
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
}