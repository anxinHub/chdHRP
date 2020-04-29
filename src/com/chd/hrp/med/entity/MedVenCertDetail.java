/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * cert_state
1：在用
0：停用



 * @Table:
 * MED_VEN_CERT_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MedVenCertDetail implements Serializable {

	
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
	 * 注册ID
	 */
	private String cert_id;
	
	/**
	 * 证件编号
	 */
	private String cert_code;
	
	/**
	 * 证件类型ID
	 */
	private Long type_id;
	
	/**
	 * 供应商ID
	 */
	private Long sup_id;
	
	private String sup_no;
	
	private String sup_code;
	
	private String sup_name;
	
	private String type_code;
	
	private String type_name;
	
	/**
	 * 发证机关
	 */
	private String issuing_authority;
	
	/**
	 * 起始日期
	 */
	private Date start_date;
	
	/**
	 * 截止日期
	 */
	private Date end_date;
	
	/**
	 * 证件描述
	 */
	private String cert_memo;
	
	/**
	 * 文档路径
	 */
	private String file_path;
	
	/**
	 * 是否停用
	 */
	private Integer cert_state;
	
	/**
	 * 有效期类型
	 */
	private Integer validity_type;

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
	* 设置 账套编码
	* @param value 
	*/
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 账套编码
	* @return String
	*/
	public String getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 注册ID
	* @param value 
	*/
	public void setCert_id(String value) {
		this.cert_id = value;
	}
	
	/**
	* 获取 注册ID
	* @return String
	*/
	public String getCert_id() {
		return this.cert_id;
	}
	/**
	* 设置 证件编号
	* @param value 
	*/
	public void setCert_code(String value) {
		this.cert_code = value;
	}
	
	/**
	* 获取 证件编号
	* @return String
	*/
	public String getCert_code() {
		return this.cert_code;
	}
	/**
	* 设置 证件类型ID
	* @param value 
	*/
	public void setType_id(Long value) {
		this.type_id = value;
	}
	
	/**
	* 获取 证件类型ID
	* @return Long
	*/
	public Long getType_id() {
		return this.type_id;
	}
	/**
	* 设置 供应商ID
	* @param value 
	*/
	public void setSup_id(Long value) {
		this.sup_id = value;
	}
	
	/**
	* 获取 供应商ID
	* @return Long
	*/
	public Long getSup_id() {
		return this.sup_id;
	}
	
	public String getSup_no() {
		return sup_no;
	}

	public void setSup_no(String sup_no) {
		this.sup_no = sup_no;
	}

	public String getSup_code() {
		return sup_code;
	}

	public void setSup_code(String sup_code) {
		this.sup_code = sup_code;
	}

	public String getSup_name() {
		return sup_name;
	}

	public void setSup_name(String sup_name) {
		this.sup_name = sup_name;
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

	/**
	* 设置 发证机关
	* @param value 
	*/
	public void setIssuing_authority(String value) {
		this.issuing_authority = value;
	}
	
	/**
	* 获取 发证机关
	* @return String
	*/
	public String getIssuing_authority() {
		return this.issuing_authority;
	}
	/**
	* 设置 起始日期
	* @param value 
	*/
	public void setStart_date(Date value) {
		this.start_date = value;
	}
	
	/**
	* 获取 起始日期
	* @return Date
	*/
	public Date getStart_date() {
		return this.start_date;
	}
	/**
	* 设置 截止日期
	* @param value 
	*/
	public void setEnd_date(Date value) {
		this.end_date = value;
	}
	
	/**
	* 获取 截止日期
	* @return Date
	*/
	public Date getEnd_date() {
		return this.end_date;
	}
	/**
	* 设置 证件描述
	* @param value 
	*/
	public void setCert_memo(String value) {
		this.cert_memo = value;
	}
	
	/**
	* 获取 证件描述
	* @return String
	*/
	public String getCert_memo() {
		return this.cert_memo;
	}
	/**
	* 设置 文档路径
	* @param value 
	*/
	public void setFile_path(String value) {
		this.file_path = value;
	}
	
	/**
	* 获取 文档路径
	* @return String
	*/
	public String getFile_path() {
		return this.file_path;
	}
	/**
	* 设置 是否停用
	* @param value 
	*/
	public void setCert_state(Integer value) {
		this.cert_state = value;
	}
	
	/**
	* 获取 是否停用
	* @return Integer
	*/
	public Integer getCert_state() {
		return this.cert_state;
	}
	
	
	public Integer getValidity_type() {
		return validity_type;
	}

	public void setValidity_type(Integer validity_type) {
		this.validity_type = validity_type;
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