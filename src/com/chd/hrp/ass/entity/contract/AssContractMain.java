/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.contract;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050501 资产合同主表
 * @Table:
 * ASS_CONTRACT_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssContractMain implements Serializable {

	
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
	 * 合同ID
	 */
	private Long contract_id;
	
	/**
	 * 合同号
	 */
	private String contract_no;
	
	/**
	 * 合同类别
	 */
	private String contract_type;
	
	/**
	 * 合同原始编号
	 */
	private String contract_ori_no;
	
	/**
	 * 合同名称
	 */
	private String contract_name;
	
	/**
	 * 统计年度
	 */
	private String ass_year;
	
	/**
	 * 统计月份
	 */
	private String ass_month;
	
	/**
	 * 供应商ID
	 */
	private Long ven_id;
	private String ven_code;
	private String ven_name;
	/**
	 * 供应商变更ID
	 */
	private Long ven_no;
	
	/**
	 * 签订日期
	 */
	private Date sign_date;
	
	/**
	 * 采购方式
	 */
	private String buy_type;
	
	/**
	 * 开始日期
	 */
	private Date start_date;
	
	/**
	 * 结束日期
	 */
	private Date end_date;
	
	/**
	 * 合同描述
	 */
	private String contract_detail;
	
	/**
	 * 售后服务说明
	 */
	private String service_detail;
	
	/**
	 * 我方负责人
	 */
	private Long emp_main;
	private String emp_main_name;
	/**
	 * 对方负责人
	 */
	private String provider;
	
	/**
	 * 是否经过招标
	 */
	private Integer is_bid;
	
	/**
	 * 合同金额
	 */
	private Double contract_money;
	
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
	private Long check_emp;
	private String check_emp_name;
	/**
	 * 审核日期
	 */
	private Date check_date;
	
	/**
	 * 状态
	 */
	private Integer state;
	
	/**
	 * 集团合同
	 */
	private Integer is_group;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	private String cur_code;
	
	private String cur_name;
	
	
	public String getCur_code() {
		return cur_code;
	}

	public void setCur_code(String cur_code) {
		this.cur_code = cur_code;
	}

	public String getCur_name() {
		return cur_name;
	}

	public void setCur_name(String cur_name) {
		this.cur_name = cur_name;
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
	* 设置 合同ID
	* @param value 
	*/
	public void setContract_id(Long value) {
		this.contract_id = value;
	}
	
	/**
	* 获取 合同ID
	* @return Long
	*/
	public Long getContract_id() {
		return this.contract_id;
	}
	/**
	* 设置 合同号
	* @param value 
	*/
	public void setContract_no(String value) {
		this.contract_no = value;
	}
	
	/**
	* 获取 合同号
	* @return String
	*/
	public String getContract_no() {
		return this.contract_no;
	}
	/**
	* 设置 合同类别
	* @param value 
	*/
	public void setContract_type(String value) {
		this.contract_type = value;
	}
	
	/**
	* 获取 合同类别
	* @return String
	*/
	public String getContract_type() {
		return this.contract_type;
	}
	/**
	* 设置 合同原始编号
	* @param value 
	*/
	public void setContract_ori_no(String value) {
		this.contract_ori_no = value;
	}
	
	/**
	* 获取 合同原始编号
	* @return String
	*/
	public String getContract_ori_no() {
		return this.contract_ori_no;
	}
	/**
	* 设置 合同名称
	* @param value 
	*/
	public void setContract_name(String value) {
		this.contract_name = value;
	}
	
	/**
	* 获取 合同名称
	* @return String
	*/
	public String getContract_name() {
		return this.contract_name;
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
	* 设置 采购方式
	* @param value 
	*/
	public void setBuy_type(String value) {
		this.buy_type = value;
	}
	
	/**
	* 获取 采购方式
	* @return String
	*/
	public String getBuy_type() {
		return this.buy_type;
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
	* 设置 结束日期
	* @param value 
	*/
	public void setEnd_date(Date value) {
		this.end_date = value;
	}
	
	/**
	* 获取 结束日期
	* @return Date
	*/
	public Date getEnd_date() {
		return this.end_date;
	}
	/**
	* 设置 合同描述
	* @param value 
	*/
	public void setContract_detail(String value) {
		this.contract_detail = value;
	}
	
	/**
	* 获取 合同描述
	* @return String
	*/
	public String getContract_detail() {
		return this.contract_detail;
	}
	/**
	* 设置 售后服务说明
	* @param value 
	*/
	public void setService_detail(String value) {
		this.service_detail = value;
	}
	
	/**
	* 获取 售后服务说明
	* @return String
	*/
	public String getService_detail() {
		return this.service_detail;
	}
	/**
	* 设置 我方负责人
	* @param value 
	*/
	public void setEmp_main(Long value) {
		this.emp_main = value;
	}
	
	/**
	* 获取 我方负责人
	* @return Long
	*/
	public Long getEmp_main() {
		return this.emp_main;
	}
	/**
	* 设置 对方负责人
	* @param value 
	*/
	public void setProvider(String value) {
		this.provider = value;
	}
	
	/**
	* 获取 对方负责人
	* @return String
	*/
	public String getProvider() {
		return this.provider;
	}
	/**
	* 设置 是否经过招标
	* @param value 
	*/
	public void setIs_bid(Integer value) {
		this.is_bid = value;
	}
	
	/**
	* 获取 是否经过招标
	* @return Integer
	*/
	public Integer getIs_bid() {
		return this.is_bid;
	}
	/**
	* 设置 合同金额
	* @param value 
	*/
	public void setContract_money(Double value) {
		this.contract_money = value;
	}
	
	/**
	* 获取 合同金额
	* @return Double
	*/
	public Double getContract_money() {
		return this.contract_money;
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
	* 设置 集团合同
	* @param value 
	*/
	public void setIs_group(Integer value) {
		this.is_group = value;
	}
	
	/**
	* 获取 集团合同
	* @return Integer
	*/
	public Integer getIs_group() {
		return this.is_group;
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
	 * @return the emp_main_name
	 */
	public String getEmp_main_name() {
		return emp_main_name;
	}

	/**
	 * @param emp_main_name the emp_main_name to set
	 */
	public void setEmp_main_name(String emp_main_name) {
		this.emp_main_name = emp_main_name;
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
	 * @return the check_emp_name
	 */
	public String getCheck_emp_name() {
		return check_emp_name;
	}

	/**
	 * @param check_emp_name the check_emp_name to set
	 */
	public void setCheck_emp_name(String check_emp_name) {
		this.check_emp_name = check_emp_name;
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
	
}