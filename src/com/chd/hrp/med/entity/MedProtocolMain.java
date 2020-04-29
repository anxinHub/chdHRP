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
 * state：
0：新建
1：审核
2：确认

 * @Table:
 * MED_PROTOCOL_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MedProtocolMain implements Serializable {

	
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
	 * 协议ID
	 */
	private Long protocol_id;
	
	/**
	 * 协议编号
	 */
	private String protocol_code;
	
	/**
	 * 原始编号
	 */
	private String original_no;
	
	/**
	 * 类别ID
	 */
	private Long type_id;
	
	/**
	 * 协议名称
	 */
	private String protocol_name;
	
	/**
	 * 签订日期
	 */
	private Date sign_date;
	
	/**
	 * 供应商变更ID
	 */
	private Long sup_no;
	
	/**
	 * 供应商ID
	 */
	private Long sup_id;
	
	/**
	 * 是否招标
	 */
	private Integer is_bid;
	
	/**
	 * 开始日期
	 */
	private Date start_date;
	
	/**
	 * 截止日期
	 */
	private Date end_date;
	
	/**
	 * 签订科室变更ID
	 */
	private Long dept_no;
	
	/**
	 * 签订科室ID
	 */
	private Long dept_id;
	
	/**
	 * 存放位置
	 */
	private String addr;
	
	/**
	 * 甲方负责人
	 */
	private String first_man;
	
	/**
	 * 乙方负责人
	 */
	private String second_man;
	
	/**
	 * 乙方电话
	 */
	private String second_phone;
	
	/**
	 * 协议简介
	 */
	private String contract_detail;
	
	/**
	 * 售后服务
	 */
	private String service_detail;
	
	/**
	 * 制单人
	 */
	private Long create_emp;
	
	/**
	 * 审核人
	 */
	private Long check_emp;
	
	/**
	 * 确认人
	 */
	private Long confirm_emp;
	
	/**
	 * 状态
	 */
	private Integer state;
	
	/**
	 * 拼音码
	 */
	private String spell;
	
	/**
	 * 是否期初
	 */
	private Integer is_init;
	

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
	* 设置 协议ID
	* @param value 
	*/
	public void setProtocol_id(Long value) {
		this.protocol_id = value;
	}
	
	/**
	* 获取 协议ID
	* @return Long
	*/
	public Long getProtocol_id() {
		return this.protocol_id;
	}
	/**
	* 设置 协议编号
	* @param value 
	*/
	public void setProtocol_code(String value) {
		this.protocol_code = value;
	}
	
	/**
	* 获取 协议编号
	* @return String
	*/
	public String getProtocol_code() {
		return this.protocol_code;
	}
	/**
	* 设置 原始编号
	* @param value 
	*/
	public void setOriginal_no(String value) {
		this.original_no = value;
	}
	
	/**
	* 获取 原始编号
	* @return String
	*/
	public String getOriginal_no() {
		return this.original_no;
	}
	/**
	* 设置 类别ID
	* @param value 
	*/
	public void setType_id(Long value) {
		this.type_id = value;
	}
	
	/**
	* 获取 类别ID
	* @return Long
	*/
	public Long getType_id() {
		return this.type_id;
	}
	/**
	* 设置 协议名称
	* @param value 
	*/
	public void setProtocol_name(String value) {
		this.protocol_name = value;
	}
	
	/**
	* 获取 协议名称
	* @return String
	*/
	public String getProtocol_name() {
		return this.protocol_name;
	}
	/**
	* 设置 签订日期
	* @param value 
	*/
	public void setSign_date(Date value) {
		this.sign_date = value;
	}
	
	/**
	* 获取 签订日期
	* @return Date
	*/
	public Date getSign_date() {
		return this.sign_date;
	}
	/**
	* 设置 供应商变更ID
	* @param value 
	*/
	public void setSup_no(Long value) {
		this.sup_no = value;
	}
	
	/**
	* 获取 供应商变更ID
	* @return Long
	*/
	public Long getSup_no() {
		return this.sup_no;
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
	/**
	* 设置 是否招标
	* @param value 
	*/
	public void setIs_bid(Integer value) {
		this.is_bid = value;
	}
	
	/**
	* 获取 是否招标
	* @return Integer
	*/
	public Integer getIs_bid() {
		return this.is_bid;
	}
	/**
	* 设置 开始日期
	* @param value 
	*/
	public void setStart_date(Date value) {
		this.start_date = value;
	}
	
	/**
	* 获取 开始日期
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
	* 设置 签订科室变更ID
	* @param value 
	*/
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	
	/**
	* 获取 签订科室变更ID
	* @return Long
	*/
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	* 设置 签订科室ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 签订科室ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 存放位置
	* @param value 
	*/
	public void setAddr(String value) {
		this.addr = value;
	}
	
	/**
	* 获取 存放位置
	* @return String
	*/
	public String getAddr() {
		return this.addr;
	}
	/**
	* 设置 甲方负责人
	* @param value 
	*/
	public void setFirst_man(String value) {
		this.first_man = value;
	}
	
	/**
	* 获取 甲方负责人
	* @return String
	*/
	public String getFirst_man() {
		return this.first_man;
	}
	/**
	* 设置 乙方负责人
	* @param value 
	*/
	public void setSecond_man(String value) {
		this.second_man = value;
	}
	
	/**
	* 获取 乙方负责人
	* @return String
	*/
	public String getSecond_man() {
		return this.second_man;
	}
	/**
	* 设置 乙方电话
	* @param value 
	*/
	public void setSecond_phone(String value) {
		this.second_phone = value;
	}
	
	/**
	* 获取 乙方电话
	* @return String
	*/
	public String getSecond_phone() {
		return this.second_phone;
	}
	/**
	* 设置 协议简介
	* @param value 
	*/
	public void setContract_detail(String value) {
		this.contract_detail = value;
	}
	
	/**
	* 获取 协议简介
	* @return String
	*/
	public String getContract_detail() {
		return this.contract_detail;
	}
	/**
	* 设置 售后服务
	* @param value 
	*/
	public void setService_detail(String value) {
		this.service_detail = value;
	}
	
	/**
	* 获取 售后服务
	* @return String
	*/
	public String getService_detail() {
		return this.service_detail;
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
	* 设置 审核人
	* @param value 
	*/
	public void setCheck_emp(Long value) {
		this.check_emp = value;
	}
	
	/**
	* 获取 审核人
	* @return Long
	*/
	public Long getCheck_emp() {
		return this.check_emp;
	}
	/**
	* 设置 确认人
	* @param value 
	*/
	public void setConfirm_emp(Long value) {
		this.confirm_emp = value;
	}
	
	/**
	* 获取 确认人
	* @return Long
	*/
	public Long getConfirm_emp() {
		return this.confirm_emp;
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
	* 设置 拼音码
	* @param value 
	*/
	public void setSpell(String value) {
		this.spell = value;
	}
	
	/**
	* 获取 拼音码
	* @return String
	*/
	public String getSpell() {
		return this.spell;
	}
	/**
	* 设置 是否期初
	* @param value 
	*/
	public void setIs_init(Integer value) {
		this.is_init = value;
	}
	
	/**
	* 获取 是否期初
	* @return Integer
	*/
	public Integer getIs_init() {
		return this.is_init;
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