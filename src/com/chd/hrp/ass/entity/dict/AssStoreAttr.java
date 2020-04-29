
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.entity.dict;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050114 库房附属表
 * @Table:
 * ASS_STORE_ATTR
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssStoreAttr implements Serializable {

	
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
	 * 库房ID
	 */
	private Long store_id;
	
	private Long store_no;
	
	private String store_code;
	/**
	 * 库房名称
	 */
	private String store_name;
	
	/**
	 * 主管部门ID
	 */
	private Long dept_id;
	
	/**
	 * 主管部门NO
	 */
	private Long dept_no;
	private String dept_code ;
	private String dept_name;
	
	/**
	 * 是否采购库房
	 */
	private Integer is_proc;
	
	/**
	 * 负责人
	 */
	private Long head_emp_id;
	private String head_emp_name;
	private Long head_emp_no;
	/**
	 * 负责人电话
	 */
	private String mobile;
	
	/**
	 * 会计
	 */
	private Long acc_emp_id;
	private String acc_emp_name;
	private Long acc_emp_no;
	/**
	 * 保管员
	 */
	private Long emp_id;
	private Long emp_no;
	private String emp_id_no;
	
	private Long safe_emp_id;
	private String safe_emp_name;
	private Long safe_emp_no;
	/**
	 * 采购员
	 */
	private Long proc_emp_id;
	private String proc_emp_name;
	private Long proc_emp_no;
	/**
	 * 地址
	 */
	private String address;
	
	/**
	 * 备注
	 */
	private String note;
	

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
	* 设置 库房ID
	* @param value 
	*/
	public void setStore_id(Long value) {
		this.store_id = value;
	}
	
	/**
	* 获取 库房ID
	* @return Long
	*/
	public Long getStore_id() {
		return this.store_id;
	}
	/**
	* 设置 主管部门ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 主管部门ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 主管部门NO
	* @param value 
	*/
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	
	/**
	* 获取 主管部门NO
	* @return Long
	*/
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	* 设置 是否采购库房
	* @param value 
	*/
	public void setIs_proc(Integer value) {
		this.is_proc = value;
	}
	
	/**
	* 获取 是否采购库房
	* @return Integer
	*/
	public Integer getIs_proc() {
		return this.is_proc;
	}
	/**
	* 设置 负责人
	* @param value 
	*/
	public void setHead_emp_id(Long value) {
		this.head_emp_id = value;
	}
	
	/**
	* 获取 负责人
	* @return Long
	*/
	public Long getHead_emp_id() {
		return this.head_emp_id;
	}
	/**
	* 设置 负责人电话
	* @param value 
	*/
	public void setMobile(String value) {
		this.mobile = value;
	}
	
	/**
	* 获取 负责人电话
	* @return String
	*/
	public String getMobile() {
		return this.mobile;
	}
	/**
	* 设置 会计
	* @param value 
	*/
	public void setAcc_emp_id(Long value) {
		this.acc_emp_id = value;
	}
	
	/**
	* 获取 会计
	* @return Long
	*/
	public Long getAcc_emp_id() {
		return this.acc_emp_id;
	}
	/**
	* 设置 保管员
	* @param value 
	*/
	public void setSafe_emp_id(Long value) {
		this.safe_emp_id = value;
	}
	
	/**
	* 获取 保管员
	* @return Long
	*/
	public Long getSafe_emp_id() {
		return this.safe_emp_id;
	}
	/**
	* 设置 采购员
	* @param value 
	*/
	public void setProc_emp_id(Long value) {
		this.proc_emp_id = value;
	}
	
	/**
	* 获取 采购员
	* @return Long
	*/
	public Long getProc_emp_id() {
		return this.proc_emp_id;
	}
	/**
	* 设置 地址
	* @param value 
	*/
	public void setAddress(String value) {
		this.address = value;
	}
	
	/**
	* 获取 地址
	* @return String
	*/
	public String getAddress() {
		return this.address;
	}
	/**
	* 设置 备注
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 备注
	* @return String
	*/
	public String getNote() {
		return this.note;
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
	
	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public Long getStore_no() {
		return store_no;
	}

	public void setStore_no(Long store_no) {
		this.store_no = store_no;
	}

	public String getStore_code() {
		return store_code;
	}

	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	/**
	 * @return the dept_name
	 */
	public String getDept_name() {
		return dept_name;
	}

	/**
	 * @param dept_name the dept_name to set
	 */
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	/**
	 * @return the head_emp_name
	 */
	public String getHead_emp_name() {
		return head_emp_name;
	}

	/**
	 * @param head_emp_name the head_emp_name to set
	 */
	public void setHead_emp_name(String head_emp_name) {
		this.head_emp_name = head_emp_name;
	}

	/**
	 * @return the acc_emp_name
	 */
	public String getAcc_emp_name() {
		return acc_emp_name;
	}

	/**
	 * @param acc_emp_name the acc_emp_name to set
	 */
	public void setAcc_emp_name(String acc_emp_name) {
		this.acc_emp_name = acc_emp_name;
	}

	/**
	 * @return the safe_emp_name
	 */
	public String getSafe_emp_name() {
		return safe_emp_name;
	}

	/**
	 * @param safe_emp_name the safe_emp_name to set
	 */
	public void setSafe_emp_name(String safe_emp_name) {
		this.safe_emp_name = safe_emp_name;
	}

	/**
	 * @return the proc_emp_name
	 */
	public String getProc_emp_name() {
		return proc_emp_name;
	}

	/**
	 * @param proc_emp_name the proc_emp_name to set
	 */
	public void setProc_emp_name(String proc_emp_name) {
		this.proc_emp_name = proc_emp_name;
	}

	/**
	 * @return the emp_id
	 */
	public Long getEmp_id() {
		return emp_id;
	}

	/**
	 * @param emp_id the emp_id to set
	 */
	public void setEmp_id(Long emp_id) {
		this.emp_id = emp_id;
	}

	/**
	 * @return the emp_no
	 */
	public Long getEmp_no() {
		return emp_no;
	}

	/**
	 * @param emp_no the emp_no to set
	 */
	public void setEmp_no(Long emp_no) {
		this.emp_no = emp_no;
	}

	/**
	 * @return the emp_id_no
	 */
	public String getEmp_id_no() {
		return emp_id_no;
	}

	/**
	 * @param emp_id_no the emp_id_no to set
	 */
	public void setEmp_id_no(String emp_id_no) {
		this.emp_id_no = emp_id_no;
	}

	/**
	 * @return the head_emp_no
	 */
	public Long getHead_emp_no() {
		return head_emp_no;
	}

	/**
	 * @param head_emp_no the head_emp_no to set
	 */
	public void setHead_emp_no(Long head_emp_no) {
		this.head_emp_no = head_emp_no;
	}

	/**
	 * @return the acc_emp_no
	 */
	public Long getAcc_emp_no() {
		return acc_emp_no;
	}

	/**
	 * @param acc_emp_no the acc_emp_no to set
	 */
	public void setAcc_emp_no(Long acc_emp_no) {
		this.acc_emp_no = acc_emp_no;
	}

	/**
	 * @return the safe_emp_no
	 */
	public Long getSafe_emp_no() {
		return safe_emp_no;
	}

	/**
	 * @param safe_emp_no the safe_emp_no to set
	 */
	public void setSafe_emp_no(Long safe_emp_no) {
		this.safe_emp_no = safe_emp_no;
	}

	/**
	 * @return the proc_emp_no
	 */
	public Long getProc_emp_no() {
		return proc_emp_no;
	}

	/**
	 * @param proc_emp_no the proc_emp_no to set
	 */
	public void setProc_emp_no(Long proc_emp_no) {
		this.proc_emp_no = proc_emp_no;
	}
	
	
}