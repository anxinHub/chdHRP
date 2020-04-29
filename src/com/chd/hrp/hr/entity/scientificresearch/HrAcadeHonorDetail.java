/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.entity.scientificresearch;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_ACADE_HONOR_DETAIL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class HrAcadeHonorDetail implements Serializable {

	
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
	 * 
	 */
	private String apply_no;
	
	/**
	 * DIC_ACADE_HONOR
	 */
	private String honor_code;
	
	/**
	 * 
	 */
	private Long seq_no;
	
	/**
	 * 
	 */
	private String content;
	
	/**
	 * 
	 */
	private String accessory;
	
	/**
	 * 
	 */
	private String note;
	
	private String filepath;
	
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
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
	* 设置 
	* @param value 
	*/
	public void setApply_no(String value) {
		this.apply_no = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getApply_no() {
		return this.apply_no;
	}
	/**
	* 设置 DIC_ACADE_HONOR
	* @param value 
	*/
	public void setHonor_code(String value) {
		this.honor_code = value;
	}
	
	/**
	* 获取 DIC_ACADE_HONOR
	* @return String
	*/
	public String getHonor_code() {
		return this.honor_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setSeq_no(Long value) {
		this.seq_no = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getSeq_no() {
		return this.seq_no;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setContent(String value) {
		this.content = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getContent() {
		return this.content;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setAccessory(String value) {
		this.accessory = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getAccessory() {
		return this.accessory;
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