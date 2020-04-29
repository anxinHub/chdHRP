/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 科室人员表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostDeptPeople implements Serializable {

	
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
	 * 科室ID
	 */
	private Long dept_id;
	/**
	 * 科室变更ID
	 */
	private Long dept_no;
	/**
	 * 科室编码
	 */
	private String dept_code;
	/**
	 * 科室名称
	 */
	private String dept_name;
	/**
	 * 医生数量
	 */
	private Long doctor_num;
	/**
	 * 护士数量
	 */
	private Long nurse_num;
	/**
	 * 技师数量
	 */
	private Long technician_num;
	/**
	 * 药师数量
	 */
	private Long pharmacist_num;
	/**
	 * 管理人员数量
	 */
	private Long manager_num;
	/**
	 * 后勤人员数量
	 */
	private Long supportor_num;
	/**
	 * 临时工数量
	 */
	private Long floater_num;
	/**
	 * 外聘人员数量
	 */
	private Long out_employ_num;
	/**
	 * 保洁人员数量
	 */
	private Long clearner_num;
	
	/**
	 * 科室人员总数量
	 */
	private Long alldept_num;
	
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
	 * @return the acc_year
	 */
	public String getAcc_year() {
		return acc_year;
	}
	/**
	 * @param acc_year the acc_year to set
	 */
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	/**
	 * @return the acc_month
	 */
	public String getAcc_month() {
		return acc_month;
	}
	/**
	 * @param acc_month the acc_month to set
	 */
	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
	}
	/**
	 * 设置 科室ID
	 */
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	/**
	 * 获取 科室ID
	 */	
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	 * 设置 科室变更ID
	 */
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	/**
	 * 获取 科室变更ID
	 */	
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	 * 设置 医生数量
	 */
	public void setDoctor_num(Long value) {
		this.doctor_num = value;
	}
	/**
	 * 获取 医生数量
	 */	
	public Long getDoctor_num() {
		return this.doctor_num;
	}
	/**
	 * 设置 护士数量
	 */
	public void setNurse_num(Long value) {
		this.nurse_num = value;
	}
	/**
	 * 获取 护士数量
	 */	
	public Long getNurse_num() {
		return this.nurse_num;
	}
	/**
	 * 设置 技师数量
	 */
	public void setTechnician_num(Long value) {
		this.technician_num = value;
	}
	/**
	 * 获取 技师数量
	 */	
	public Long getTechnician_num() {
		return this.technician_num;
	}
	/**
	 * 设置 药师数量
	 */
	public void setPharmacist_num(Long value) {
		this.pharmacist_num = value;
	}
	/**
	 * 获取 药师数量
	 */	
	public Long getPharmacist_num() {
		return this.pharmacist_num;
	}
	/**
	 * 设置 管理人员数量
	 */
	public void setManager_num(Long value) {
		this.manager_num = value;
	}
	/**
	 * 获取 管理人员数量
	 */	
	public Long getManager_num() {
		return this.manager_num;
	}
	/**
	 * 设置 后勤人员数量
	 */
	public void setSupportor_num(Long value) {
		this.supportor_num = value;
	}
	/**
	 * 获取 后勤人员数量
	 */	
	public Long getSupportor_num() {
		return this.supportor_num;
	}
	/**
	 * 设置 临时工数量
	 */
	public void setFloater_num(Long value) {
		this.floater_num = value;
	}
	/**
	 * 获取 临时工数量
	 */	
	public Long getFloater_num() {
		return this.floater_num;
	}
	/**
	 * 设置 外聘人员数量
	 */
	public void setOut_employ_num(Long value) {
		this.out_employ_num = value;
	}
	/**
	 * 获取 外聘人员数量
	 */	
	public Long getOut_employ_num() {
		return this.out_employ_num;
	}
	/**
	 * 设置 保洁人员数量
	 */
	public void setClearner_num(Long value) {
		this.clearner_num = value;
	}
	/**
	 * 获取 保洁人员数量
	 */	
	public Long getClearner_num() {
		return this.clearner_num;
	}
	
	
	
	
	/**
	 * 设置 科室人员数量
	 */
	public void setAlldept_num(Long value) {
		this.alldept_num = value;
	}
	/**
	 * 获取 科室人员数量
	 */	
	public Long getAlldept_num() {
		return this.alldept_num;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @return the year_month
	 */
	public String getYear_month() {
		return year_month;
	}
	/**
	 * @param year_month the year_month to set
	 */
	public void setYear_month(String year_month) {
		this.year_month = year_month;
	}
	
}