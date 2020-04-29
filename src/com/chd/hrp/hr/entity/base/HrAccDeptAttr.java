/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.hr.entity.base;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 部门字典属性表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class HrAccDeptAttr implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 变更ID
	 */
	private String dept_no;
	/**
	 * 集团ID
	 */
	private String group_id;
	/**
	 * 医院ID
	 */
	private String hos_id;
	/**
	 * 部门ID
	 */
	private String dept_id;
	/**
	 * 部门类型
	 */
	private String type_code;
	/**
	 * 部门性质
	 */
	private String natur_code;
	/**
	 * 支出性质
	 */
	private String out_code;
	/**
	 * 部门主管
	 */
	private Long emp_id;
	/**
	 * 是否职能科室
	 */
	private Integer is_manager;
	/**
	 * 是否采购科室
	 */
	private Integer is_stock;
	
	private String dept_code;
	
	private String dept_name;
	
	private String super_code;
	
	private String super_name;
	
	private String kind_code;
	
	private String kind_name;
	
	private String type_name;
	
	private String natur_name;
	
	private String out_name;
	
	private String emp_name;
	
	private String emp_code;
	
	private String error_type;
	private String para_code;
	private String para_name;
	

	public String getError_type() {
		return error_type;
	}
	public void setError_type(String error_type) {
		this.error_type = error_type;
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
	public String getSuper_code() {
		return super_code;
	}
	public void setSuper_code(String super_code) {
		this.super_code = super_code;
	}
	public String getSuper_name() {
		return super_name;
	}
	public void setSuper_name(String super_name) {
		this.super_name = super_name;
	}
	public String getKind_code() {
		return kind_code;
	}
	public void setKind_code(String kind_code) {
		this.kind_code = kind_code;
	}
	public String getKind_name() {
		return kind_name;
	}
	public void setKind_name(String kind_name) {
		this.kind_name = kind_name;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public String getNatur_name() {
		return natur_name;
	}
	public void setNatur_name(String natur_name) {
		this.natur_name = natur_name;
	}
	public String getOut_name() {
		return out_name;
	}
	public void setOut_name(String out_name) {
		this.out_name = out_name;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	/**
	 * 设置 变更ID
	 */
	public void setDept_no(String value) {
		this.dept_no = value;
	}
	/**
	 * 获取 变更ID
	 */	
	public String getDept_no() {
		return this.dept_no;
	}
	/**
	 * 设置 集团ID
	 */
	public void setGroup_id(String value) {
		this.group_id = value;
	}
	/**
	 * 获取 集团ID
	 */	
	public String getGroup_id() {
		return this.group_id;
	}
	/**
	 * 设置 医院ID
	 */
	public void setHos_id(String value) {
		this.hos_id = value;
	}
	/**
	 * 获取 医院ID
	 */	
	public String getHos_id() {
		return this.hos_id;
	}
	/**
	 * 设置 部门ID
	 */
	public void setDept_id(String value) {
		this.dept_id = value;
	}
	/**
	 * 获取 部门ID
	 */	
	public String getDept_id() {
		return this.dept_id;
	}
	/**
	 * 设置 部门类型
	 */
	public void setType_code(String value) {
		this.type_code = value;
	}
	/**
	 * 获取 部门类型
	 */	
	public String getType_code() {
		return this.type_code;
	}
	/**
	 * 设置 部门性质
	 */
	public void setNatur_code(String value) {
		this.natur_code = value;
	}
	/**
	 * 获取 部门性质
	 */	
	public String getNatur_code() {
		return this.natur_code;
	}
	/**
	 * 设置 支出性质
	 */
	public void setOut_code(String value) {
		this.out_code = value;
	}
	/**
	 * 获取 支出性质
	 */	
	public String getOut_code() {
		return this.out_code;
	}
	
	public Long getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(Long emp_id) {
		this.emp_id = emp_id;
	}
	/**
	 * 设置 是否职能科室
	 */
	public void setIs_manager(Integer value) {
		this.is_manager = value;
	}
	/**
	 * 获取 是否职能科室
	 */	
	public Integer getIs_manager() {
		return this.is_manager;
	}
	/**
	 * 设置 是否采购科室
	 */
	public void setIs_stock(Integer value) {
		this.is_stock = value;
	}
	/**
	 * 获取 是否采购科室
	 */	
	public Integer getIs_stock() {
		return this.is_stock;
	}
	public String getEmp_code() {
		return emp_code;
	}
	public void setEmp_code(String emp_code) {
		this.emp_code = emp_code;
	}
	
    public String getPara_code() {
    	return para_code;
    }
	
    public void setPara_code(String para_code) {
    	this.para_code = para_code;
    }
	
    public String getPara_name() {
    	return para_name;
    }
	
    public void setPara_name(String para_name) {
    	this.para_name = para_name;
    }
	
	
}