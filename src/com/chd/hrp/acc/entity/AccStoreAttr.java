/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.entity;

import java.io.Serializable;

/**
 * @Title. @Description. 辅助核算<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class AccStoreAttr implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Long group_id;

	private Long hos_id;

	private Long store_id;

	private Long dept_id;
	
	private Long dept_no;
	
	private String dept_code;
	
	private String dept_name;

	private Integer is_proc;

	private Long head_emp_id;
	
	private String head_emp_code;
	
	private String head_emp_name;

	private String mobile;

	private Long acc_emp_id;
	
	private String acc_emp_code;
	
	private String acc_emp_name;

	private Long safe_emp_id;
	
	private String safe_emp_code;
	
	private String safe_emp_name;

	private Long proc_emp_id;
	
	private String proc_emp_code;
	
	private String proc_emp_name;

	private String address;

	private String note;

	private String store_code;

	private String type_code;

	private String type_name;

	private String store_name;

	private String spell_code;

	private String wbx_code;
	
	private Integer is_stop;

	private Integer is_disable;
	
	public Integer getIs_disable() {
		return is_disable;
	}

	public void setIs_disable(Integer is_disable) {
		this.is_disable = is_disable;
	}

	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public Long getHos_id() {
		return hos_id;
	}

	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}

	public Long getStore_id() {
		return store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	public Long getDept_id() {
		return dept_id;
	}

	public void setDept_id(Long dept_id) {
		this.dept_id = dept_id;
	}

	public Integer getIs_proc() {
		return is_proc;
	}

	public void setIs_proc(Integer is_proc) {
		this.is_proc = is_proc;
	}

	public Long getHead_emp_id() {
		return head_emp_id;
	}

	public void setHead_emp_id(Long head_emp_id) {
		this.head_emp_id = head_emp_id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Long getAcc_emp_id() {
		return acc_emp_id;
	}

	public void setAcc_emp_id(Long acc_emp_id) {
		this.acc_emp_id = acc_emp_id;
	}

	public Long getSafe_emp_id() {
		return safe_emp_id;
	}

	public void setSafe_emp_id(Long safe_emp_id) {
		this.safe_emp_id = safe_emp_id;
	}

	public Long getProc_emp_id() {
		return proc_emp_id;
	}

	public void setProc_emp_id(Long proc_emp_id) {
		this.proc_emp_id = proc_emp_id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getStore_code() {
		return store_code;
	}

	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}

	public String getType_code() {
		return type_code;
	}

	public void setType_code(String type_code) {
		this.type_code = type_code;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getSpell_code() {
		return spell_code;
	}

	public void setSpell_code(String spell_code) {
		this.spell_code = spell_code;
	}

	public String getWbx_code() {
		return wbx_code;
	}

	public void setWbx_code(String wbx_code) {
		this.wbx_code = wbx_code;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getHead_emp_code() {
		return head_emp_code;
	}

	public void setHead_emp_code(String head_emp_code) {
		this.head_emp_code = head_emp_code;
	}

	public String getHead_emp_name() {
		return head_emp_name;
	}

	public void setHead_emp_name(String head_emp_name) {
		this.head_emp_name = head_emp_name;
	}

	public String getAcc_emp_code() {
		return acc_emp_code;
	}

	public void setAcc_emp_code(String acc_emp_code) {
		this.acc_emp_code = acc_emp_code;
	}

	public String getAcc_emp_name() {
		return acc_emp_name;
	}

	public void setAcc_emp_name(String acc_emp_name) {
		this.acc_emp_name = acc_emp_name;
	}

	public String getSafe_emp_code() {
		return safe_emp_code;
	}

	public void setSafe_emp_code(String safe_emp_code) {
		this.safe_emp_code = safe_emp_code;
	}

	public String getSafe_emp_name() {
		return safe_emp_name;
	}

	public void setSafe_emp_name(String safe_emp_name) {
		this.safe_emp_name = safe_emp_name;
	}

	public String getProc_emp_code() {
		return proc_emp_code;
	}

	public void setProc_emp_code(String proc_emp_code) {
		this.proc_emp_code = proc_emp_code;
	}

	public String getProc_emp_name() {
		return proc_emp_name;
	}

	public void setProc_emp_name(String proc_emp_name) {
		this.proc_emp_name = proc_emp_name;
	}

	public Integer getIs_stop() {
		return is_stop;
	}

	public void setIs_stop(Integer is_stop) {
		this.is_stop = is_stop;
	}

	public Long getDept_no() {
		return dept_no;
	}

	public void setDept_no(Long dept_no) {
		this.dept_no = dept_no;
	}
	
	

}