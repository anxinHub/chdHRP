/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.bid;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050401 招标管理
 * @Table:
 * ASS_BID_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssBidMain implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集体ID
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
	 * 招标ID
	 */
	private Long bid_id;
	
	/**
	 * 招标编码
	 */
	private String bid_no;
	
	/**
	 * 招标日期
	 */
	private Date bid_date;
	
	/**
	 * 供应商ID
	 */
	private Long ven_id;
	
	/**
	 * 供应商NO
	 */
	private Long ven_no;
	private String ven_code;
	private String ven_name;
	
	/**
	 * 联系人
	 */
	private String link_man;
	
	/**
	 * 手机号码
	 */
	private String tel_mobile;
	
	/**
	 * 办公电话
	 */
	private String tel_office;
	
	/**
	 * 状态
	 */
	private Integer state;
	
	/**
	 * 制单人
	 */
	private Long create_emp;
	private String create_emp_name;
	/**
	 * 制单日期
	 */
	private Date create_date;
	
	/**
	 * 审核人
	 */
	private Long audit_emp;
	private String audit_emp_name;
	/**
	 * 审核日期
	 */
	private Date audit_date;
	
	/**
	 * 集团招标
	 */
	private Integer is_group;
	
	/**
	 * 备注
	 */
	private String bid_note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	private String file_name;
	
	private Long bid_file_id;
	
	private String file_url;
	
	
	public Long getBid_file_id() {
		return bid_file_id;
	}

	public void setBid_file_id(Long bid_file_id) {
		this.bid_file_id = bid_file_id;
	}

	public String getFile_url() {
		return file_url;
	}

	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	/**
	* 设置 集体ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集体ID
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
	* 设置 招标ID
	* @param value 
	*/
	public void setBid_id(Long value) {
		this.bid_id = value;
	}
	
	/**
	* 获取 招标ID
	* @return Long
	*/
	public Long getBid_id() {
		return this.bid_id;
	}
	/**
	* 设置 招标编码
	* @param value 
	*/
	public void setBid_no(String value) {
		this.bid_no = value;
	}
	
	/**
	* 获取 招标编码
	* @return String
	*/
	public String getBid_no() {
		return this.bid_no;
	}
	/**
	* 设置 招标日期
	* @param value 
	*/
	public void setBid_date(Date value) {
		this.bid_date = value;
	}
	
	/**
	* 获取 招标日期
	* @return Date
	*/
	public Date getBid_date() {
		return this.bid_date;
	}
	/**
	* 设置 供应商ID
	* @param value 
	*/
	public void setVen_id(Long value) {
		this.ven_id = value;
	}
	
	/**
	* 获取 供应商ID
	* @return Long
	*/
	public Long getVen_id() {
		return this.ven_id;
	}
	/**
	* 设置 供应商NO
	* @param value 
	*/
	public void setVen_no(Long value) {
		this.ven_no = value;
	}
	
	/**
	* 获取 供应商NO
	* @return Long
	*/
	public Long getVen_no() {
		return this.ven_no;
	}
	/**
	* 设置 联系人
	* @param value 
	*/
	public void setLink_man(String value) {
		this.link_man = value;
	}
	
	/**
	* 获取 联系人
	* @return String
	*/
	public String getLink_man() {
		return this.link_man;
	}
	/**
	* 设置 手机号码
	* @param value 
	*/
	public void setTel_mobile(String value) {
		this.tel_mobile = value;
	}
	
	/**
	* 获取 手机号码
	* @return String
	*/
	public String getTel_mobile() {
		return this.tel_mobile;
	}
	/**
	* 设置 办公电话
	* @param value 
	*/
	public void setTel_office(String value) {
		this.tel_office = value;
	}
	
	/**
	* 获取 办公电话
	* @return String
	*/
	public String getTel_office() {
		return this.tel_office;
	}
	/**
	* 设置 状态
	* @param value 
	*/
	public void setState(Integer value) {
		this.state = value;
	}
	
	/**
	* 获取 状态
	* @return Integer
	*/
	public Integer getState() {
		return this.state;
	}
	/**
	* 设置 制单人
	* @param value 
	*/
	public void setCreate_emp(Long value) {
		this.create_emp = value;
	}
	
	/**
	* 获取 制单人
	* @return Long
	*/
	public Long getCreate_emp() {
		return this.create_emp;
	}
	/**
	* 设置 制单日期
	* @param value 
	*/
	public void setCreate_date(Date value) {
		this.create_date = value;
	}
	
	/**
	* 获取 制单日期
	* @return Date
	*/
	public Date getCreate_date() {
		return this.create_date;
	}
	/**
	* 设置 审核人
	* @param value 
	*/
	public void setAudit_emp(Long value) {
		this.audit_emp = value;
	}
	
	/**
	* 获取 审核人
	* @return Long
	*/
	public Long getAudit_emp() {
		return this.audit_emp;
	}
	/**
	* 设置 审核日期
	* @param value 
	*/
	public void setAudit_date(Date value) {
		this.audit_date = value;
	}
	
	/**
	* 获取 审核日期
	* @return Date
	*/
	public Date getAudit_date() {
		return this.audit_date;
	}
	/**
	* 设置 集团招标
	* @param value 
	*/
	public void setIs_group(Integer value) {
		this.is_group = value;
	}
	
	/**
	* 获取 集团招标
	* @return Integer
	*/
	public Integer getIs_group() {
		return this.is_group;
	}
	/**
	* 设置 备注
	* @param value 
	*/
	public void setBid_note(String value) {
		this.bid_note = value;
	}
	
	/**
	* 获取 备注
	* @return String
	*/
	public String getBid_note() {
		return this.bid_note;
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

	/**
	 * @return the ven_code
	 */
	public String getVen_code() {
		return ven_code;
	}

	/**
	 * @param ven_code the ven_code to set
	 */
	public void setVen_code(String ven_code) {
		this.ven_code = ven_code;
	}

	/**
	 * @return the ven_name
	 */
	public String getVen_name() {
		return ven_name;
	}

	/**
	 * @param ven_name the ven_name to set
	 */
	public void setVen_name(String ven_name) {
		this.ven_name = ven_name;
	}

	/**
	 * @return the create_emp_name
	 */
	public String getCreate_emp_name() {
		return create_emp_name;
	}

	/**
	 * @param create_emp_name the create_emp_name to set
	 */
	public void setCreate_emp_name(String create_emp_name) {
		this.create_emp_name = create_emp_name;
	}

	/**
	 * @return the audit_emp_name
	 */
	public String getAudit_emp_name() {
		return audit_emp_name;
	}

	/**
	 * @param audit_emp_name the audit_emp_name to set
	 */
	public void setAudit_emp_name(String audit_emp_name) {
		this.audit_emp_name = audit_emp_name;
	}
	
}