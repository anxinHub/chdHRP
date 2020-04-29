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
 * HR_DRUG_PERM_DETAIL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrDrugPermDetail implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private Double group_id;
	
	/**
	 * 
	 */
	private Double hos_id;
	
	/**
	 * 
	 */
	private Double emp_id;
	
	/**
	 * DIC_PERM_TYPE
	 */
	private String perm_type;
	
	/**
	 * 
	 */
	private Double seq_no;
	
	/**
	 * 
	 */
	private Long med_id;
	private String inv_name;
	
	/**
	 * 
	 */
	private Date get_date;
	
	/**
	 * 
	 */
	private Date stop_date;
	
	/**
	 * 
	 */
	private Date apply_date;
	
	/**
	 * 0:新建 1:提交
	 */
	private Integer state;
	
	/**
	 * 
	 */
	private String note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 
	* @param value 
	*/
	public void setGroup_id(Double value) {
		this.group_id = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setHos_id(Double value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setEmp_id(Double value) {
		this.emp_id = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getEmp_id() {
		return this.emp_id;
	}
	/**
	* 设置 DIC_PERM_TYPE
	* @param value 
	*/
	public void setPerm_type(String value) {
		this.perm_type = value;
	}
	
	/**
	* 获取 DIC_PERM_TYPE
	* @return String
	*/
	public String getPerm_type() {
		return this.perm_type;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setSeq_no(Double value) {
		this.seq_no = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getSeq_no() {
		return this.seq_no;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setMed_id(Long value) {
		this.med_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getMed_id() {
		return this.med_id;
	}
	
	public String getInv_name() {
		return inv_name;
	}

	public void setInv_name(String inv_name) {
		this.inv_name = inv_name;
	}

	/**
	* 设置 
	* @param value 
	*/
	public void setGet_date(Date value) {
		this.get_date = value;
	}
	
	/**
	* 获取 
	* @return Date
	*/
	public Date getGet_date() {
		return this.get_date;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setStop_date(Date value) {
		this.stop_date = value;
	}
	
	/**
	* 获取 
	* @return Date
	*/
	public Date getStop_date() {
		return this.stop_date;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setApply_date(Date value) {
		this.apply_date = value;
	}
	
	/**
	* 获取 
	* @return Date
	*/
	public Date getApply_date() {
		return this.apply_date;
	}
	/**
	* 设置 0:新建 1:提交
	* @param value 
	*/
	public void setState(Integer value) {
		this.state = value;
	}
	
	/**
	* 获取 0:新建 1:提交
	* @return Integer
	*/
	public Integer getState() {
		return this.state;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 
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
}