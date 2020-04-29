/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.ins;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050601 资产安装主表
 * @Table:
 * ASS_INS_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssInsMain implements Serializable {

	
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
	 * 安装ID
	 */
	private Long ins_id;
	
	/**
	 * 安装单号
	 */
	private String ins_no;
	/**
	 * 安装明细ID
	 */
	private Long ins_detail_id;
	/**
	 * 安装数量
	 */
	private Integer ins_amount;
	
	/**
	 * 安装费用
	 */
	private Double ins_money;
	/**
	 * 统计年度
	 */
	private String ass_year;
	
	/**
	 * 统计月份
	 */
	private String ass_month;
	
	/**
	 * 安装日期
	 */
	private Date ins_date;
	
	/**
	 * 合同ID
	 */
	private Long pact_code;
	
	/**
	 * 供应商ID
	 */
	private Long ven_id;
	
	/**
	 * 供应商变更ID
	 */
	private Long ven_no;
	private String sup_code;
	private String sup_name;
	
	/**
	 * 安装科室ID
	 */
	private Long dept_id;
	
	/**
	 * 安装科室NO
	 */
	private Long dept_no;
	private String dept_code ;
	private String dept_name;
	/**
	 * 安装说明
	 */
	private String accept_desc;
	
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
	 * 状态
	 */
	private Integer state;
	private String state_name;
	/**
	 * 用户
	*/
	private String user_name;
	/**
	 * 资产ID
	 */
	private Long ass_id;
	
	/**
	 * 资产变更NO
	 */
	private Long ass_no;
	
	/**
	 * 型号
	 */
	private String ass_model;
	
	/**
	 * 规格
	 */
	private String ass_spec;
	
	/**
	 * 品牌
	 */
	private String ass_brand;
	
	/**
	 * 生产厂商ID
	 */
	private String fac_id;
	
	/**
	 * 生产厂家变更ID
	 */
	private String fac_no;
	/**
	 * 安装说明
	 */
	private String ins_desc;
  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	

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
	* 设置 安装ID
	* @param value 
	*/
	public void setIns_id(Long value) {
		this.ins_id = value;
	}
	
	/**
	* 获取 安装ID
	* @return Long
	*/
	public Long getIns_id() {
		return this.ins_id;
	}
	/**
	* 设置 安装单号
	* @param value 
	*/
	public void setIns_no(String value) {
		this.ins_no = value;
	}
	
	/**
	* 获取 安装单号
	* @return String
	*/
	public String getIns_no() {
		return this.ins_no;
	}
	/**
	* 设置 统计年度
	* @param value 
	*/
	public void setAss_year(String value) {
		this.ass_year = value;
	}
	
	/**
	* 获取 统计年度
	* @return String
	*/
	public String getAss_year() {
		return this.ass_year;
	}
	/**
	* 设置 统计月份
	* @param value 
	*/
	public void setAss_month(String value) {
		this.ass_month = value;
	}
	
	/**
	* 获取 统计月份
	* @return String
	*/
	public String getAss_month() {
		return this.ass_month;
	}
	/**
	* 设置 安装日期
	* @param value 
	*/
	public void setIns_date(Date value) {
		this.ins_date = value;
	}
	
	/**
	* 获取 安装日期
	* @return Date
	*/
	public Date getIns_date() {
		return this.ins_date;
	}
	
	/**
	* 设置 供应商ID
	* @param value 
	*/
	public void setVen_id(Long value) {
		this.ven_id = value;
	}
	

	public String getSup_code() {
		return sup_code;
	}

	public void setSup_code(String sup_code) {
		this.sup_code = sup_code;
	}

	/**
	* 获取 供应商ID
	* @return Long
	*/
	public Long getVen_id() {
		return this.ven_id;
	}
	/**
	* 设置 供应商变更ID
	* @param value 
	*/
	public void setVen_no(Long value) {
		this.ven_no = value;
	}
	
	/**
	* 获取 供应商变更ID
	* @return Long
	*/
	public Long getVen_no() {
		return this.ven_no;
	}
	/**
	* 设置 安装科室ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 安装科室ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 安装科室NO
	* @param value 
	*/
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	
	/**
	* 获取 安装科室NO
	* @return Long
	*/
	public Long getDept_no() {
		return this.dept_no;
	}
	
	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	/**
	* 设置 安装说明
	* @param value 
	*/
	public void setAccept_desc(String value) {
		this.accept_desc = value;
	}
	
	/**
	* 获取 安装说明
	* @return String
	*/
	public String getAccept_desc() {
		return this.accept_desc;
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
	
	
	
	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
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

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getCreate_emp_name() {
		return create_emp_name;
	}

	public void setCreate_emp_name(String create_emp_name) {
		this.create_emp_name = create_emp_name;
	}

	public String getAudit_emp_name() {
		return audit_emp_name;
	}

	public void setAudit_emp_name(String audit_emp_name) {
		this.audit_emp_name = audit_emp_name;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getSup_name() {
		return sup_name;
	}

	public void setSup_name(String sup_name) {
		this.sup_name = sup_name;
	}


	public Long getPact_code() {
		return pact_code;
	}

	public void setPact_code(Long pact_code) {
		this.pact_code = pact_code;
	}

	public Long getIns_detail_id() {
		return ins_detail_id;
	}

	public void setIns_detail_id(Long ins_detail_id) {
		this.ins_detail_id = ins_detail_id;
	}

	public Integer getIns_amount() {
		return ins_amount;
	}

	public void setIns_amount(Integer ins_amount) {
		this.ins_amount = ins_amount;
	}

	public Double getIns_money() {
		return ins_money;
	}

	public void setIns_money(Double ins_money) {
		this.ins_money = ins_money;
	}

	public String getIns_desc() {
		return ins_desc;
	}

	public void setIns_desc(String ins_desc) {
		this.ins_desc = ins_desc;
	}

	public String getFac_no() {
		return fac_no;
	}

	public void setFac_no(String fac_no) {
		this.fac_no = fac_no;
	}

	public String getFac_id() {
		return fac_id;
	}

	public void setFac_id(String fac_id) {
		this.fac_id = fac_id;
	}

	public String getAss_brand() {
		return ass_brand;
	}

	public void setAss_brand(String ass_brand) {
		this.ass_brand = ass_brand;
	}

	public String getAss_spec() {
		return ass_spec;
	}

	public void setAss_spec(String ass_spec) {
		this.ass_spec = ass_spec;
	}

	public String getAss_model() {
		return ass_model;
	}

	public void setAss_model(String ass_model) {
		this.ass_model = ass_model;
	}

	public Long getAss_no() {
		return ass_no;
	}

	public void setAss_no(Long ass_no) {
		this.ass_no = ass_no;
	}

	public Long getAss_id() {
		return ass_id;
	}

	public void setAss_id(Long ass_id) {
		this.ass_id = ass_id;
	}
}