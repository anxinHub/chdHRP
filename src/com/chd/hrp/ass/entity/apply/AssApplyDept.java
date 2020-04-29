
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.entity.apply;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050201 购置申请单
 * @Table:
 * ASS_APPLY_DEPT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssApplyDept implements Serializable {

	
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
	 * 申请ID
	 */
	private Long apply_id;
	
	
	/**
	 * 申请单号
	 */
	private String apply_no;
	
	/**
	 * 购置年度
	 */
	private String apply_year;
	
	/**
	 * 购置月份
	 */
	private String apply_month;
	
	/**
	 * 申请科室ID
	 */
	private Long dept_id;
	/**
	 * 申请科室编码
	 */
	private String dept_code;
	/**
	 * 申请科室名称
	 */
	private String dept_name;
	/**
	 * 用户
	 */
	private String apply_emp_name;
	
	
	private String create_emp_name;
	
	
	private String check_emp_name;
	
	/**
	 * 申请科室NO
	 */
	private Long dept_no;
	
	/**
	 * 申请人
	 */
	private Long apply_emp;
	
	/**
	 * 申请日期
	 */
	private Date apply_date;
	
	/**
	 * 申请金额
	 */
	private Double apply_money;
	
	/**
	 * 摘要
	 */
	private String summary;
	
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
	private Long check_emp;
	
	/**
	 * 审核日期
	 */
	private Date check_date;
	
	/**
	 * 是否追加申请
	 */
	private Integer is_add;
	
	/**
	 * 状态
	 */
	private Integer state;
	
	/**
	 * 备注
	 */
	private String note;

	/**
	 * 明细ID
	 */
	private Integer detail_id;
	/**
	 * 资产ID
	 */
	private Long ass_id;
	
	/**
	 * 资产变更NO
	 */
	private Long ass_no;
	
	/**
	 * 规格
	 */
	private String ass_spec;
	
	/**
	 * 型号
	 */
	private String ass_model;
	
	/**
	 * 品牌
	 */
	private String ass_brand;
	
	/**
	 * 生产厂家ID
	 */
	private Long fac_id;
	
	/**
	 * 生产厂家NO
	 */
	private Long fac_no;
	
	/**
	 * 入库数量
	 */
	private Integer apply_amount;
	
	/**
	 * 单价
	 */
	private Double advice_price;

  /**
	 * 导入验证信息
	 */
	private String error_type;
	/**
	 * 项目ID
	 */
	private Long proj_id;
	/**
	 * 项目变更ID
	 */
	private Long proj_no;
	
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
	* 设置 申请单号
	* @param value 
	*/
	public void setApply_no(String value) {
		this.apply_no = value;
	}
	
	/**
	* 获取 申请单号
	* @return String
	*/
	public String getApply_no() {
		return this.apply_no;
	}
	/**
	* 设置 购置年度
	* @param value 
	*/
	public void setApply_year(String value) {
		this.apply_year = value;
	}
	
	/**
	* 获取 购置年度
	* @return String
	*/
	public String getApply_year() {
		return this.apply_year;
	}
	/**
	* 设置 购置月份
	* @param value 
	*/
	public void setApply_month(String value) {
		this.apply_month = value;
	}
	
	/**
	* 获取 购置月份
	* @return String
	*/
	public String getApply_month() {
		return this.apply_month;
	}
	/**
	* 设置 申请科室ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 申请科室ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 申请科室NO
	* @param value 
	*/
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	
	/**
	* 获取 申请科室NO
	* @return Long
	*/
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	* 设置 申请人
	* @param value 
	*/
	public void setApply_emp(Long value) {
		this.apply_emp = value;
	}
	
	/**
	* 获取 申请人
	* @return Long
	*/
	public Long getApply_emp() {
		return this.apply_emp;
	}
	/**
	* 设置 申请金额
	* @param value 
	*/
	public void setApply_money(Double value) {
		this.apply_money = value;
	}
	
	/**
	* 获取 申请金额
	* @return Double
	*/
	public Double getApply_money() {
		return this.apply_money;
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
	* 设置 审核日期
	* @param value 
	*/
	public void setCheck_date(Date value) {
		this.check_date = value;
	}
	
	/**
	* 获取 审核日期
	* @return Date
	*/
	public Date getCheck_date() {
		return this.check_date;
	}
	/**
	* 设置 是否追加申请
	* @param value 
	*/
	public void setIs_add(Integer value) {
		this.is_add = value;
	}
	
	/**
	* 获取 是否追加申请
	* @return Integer
	*/
	public Integer getIs_add() {
		return this.is_add;
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
	* 设置 备注
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 备注
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

	public Long getApply_id() {
		return apply_id;
	}

	public void setApply_id(Long apply_id) {
		this.apply_id = apply_id;
	}
   
	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getApply_emp_name() {
		return apply_emp_name;
	}

	public void setApply_emp_name(String apply_emp_name) {
		this.apply_emp_name = apply_emp_name;
	}

	public String getCreate_emp_name() {
		return create_emp_name;
	}

	public void setCreate_emp_name(String create_emp_name) {
		this.create_emp_name = create_emp_name;
	}

	public String getCheck_emp_name() {
		return check_emp_name;
	}

	public void setCheck_emp_name(String check_emp_name) {
		this.check_emp_name = check_emp_name;
	}

	/**
	 * @return the apply_date
	 */
	public Date getApply_date() {
		return apply_date;
	}

	/**
	 * @param apply_date the apply_date to set
	 */
	public void setApply_date(Date apply_date) {
		this.apply_date = apply_date;
	}

	public Integer getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(Integer detail_id) {
		this.detail_id = detail_id;
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

	public String getAss_brand() {
		return ass_brand;
	}

	public void setAss_brand(String ass_brand) {
		this.ass_brand = ass_brand;
	}

	public Long getFac_id() {
		return fac_id;
	}

	public void setFac_id(Long fac_id) {
		this.fac_id = fac_id;
	}

	public Long getFac_no() {
		return fac_no;
	}

	public void setFac_no(Long fac_no) {
		this.fac_no = fac_no;
	}

	public Integer getApply_amount() {
		return apply_amount;
	}

	public void setApply_amount(Integer apply_amount) {
		this.apply_amount = apply_amount;
	}

	public Double getAdvice_price() {
		return advice_price;
	}

	public void setAdvice_price(Double advice_price) {
		this.advice_price = advice_price;
	}

	public Long getProj_id() {
		return proj_id;
	}

	public void setProj_id(Long proj_id) {
		this.proj_id = proj_id;
	}

	public Long getProj_no() {
		return proj_no;
	}

	public void setProj_no(Long proj_no) {
		this.proj_no = proj_no;
	}
	

	
}