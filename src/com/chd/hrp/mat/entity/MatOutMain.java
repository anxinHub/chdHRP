/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * IN_NO:每个库房每个月从SS-YYYYMM0001开始编号 ss库号
状态：0:验收;   1:未审核；2审核；3入库确认；4财务记帐。

 * @Table:
 * MAT_OUT_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MatOutMain implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private Long group_id;
	
	/**
	 * 
	 */
	private Long hos_id;
	
	/**
	 * 
	 */
	private Object copy_code;
	
	/**
	 * 
	 */
	private Long out_id;
	
	/**
	 * 
	 */
	private Object out_no;
	
	/**
	 * 
	 */
	private Object year;
	
	/**
	 * 
	 */
	private Object month;
	
	/**
	 * 
	 */
	private Object bus_type_code;
	
	/**
	 * 
	 */
	private Long store_id;
	
	/**
	 * 
	 */
	private Long store_no;
	
	/**
	 * 
	 */
	private Object brief;
	
	/**
	 * 
	 */
	private Date out_date;
	
	/**
	 * 
	 */
	private Long dept_id;
	
	/**
	 * 
	 */
	private Long dept_no;
	
	/**
	 * 
	 */
	private Long dept_emp;
	
	/**
	 * 
	 */
	private Object use_code;
	
	/**
	 * 
	 */
	private Integer proj_id;
	
	/**
	 * 
	 */
	private Long maker;
	
	/**
	 * 
	 */
	private Long checker;
	
	/**
	 * 
	 */
	private Date check_date;
	
	/**
	 * 
	 */
	private Long confirmer;
	
	/**
	 * 
	 */
	private Date confirm_date;
	
	/**
	 * 
	 */
	private Integer state;
	
	/**
	 * 
	 */
	private Integer is_dir;
	
	/**
	 * 
	 */
	private Object his_flag;
	
	/**
	 * 
	 */
	private Long emp_no;
	
	private Double amount_money;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCopy_code(Object value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setOut_id(Long value) {
		this.out_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getOut_id() {
		return this.out_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setOut_no(Object value) {
		this.out_no = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getOut_no() {
		return this.out_no;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setYear(Object value) {
		this.year = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getYear() {
		return this.year;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setMonth(Object value) {
		this.month = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getMonth() {
		return this.month;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setBus_type_code(Object value) {
		this.bus_type_code = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getBus_type_code() {
		return this.bus_type_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setStore_id(Long value) {
		this.store_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getStore_id() {
		return this.store_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setStore_no(Long value) {
		this.store_no = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getStore_no() {
		return this.store_no;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setBrief(Object value) {
		this.brief = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getBrief() {
		return this.brief;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setOut_date(Date value) {
		this.out_date = value;
	}
	
	/**
	* 获取 
	* @return Date
	*/
	public Date getOut_date() {
		return this.out_date;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setDept_emp(Long value) {
		this.dept_emp = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getDept_emp() {
		return this.dept_emp;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setUse_code(Object value) {
		this.use_code = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getUse_code() {
		return this.use_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setProj_id(Integer value) {
		this.proj_id = value;
	}
	
	/**
	* 获取 
	* @return Integer
	*/
	public Integer getProj_id() {
		return this.proj_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setMaker(Long value) {
		this.maker = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getMaker() {
		return this.maker;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setChecker(Long value) {
		this.checker = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getChecker() {
		return this.checker;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCheck_date(Date value) {
		this.check_date = value;
	}
	
	/**
	* 获取 
	* @return Date
	*/
	public Date getCheck_date() {
		return this.check_date;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setConfirmer(Long value) {
		this.confirmer = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getConfirmer() {
		return this.confirmer;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setConfirm_date(Date value) {
		this.confirm_date = value;
	}
	
	/**
	* 获取 
	* @return Date
	*/
	public Date getConfirm_date() {
		return this.confirm_date;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setState(Integer value) {
		this.state = value;
	}
	
	/**
	* 获取 
	* @return Integer
	*/
	public Integer getState() {
		return this.state;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setIs_dir(Integer value) {
		this.is_dir = value;
	}
	
	/**
	* 获取 
	* @return Integer
	*/
	public Integer getIs_dir() {
		return this.is_dir;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setHis_flag(Object value) {
		this.his_flag = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getHis_flag() {
		return this.his_flag;
	}
	
	public Long getEmp_no() {
		return emp_no;
	}

	public void setEmp_no(Long emp_no) {
		this.emp_no = emp_no;
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

	public Double getAmount_money() {
		return amount_money;
	}

	public void setAmount_money(Double amount_money) {
		this.amount_money = amount_money;
	}
}