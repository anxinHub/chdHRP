/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.entity.medicalmanagement;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 
 * @Table:
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrCertificate implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private Integer group_id;
	
	/**
	 * 
	 */
	private Integer hos_id;
	
	/**
	 * 
	 */
	private String cert_code;
	
	private String cert_name;
	
	private Date begin_date;
	private Date end_date;

	private int inva_day;
	private String state;
  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 
	* @param value 
	*/
	public void setGroup_id(Integer value) {
		this.group_id = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Integer getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setHos_id(Integer value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Integer getHos_id() {
		return this.hos_id;
	}

	/**
	 * @return the cert_code
	 */
	public String getCert_code() {
		return cert_code;
	}

	/**
	 * @param cert_code the cert_code to set
	 */
	public void setCert_code(String cert_code) {
		this.cert_code = cert_code;
	}

	/**
	 * @return the cert_name
	 */
	public String getCert_name() {
		return cert_name;
	}

	/**
	 * @param cert_name the cert_name to set
	 */
	public void setCert_name(String cert_name) {
		this.cert_name = cert_name;
	}

	/**
	 * @return the error_type
	 */
	public String getError_type() {
		return error_type;
	}

	/**
	 * @param error_type the error_type to set
	 */
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	/**
	 * @return the begin_date
	 */
	public Date getBegin_date() {
		return begin_date;
	}

	/**
	 * @param begin_date the begin_date to set
	 */
	public void setBegin_date(Date begin_date) {
		this.begin_date = begin_date;
	}

	/**
	 * @return the end_date
	 */
	public Date getEnd_date() {
		return end_date;
	}

	/**
	 * @param end_date the end_date to set
	 */
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	/**
	 * @return the inva_day
	 */
	public int getInva_day() {
		return inva_day;
	}

	/**
	 * @param inva_day the inva_day to set
	 */
	public void setInva_day(int inva_day) {
		this.inva_day = inva_day;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	
}