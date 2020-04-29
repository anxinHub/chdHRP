/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.measure;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 051204 检测计量记录
 * @Table:
 * ASS_MEASURE_REC
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssMeasureRec implements Serializable {

	
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
	 * 计量记录ID
	 */
	private Long rec_id;
	
	/**
	 * 计量记录编号
	 */
	private String seq_no;
	
	/**
	 * 计划ID
	 */
	private Long plan_id;
	private String plan_name ;
	
	/**
	 * 计量证书号
	 */
	private String cert_no;
	
	/**
	 * 资产性质
	 */
	private String ass_nature;
	private String naturs_name;
	
	/**
	 * 外部计量员
	 */
	private String outer_measure_engr;
	private String outer_measure_engr_name;
	
	/**
	 * 内部计量员
	 */
	private String inner_measure_emp;
	private String inner_measure_emp_name;
	
	/**
	 * 计划计量日期
	 */
	private Date plan_measure_date;
	
	/**
	 * 实际计量日期
	 */
	private Date fact_measure_date;
	
	/**
	 * 预计下次日期
	 */
	private Date pre_next_date;
	
	/**
	 * 计量工时
	 */
	private Double measure_hours;
	
	/**
	 * 其他费用
	 */
	private Double other_money;
	
	/**
	 * 计量费用
	 */
	private Double measure_money;
	
	/**
	 * 计量说明
	 */
	private String measure_memo;
	
	/**
	 * 检测方式
	 */
	private Integer measure_kind;
	
	/**
	 * 内部计量部门ID
	 */
	private Long inner_measure_dept_id;
	
	/**
	 * 内部计量部门NO
	 */
	private Long inner_measure_dept_no;
	
	private String inner_measure_dept_name;
	
	/**
	 * 外部计量单位
	 */
	private String outer_measure_org;
	
	/**
	 * 制单人
	 */
	private String create_emp;
	private String create_emp_name ;
	
	/**
	 * 制单时间
	 */
	private Date create_date;
	
	/**
	 * 审核人
	 */
	private String audit_emp;
	private String audit_emp_name;
	
	/**
	 * 审核时间
	 */
	private Date audit_date;
	
	/**
	 * 经办人
	 */
	private String deal_emp;
	private String deal_emp_name ;
	
	/**
	 * 计量类别
	 */
	private Integer measure_type;
	
	/**
	 * 状态
	 */
	private Integer state;
	
	/**
	 * 计量结果
	 */
	private Integer measure_result;
	
	/**
	 * 处理意见
	 */
	private String measure_idea;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	private String entrust_no;
	
	private String firm_basis;
	
	
	
	public String getEntrust_no() {
		return entrust_no;
	}

	public void setEntrust_no(String entrust_no) {
		this.entrust_no = entrust_no;
	}

	public String getFirm_basis() {
		return firm_basis;
	}

	public void setFirm_basis(String firm_basis) {
		this.firm_basis = firm_basis;
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
	* 设置 计量记录ID
	* @param value 
	*/
	public void setRec_id(Long value) {
		this.rec_id = value;
	}
	
	/**
	* 获取 计量记录ID
	* @return Long
	*/
	public Long getRec_id() {
		return this.rec_id;
	}
	/**
	* 设置 计量记录编号
	* @param value 
	*/
	public String getSeq_no() {
		return seq_no;
	}

	public void setSeq_no(String seq_no) {
		this.seq_no = seq_no;
	}
	
	/**
	* 设置 计划ID
	* @param value 
	*/
	public void setPlan_id(Long value) {
		this.plan_id = value;
	}
	
	/**
	* 获取 计划ID
	* @return Long
	*/
	public Long getPlan_id() {
		return this.plan_id;
	}
	/**
	* 设置 计量证书号
	* @param value 
	*/
	public void setCert_no(String value) {
		this.cert_no = value;
	}
	
	/**
	* 获取 计量证书号
	* @return String
	*/
	public String getCert_no() {
		return this.cert_no;
	}
	/**
	* 设置 资产性质
	* @param value 
	*/
	public void setAss_nature(String value) {
		this.ass_nature = value;
	}
	
	/**
	* 获取 资产性质
	* @return String
	*/
	public String getAss_nature() {
		return this.ass_nature;
	}
	/**
	* 设置 外部计量员
	* @param value 
	*/
	public void setOuter_measure_engr(String value) {
		this.outer_measure_engr = value;
	}
	
	/**
	* 获取 外部计量员
	* @return String
	*/
	public String getOuter_measure_engr() {
		return this.outer_measure_engr;
	}
	/**
	* 设置 内部计量员
	* @param value 
	*/
	public void setInner_measure_emp(String value) {
		this.inner_measure_emp = value;
	}
	
	/**
	* 获取 内部计量员
	* @return String
	*/
	public String getInner_measure_emp() {
		return this.inner_measure_emp;
	}
	/**
	* 设置 计划计量日期
	* @param value 
	*/
	public void setPlan_measure_date(Date value) {
		this.plan_measure_date = value;
	}
	
	/**
	* 获取 计划计量日期
	* @return Date
	*/
	public Date getPlan_measure_date() {
		return this.plan_measure_date;
	}
	/**
	* 设置 实际计量日期
	* @param value 
	*/
	public void setFact_measure_date(Date value) {
		this.fact_measure_date = value;
	}
	
	/**
	* 获取 实际计量日期
	* @return Date
	*/
	public Date getFact_measure_date() {
		return this.fact_measure_date;
	}
	/**
	* 设置 预计下次日期
	* @param value 
	*/
	public void setPre_next_date(Date value) {
		this.pre_next_date = value;
	}
	
	/**
	* 获取 预计下次日期
	* @return Date
	*/
	public Date getPre_next_date() {
		return this.pre_next_date;
	}
	/**
	* 设置 计量工时
	* @param value 
	*/
	public void setMeasure_hours(Double value) {
		this.measure_hours = value;
	}
	
	/**
	* 获取 计量工时
	* @return Double
	*/
	public Double getMeasure_hours() {
		return this.measure_hours;
	}
	/**
	* 设置 其他费用
	* @param value 
	*/
	public void setOther_money(Double value) {
		this.other_money = value;
	}
	
	/**
	* 获取 其他费用
	* @return Double
	*/
	public Double getOther_money() {
		return this.other_money;
	}
	/**
	* 设置 计量费用
	* @param value 
	*/
	public void setMeasure_money(Double value) {
		this.measure_money = value;
	}
	
	/**
	* 获取 计量费用
	* @return Double
	*/
	public Double getMeasure_money() {
		return this.measure_money;
	}
	/**
	* 设置 计量说明
	* @param value 
	*/
	public void setMeasure_memo(String value) {
		this.measure_memo = value;
	}
	
	/**
	* 获取 计量说明
	* @return String
	*/
	public String getMeasure_memo() {
		return this.measure_memo;
	}
	/**
	* 设置 检测方式
	* @param value 
	*/
	public void setMeasure_kind(Integer value) {
		this.measure_kind = value;
	}
	
	/**
	* 获取 检测方式
	* @return Integer
	*/
	public Integer getMeasure_kind() {
		return this.measure_kind;
	}
	/**
	* 设置 内部计量部门ID
	* @param value 
	*/
	public void setInner_measure_dept_id(Long value) {
		this.inner_measure_dept_id = value;
	}
	
	/**
	* 获取 内部计量部门ID
	* @return Long
	*/
	public Long getInner_measure_dept_id() {
		return this.inner_measure_dept_id;
	}
	/**
	* 设置 内部计量部门NO
	* @param value 
	*/
	public void setInner_measure_dept_no(Long value) {
		this.inner_measure_dept_no = value;
	}
	
	/**
	* 获取 内部计量部门NO
	* @return Long
	*/
	public Long getInner_measure_dept_no() {
		return this.inner_measure_dept_no;
	}
	/**
	* 设置 外部计量单位
	* @param value 
	*/
	public void setOuter_measure_org(String value) {
		this.outer_measure_org = value;
	}
	
	/**
	* 获取 外部计量单位
	* @return String
	*/
	public String getOuter_measure_org() {
		return this.outer_measure_org;
	}
	/**
	* 设置 制单人
	* @param value 
	*/
	public void setCreate_emp(String value) {
		this.create_emp = value;
	}
	
	/**
	* 获取 制单人
	* @return String
	*/
	public String getCreate_emp() {
		return this.create_emp;
	}
	/**
	* 设置 制单时间
	* @param value 
	*/
	public void setCreate_date(Date value) {
		this.create_date = value;
	}
	
	/**
	* 获取 制单时间
	* @return Date
	*/
	public Date getCreate_date() {
		return this.create_date;
	}
	/**
	* 设置 审核人
	* @param value 
	*/
	public void setAudit_emp(String value) {
		this.audit_emp = value;
	}
	
	/**
	* 获取 审核人
	* @return String
	*/
	public String getAudit_emp() {
		return this.audit_emp;
	}
	/**
	* 设置 审核时间
	* @param value 
	*/
	public void setAudit_date(Date value) {
		this.audit_date = value;
	}
	
	/**
	* 获取 审核时间
	* @return Date
	*/
	public Date getAudit_date() {
		return this.audit_date;
	}
	/**
	* 设置 经办人
	* @param value 
	*/
	public void setDeal_emp(String value) {
		this.deal_emp = value;
	}
	
	/**
	* 获取 经办人
	* @return String
	*/
	public String getDeal_emp() {
		return this.deal_emp;
	}
	/**
	* 设置 计量类别
	* @param value 
	*/
	public void setMeasure_type(Integer value) {
		this.measure_type = value;
	}
	
	/**
	* 获取 计量类别
	* @return Integer
	*/
	public Integer getMeasure_type() {
		return this.measure_type;
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
	* 设置 计量结果
	* @param value 
	*/
	public void setMeasure_result(Integer value) {
		this.measure_result = value;
	}
	
	/**
	* 获取 计量结果
	* @return Integer
	*/
	public Integer getMeasure_result() {
		return this.measure_result;
	}
	/**
	* 设置 处理意见
	* @param value 
	*/
	public void setMeasure_idea(String value) {
		this.measure_idea = value;
	}
	
	/**
	* 获取 处理意见
	* @return String
	*/
	public String getMeasure_idea() {
		return this.measure_idea;
	}
	
	
	
	public String getPlan_name() {
		return plan_name;
	}

	public void setPlan_name(String plan_name) {
		this.plan_name = plan_name;
	}

	public String getNaturs_name() {
		return naturs_name;
	}

	public void setNaturs_name(String naturs_name) {
		this.naturs_name = naturs_name;
	}

	public String getOuter_measure_engr_name() {
		return outer_measure_engr_name;
	}

	public void setOuter_measure_engr_name(String outer_measure_engr_name) {
		this.outer_measure_engr_name = outer_measure_engr_name;
	}

	public String getInner_measure_emp_name() {
		return inner_measure_emp_name;
	}

	public void setInner_measure_emp_name(String inner_measure_emp_name) {
		this.inner_measure_emp_name = inner_measure_emp_name;
	}

	public String getInner_measure_dept_name() {
		return inner_measure_dept_name;
	}

	public void setInner_measure_dept_name(String inner_measure_dept_name) {
		this.inner_measure_dept_name = inner_measure_dept_name;
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

	public String getDeal_emp_name() {
		return deal_emp_name;
	}

	public void setDeal_emp_name(String deal_emp_name) {
		this.deal_emp_name = deal_emp_name;
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