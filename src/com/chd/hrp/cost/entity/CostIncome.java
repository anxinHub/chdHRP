/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 科室收入数据总表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostIncome implements Serializable {

	
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
	 * 统计年月
	 */
	private String year_month;
	
	private String acc_year;
	
	private String acc_month;
	/**
	 * 开单科室ID
	 */
	private Long appl_dept_id;
	/**
	 * 开单科室变更ID
	 */
	private Long appl_dept_no;
	
	private String appl_dept_code;
	
	private String appl_dept_name;
	/**
	 * 执行科室ID
	 */
	private Long exec_dept_id;
	/**
	 * 执行科室变更ID
	 */
	private Long exec_dept_no;
	
	private String exec_dept_code;
	
	private String exec_dept_name;
	/**
	 * 收费类别
	 */
	
	private Long charge_kind_id;
	
	
	private String charge_kind_code;
	
	private String charge_kind_name;
	/**
	 * 金额
	 */
	private double money;
	/**
	 * 操作员ID
	 */
	private Long create_user;
	
	private String create_code;
	
	
	private String create_name;
	/**
	 * 操作时间
	 */
	private Date create_date;
	/**
	 * 导入验证信息
	 */
	private String error_type;
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
	
	
	
	public String getCreate_code() {
		return create_code;
	}
	public void setCreate_code(String create_code) {
		this.create_code = create_code;
	}
	public String getCreate_name() {
		return create_name;
	}
	public void setCreate_name(String create_name) {
		this.create_name = create_name;
	}
	public String getAppl_dept_code() {
		return appl_dept_code;
	}
	public void setAppl_dept_code(String appl_dept_code) {
		this.appl_dept_code = appl_dept_code;
	}
	public String getAppl_dept_name() {
		return appl_dept_name;
	}
	public void setAppl_dept_name(String appl_dept_name) {
		this.appl_dept_name = appl_dept_name;
	}
	public String getExec_dept_code() {
		return exec_dept_code;
	}
	public void setExec_dept_code(String exec_dept_code) {
		this.exec_dept_code = exec_dept_code;
	}
	public String getExec_dept_name() {
		return exec_dept_name;
	}
	public void setExec_dept_name(String exec_dept_name) {
		this.exec_dept_name = exec_dept_name;
	}
	public Long getCharge_kind_id() {
		return charge_kind_id;
	}
	public void setCharge_kind_id(Long charge_kind_id) {
		this.charge_kind_id = charge_kind_id;
	}
	public String getCharge_kind_name() {
		return charge_kind_name;
	}
	public void setCharge_kind_name(String charge_kind_name) {
		this.charge_kind_name = charge_kind_name;
	}
	public String getAcc_year() {
		return acc_year;
	}
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	public String getAcc_month() {
		return acc_month;
	}
	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
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
	 * 设置 统计年月
	 */
	public void setYear_month(String value) {
		this.year_month = value;
	}
	/**
	 * 获取 统计年月
	 */	
	public String getYear_month() {
		return this.year_month;
	}
	/**
	 * 设置 开单科室ID
	 */
	public void setAppl_dept_id(Long value) {
		this.appl_dept_id = value;
	}
	/**
	 * 获取 开单科室ID
	 */	
	public Long getAppl_dept_id() {
		return this.appl_dept_id;
	}
	/**
	 * 设置 开单科室变更ID
	 */
	public void setAppl_dept_no(Long value) {
		this.appl_dept_no = value;
	}
	/**
	 * 获取 开单科室变更ID
	 */	
	public Long getAppl_dept_no() {
		return this.appl_dept_no;
	}
	/**
	 * 设置 执行科室ID
	 */
	public void setExec_dept_id(Long value) {
		this.exec_dept_id = value;
	}
	/**
	 * 获取 执行科室ID
	 */	
	public Long getExec_dept_id() {
		return this.exec_dept_id;
	}
	/**
	 * 设置 执行科室变更ID
	 */
	public void setExec_dept_no(Long value) {
		this.exec_dept_no = value;
	}
	/**
	 * 获取 执行科室变更ID
	 */	
	public Long getExec_dept_no() {
		return this.exec_dept_no;
	}
	/**
	 * 设置 收费类别
	 */
	public void setCharge_kind_code(String value) {
		this.charge_kind_code = value;
	}
	/**
	 * 获取 收费类别
	 */	
	public String getCharge_kind_code() {
		return this.charge_kind_code;
	}
	/**
	 * 设置 金额
	 */
	public void setMoney(double value) {
		this.money = value;
	}
	/**
	 * 获取 金额
	 */	
	public double getMoney() {
		return this.money;
	}
	/**
	 * 设置 操作员ID
	 */
	public void setCreate_user(Long value) {
		this.create_user = value;
	}
	/**
	 * 获取 操作员ID
	 */	
	public Long getCreate_user() {
		return this.create_user;
	}
	/**
	 * 设置 操作时间
	 */
	public void setCreate_date(Date value) {
		this.create_date = value;
	}
	/**
	 * 获取 操作时间
	 */	
	public Date getCreate_date() {
		return this.create_date;
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