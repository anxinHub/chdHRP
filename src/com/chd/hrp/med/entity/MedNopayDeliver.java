package com.chd.hrp.med.entity;

import java.io.Serializable;
import java.util.Date;

public class MedNopayDeliver implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long group_id ;
	
	private Long hos_id ;
	
	private String copy_code ;
	
	private Long deliver_id ;
	
	private String deliver_no ;
	
	private String origin_no ;
	
	private String year ;
	
	private String month ;
	
	private Long sup_id ;
	
	private Long sup_no ;
	
	private Long store_id ;
	
	private Long store_no ;
	
	private String brief ;
	
	private Long stocker ;
	
	private String stock_type_code ;
	
	private Long confirmer ;
	
	private String confirm_date ;
	
	private Long maker ;
	
	private Date in_date ;
	
	private Long checker ;
	
	private Date check_date ;
	
	private int state ;
	
	private String protocol_code ;
	
	private Long app_dept ;
	
	private Long proj_id ;
	
	/**
	 * 导入验证信息
	 */
	private String error_type;

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

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public Long getDeliver_id() {
		return deliver_id;
	}

	public void setDeliver_id(Long deliver_id) {
		this.deliver_id = deliver_id;
	}

	public String getDeliver_no() {
		return deliver_no;
	}

	public void setDeliver_no(String deliver_no) {
		this.deliver_no = deliver_no;
	}

	public String getOrigin_no() {
		return origin_no;
	}

	public void setOrigin_no(String origin_no) {
		this.origin_no = origin_no;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Long getSup_id() {
		return sup_id;
	}

	public void setSup_id(Long sup_id) {
		this.sup_id = sup_id;
	}

	public Long getSup_no() {
		return sup_no;
	}

	public void setSup_no(Long sup_no) {
		this.sup_no = sup_no;
	}

	public Long getStore_id() {
		return store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	public Long getStore_no() {
		return store_no;
	}

	public void setStore_no(Long store_no) {
		this.store_no = store_no;
	}

	public String getBrief() {
		return brief;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public Long getStocker() {
		return stocker;
	}

	public void setStocker(Long stocker) {
		this.stocker = stocker;
	}

	public String getStock_type_code() {
		return stock_type_code;
	}

	public void setStock_type_code(String stock_type_code) {
		this.stock_type_code = stock_type_code;
	}

	public Long getConfirmer() {
		return confirmer;
	}

	public void setConfirmer(Long confirmer) {
		this.confirmer = confirmer;
	}

	public String getConfirm_date() {
		return confirm_date;
	}

	public void setConfirm_date(String confirm_date) {
		this.confirm_date = confirm_date;
	}

	public Long getMaker() {
		return maker;
	}

	public void setMaker(Long maker) {
		this.maker = maker;
	}

	public Date getIn_date() {
		return in_date;
	}

	public void setIn_date(Date in_date) {
		this.in_date = in_date;
	}

	public Long getChecker() {
		return checker;
	}

	public void setChecker(Long checker) {
		this.checker = checker;
	}

	public Date getCheck_date() {
		return check_date;
	}

	public void setCheck_date(Date check_date) {
		this.check_date = check_date;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getProtocol_code() {
		return protocol_code;
	}

	public void setProtocol_code(String protocol_code) {
		this.protocol_code = protocol_code;
	}

	public Long getApp_dept() {
		return app_dept;
	}

	public void setApp_dept(Long app_dept) {
		this.app_dept = app_dept;
	}

	public Long getProj_id() {
		return proj_id;
	}

	public void setProj_id(Long proj_id) {
		this.proj_id = proj_id;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
}
