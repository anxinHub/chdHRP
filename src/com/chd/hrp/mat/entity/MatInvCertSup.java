
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
 * MAT_INV_CERT
 * @Table:
 * MAT_INV_CERT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MatInvCertSup implements Serializable {

	
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
	 * 证件ID
	 */
	private Long sup_cert_id;
	
	/**
	 * 证件编号
	 */
	private String sup_cert_code;
	
	/**
	 * 证件产品名称
	 */
	private String sup_cert_inv_name;
	
	/**
	 * 证件类型ID
	 */
	private Long type_id;
	
	private String type_code;
	
	private String type_name;
	
	/**
	 * 生产厂商
	 */
	private Long fac_id;
	
	private String fac_code;
	
	private String fac_name;
	
	/**
	 * 发证日期
	 */
	private Date cert_date;
	
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
	 * 联系人
	 */
	private String link_person;
	
	/**
	 * 联系电话
	 */
	private String link_tel;
	
	/**
	 * 手机
	 */
	private String link_mobile;
	
	/**
	 * 证件描述
	 */
	private String cert_memo;
	
	/**
	 * 文档路径
	 */
	private String file_path;
	
	/**
	 * 存档位置
	 */
	private String file_address;
	
	/**
	 * 是否停用
	 */
	private Integer cert_state;
	
	/**
	 * 供应商名称
	 */
	private String sup_name;
	/**
	 * 状态
	 */
	private Integer state;
	
	private Long stop_start;
	private Date queryDate;

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
	* 设置 生产厂商
	* @param value 
	*/
	public void setFac_id(Long value) {
		this.fac_id = value;
	}
	
	/**
	* 获取 生产厂商
	* @return Long
	*/
	public Long getFac_id() {
		return this.fac_id;
	}
	/**
	* 设置 发证日期
	* @param value 
	*/
	public void setCert_date(Date value) {
		this.cert_date = value;
	}
	
	/**
	* 获取 发证日期
	* @return Date
	*/
	public Date getCert_date() {
		return this.cert_date;
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
	* 设置 联系人
	* @param value 
	*/
	public void setLink_person(String value) {
		this.link_person = value;
	}
	
	/**
	* 获取 联系人
	* @return String
	*/
	public String getLink_person() {
		return this.link_person;
	}
	/**
	* 设置 联系电话
	* @param value 
	*/
	public void setLink_tel(String value) {
		this.link_tel = value;
	}
	
	/**
	* 获取 联系电话
	* @return String
	*/
	public String getLink_tel() {
		return this.link_tel;
	}
	/**
	* 设置 手机
	* @param value 
	*/
	public void setLink_mobile(String value) {
		this.link_mobile = value;
	}
	
	/**
	* 获取 手机
	* @return String
	*/
	public String getLink_mobile() {
		return this.link_mobile;
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
	* 设置 存档位置
	* @param value 
	*/
	public void setFile_address(String value) {
		this.file_address = value;
	}
	
	/**
	* 获取 存档位置
	* @return String
	*/
	public String getFile_address() {
		return this.file_address;
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
	
	public String getFac_code() {
		return fac_code;
	}

	public void setFac_code(String fac_code) {
		this.fac_code = fac_code;
	}

	public String getFac_name() {
		return fac_name;
	}

	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
	}

	public String getSup_name() {
		return sup_name;
	}

	public void setSup_name(String sup_name) {
		this.sup_name = sup_name;
	}

	/**
	 * @return the state
	 */
	public Integer getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(Integer state) {
		this.state = state;
	}

	/**
	 * @return the stop_start
	 */
	public Long getStop_start() {
		return stop_start;
	}

	/**
	 * @param stop_start the stop_start to set
	 */
	public void setStop_start(Long stop_start) {
		this.stop_start = stop_start;
	}

	/**
	 * @return the queryDate
	 */
	public Date getQueryDate() {
		return queryDate;
	}

	/**
	 * @param queryDate the queryDate to set
	 */
	public void setQueryDate(Date queryDate) {
		this.queryDate = queryDate;
	}

	/**
	 * @return the sup_cert_id
	 */
	public Long getSup_cert_id() {
		return sup_cert_id;
	}

	/**
	 * @param sup_cert_id the sup_cert_id to set
	 */
	public void setSup_cert_id(Long sup_cert_id) {
		this.sup_cert_id = sup_cert_id;
	}

	/**
	 * @return the sup_cert_code
	 */
	public String getSup_cert_code() {
		return sup_cert_code;
	}

	/**
	 * @param sup_cert_code the sup_cert_code to set
	 */
	public void setSup_cert_code(String sup_cert_code) {
		this.sup_cert_code = sup_cert_code;
	}

	/**
	 * @return the sup_cert_inv_name
	 */
	public String getSup_cert_inv_name() {
		return sup_cert_inv_name;
	}

	/**
	 * @param sup_cert_inv_name the sup_cert_inv_name to set
	 */
	public void setSup_cert_inv_name(String sup_cert_inv_name) {
		this.sup_cert_inv_name = sup_cert_inv_name;
	}
	
}