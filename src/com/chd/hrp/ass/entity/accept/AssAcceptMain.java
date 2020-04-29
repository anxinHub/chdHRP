/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.accept;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050601 资产验收主表
 * @Table:
 * ASS_ACCEPT_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssAcceptMain implements Serializable {

	
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
	 * 验收ID
	 */
	private Long accept_id;
	
	/**
	 * 验收单号
	 */
	private String accept_no;
	
	/**
	 * 统计年度
	 */
	private String ass_year;
	
	/**
	 * 统计月份
	 */
	private String ass_month;
	
	/**
	 * 摘要
	 */
	private String summary;
	
	/**
	 * 合同ID
	 */
	private String pact_code;
	
	/**
	 * 供应商ID
	 */
	private Long ven_id;
	
	/**
	 * 供应商变更ID
	 */
	private Long ven_no;
	
	/**
	 * 验收科室ID
	 */
	private Long dept_id;
	
	/**
	 * 验收科室NO
	 */
	private Long dept_no;
	
	/**
	 * 验收人
	 */
	private Long accept_emp;
	
	/**
	 * 验收日期
	 */
	private Date accept_date;
	
	/**
	 * 制单人
	 */
	private Long create_emp;
	
	/**
	 * 制单日期
	 */
	private Date create_date;
	
	/**
	 * 审核人
	 */
	private Long audit_emp;
	
	/**
	 * 审核日期
	 */
	private Date audit_date;
	
	/**
	 * 状态
	 */
	private Integer state;
	
	/**
	 * 验收说明
	 */
	private String accept_desc;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	
	private String contract_name;
	
	private String ven_code;
	
	private String ven_name;
	
	private String dept_code;
	
	private String dept_name;
	
	private String accept_emp_name;
	
	private String create_emp_name;
	
	private String audit_emp_name;
	/**
	 * 安装验收明细ID
	 */
	private Long accept_detail_id;
	/**
	 * 资产ID
	 */
	private Long ass_id;
	
	/**
	 * 资产变更NO
	 */
	private Long ass_no;
	
	private String ass_name;
	
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
	 * 验收数量
	 */
	private Integer accept_amount;
	
	/**
	 * 验收结果
	 */
	private Integer is_well;
	
	private String device_code;//设备来源
	private String device_name;
	private String buy_code;//申购类别
	private String buy_name;
	private String source_code;//资金来源
	private String source_name;
	private String ass_naturs;
	private String naturs_name;
	private Integer in_state;
	
	public String getVen_code() {
		return ven_code;
	}

	public void setVen_code(String ven_code) {
		this.ven_code = ven_code;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getContract_name() {
		return contract_name;
	}

	public void setContract_name(String contract_name) {
		this.contract_name = contract_name;
	}

	public String getVen_name() {
		return ven_name;
	}

	public void setVen_name(String ven_name) {
		this.ven_name = ven_name;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getAccept_emp_name() {
		return accept_emp_name;
	}

	public void setAccept_emp_name(String accept_emp_name) {
		this.accept_emp_name = accept_emp_name;
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
	* 设置 验收ID
	* @param value 
	*/
	public void setAccept_id(Long value) {
		this.accept_id = value;
	}
	
	/**
	* 获取 验收ID
	* @return Long
	*/
	public Long getAccept_id() {
		return this.accept_id;
	}
	/**
	* 设置 验收单号
	* @param value 
	*/
	public void setAccept_no(String value) {
		this.accept_no = value;
	}
	
	/**
	* 获取 验收单号
	* @return String
	*/
	public String getAccept_no() {
		return this.accept_no;
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
	* 设置 摘要
	* @param value 
	*/
	public void setSummary(String value) {
		this.summary = value;
	}
	
	/**
	* 获取 摘要
	* @return String
	*/
	public String getSummary() {
		return this.summary;
	}
	
	public String getPact_code() {
		return pact_code;
	}

	public void setPact_code(String pact_code) {
		this.pact_code = pact_code;
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
	* 设置 验收科室ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 验收科室ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 验收科室NO
	* @param value 
	*/
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	
	/**
	* 获取 验收科室NO
	* @return Long
	*/
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	* 设置 验收人
	* @param value 
	*/
	public void setAccept_emp(Long value) {
		this.accept_emp = value;
	}
	
	/**
	* 获取 验收人
	* @return Long
	*/
	public Long getAccept_emp() {
		return this.accept_emp;
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
	* 设置 验收说明
	* @param value 
	*/
	public void setAccept_desc(String value) {
		this.accept_desc = value;
	}
	
	/**
	* 获取 验收说明
	* @return String
	*/
	public String getAccept_desc() {
		return this.accept_desc;
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

	public Date getAudit_date() {
		return audit_date;
	}

	public void setAudit_date(Date audit_date) {
		this.audit_date = audit_date;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Date getAccept_date() {
		return accept_date;
	}

	public void setAccept_date(Date accept_date) {
		this.accept_date = accept_date;
	}

	public Long getAccept_detail_id() {
		return accept_detail_id;
	}

	public void setAccept_detail_id(Long accept_detail_id) {
		this.accept_detail_id = accept_detail_id;
	}

	public Long getAss_id() {
		return ass_id;
	}

	public void setAss_id(Long ass_id) {
		this.ass_id = ass_id;
	}

	public Long getAss_no() {
		return ass_no;
	}

	public void setAss_no(Long ass_no) {
		this.ass_no = ass_no;
	}

	public String getAss_name() {
		return ass_name;
	}

	public void setAss_name(String ass_name) {
		this.ass_name = ass_name;
	}

	public String getAss_model() {
		return ass_model;
	}

	public void setAss_model(String ass_model) {
		this.ass_model = ass_model;
	}

	public String getAss_spec() {
		return ass_spec;
	}

	public void setAss_spec(String ass_spec) {
		this.ass_spec = ass_spec;
	}

	public String getAss_brand() {
		return ass_brand;
	}

	public void setAss_brand(String ass_brand) {
		this.ass_brand = ass_brand;
	}

	public String getFac_id() {
		return fac_id;
	}

	public void setFac_id(String fac_id) {
		this.fac_id = fac_id;
	}

	public String getFac_no() {
		return fac_no;
	}

	public void setFac_no(String fac_no) {
		this.fac_no = fac_no;
	}

	public Integer getAccept_amount() {
		return accept_amount;
	}

	public void setAccept_amount(Integer accept_amount) {
		this.accept_amount = accept_amount;
	}

	public Integer getIs_well() {
		return is_well;
	}

	public void setIs_well(Integer is_well) {
		this.is_well = is_well;
	}

	public String getDevice_code() {
		return device_code;
	}

	public void setDevice_code(String device_code) {
		this.device_code = device_code;
	}

	public String getBuy_code() {
		return buy_code;
	}

	public void setBuy_code(String buy_code) {
		this.buy_code = buy_code;
	}

	public String getSource_code() {
		return source_code;
	}

	public void setSource_code(String source_code) {
		this.source_code = source_code;
	}

	public String getDevice_name() {
		return device_name;
	}

	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}

	public String getBuy_name() {
		return buy_name;
	}

	public void setBuy_name(String buy_name) {
		this.buy_name = buy_name;
	}

	public String getSource_name() {
		return source_name;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

	public String getAss_naturs() {
		return ass_naturs;
	}

	public void setAss_naturs(String ass_naturs) {
		this.ass_naturs = ass_naturs;
	}

	public Integer getIn_state() {
		return in_state;
	}

	public void setIn_state(Integer in_state) {
		this.in_state = in_state;
	}

	public String getNaturs_name() {
		return naturs_name;
	}

	public void setNaturs_name(String naturs_name) {
		this.naturs_name = naturs_name;
	}
	
	
	
}