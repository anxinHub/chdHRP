/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 成本_分摊服务科室
 * @Table:
 * COST_PARA_DEPT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class CostParaDept implements Serializable {

	
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
	 * 统计年份
	 */
	private String acc_year;
	
	/**
	 * 统计月份
	 */
	private String acc_month;
	
	/**
	 * 序号
	 */
	private Long bill_code;
	
	/**
	 * 服务科室ID
	 */
	private Long dept_id;
	
	/**
	 * 服务科室变更ID
	 */
	private Long dept_no;
	/**
	 * 服务科室编码
	 */
	private String dept_code;
	/**
	 * 服务科室名称
	 */
	private String dept_name;
	/**
	 * 科室类型
	 */
	private String type_code;
	/**
	 * 科室性质
	 */
	private String natur_code;
	/**
	 * 分摊性质
	 */
	private String para_code;

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
	* 设置 统计年份
	* @param value 
	*/
	public void setAcc_year(String value) {
		this.acc_year = value;
	}
	
	/**
	* 获取 统计年份
	* @return String
	*/
	public String getAcc_year() {
		return this.acc_year;
	}
	/**
	* 设置 统计月份
	* @param value 
	*/
	public void setAcc_month(String value) {
		this.acc_month = value;
	}
	
	/**
	* 获取 统计月份
	* @return String
	*/
	public String getAcc_month() {
		return this.acc_month;
	}
	/**
	* 设置 序号
	* @param value 
	*/
	public void setBill_code(Long value) {
		this.bill_code = value;
	}
	
	/**
	* 获取 序号
	* @return Long
	*/
	public Long getBill_code() {
		return this.bill_code;
	}
	/**
	* 设置 服务科室ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 服务科室ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 服务科室变更ID
	* @param value 
	*/
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	
	/**
	* 获取 服务科室变更ID
	* @return Long
	*/
	public Long getDept_no() {
		return this.dept_no;
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

	
    public String getType_code() {
    	return type_code;
    }

	
    public void setType_code(String type_code) {
    	this.type_code = type_code;
    }

	
    public String getNatur_code() {
    	return natur_code;
    }

	
    public void setNatur_code(String natur_code) {
    	this.natur_code = natur_code;
    }

	
    public String getPara_code() {
    	return para_code;
    }

	
    public void setPara_code(String para_code) {
    	this.para_code = para_code;
    }
	
}