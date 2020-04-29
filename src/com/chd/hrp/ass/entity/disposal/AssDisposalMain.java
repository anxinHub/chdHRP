/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.disposal;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 051001资产处置主表
 * @Table:
 * ASS_DISPOSAL_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssDisposalMain implements Serializable {

	
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
	 * 年月
	 */
	private String year_month;
	
	/**
	 * 处置单序号
	 */
	private Integer dis_id;
	
	/**
	 * 处置单号
	 */
	private String ass_dis_no;
	
	/**
	 * 业务类型
	 */
	private String bus_type_code;
	
	private String bus_type_name;
	
	/**
	 * 资产性质
	 */
	private String ass_nature;
	
	private String naturs_name;
	
	/**
	 * 卡片位置
	 */
	private String card;
	
	/**
	 * 仓库编码ID
	 */
	private Long store_id;
	
	/**
	 * 仓库编码NO
	 */
	private Long store_no;
	private String store_code ;
	private String store_name;
	
	/**
	 * 科室编码ID
	 */
	private Long dept_id;
	
	/**
	 * 科室编码NO
	 */
	private Long dept_no;
	
	private String dept_code ;
	private String dept_name;
	
	/**
	 * 备注
	 */
	private String note;
	
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


	private Integer ass_id;
	
	private Double card_amount;
	
	private Double card_price;
  public Integer getAss_id() {
		return ass_id;
	}

	public void setAss_id(Integer ass_id) {
		this.ass_id = ass_id;
	}

	public Double getCard_amount() {
		return card_amount;
	}

	public void setCard_amount(Double card_amount) {
		this.card_amount = card_amount;
	}

	public Double getCard_price() {
		return card_price;
	}

	public void setCard_price(Double card_price) {
		this.card_price = card_price;
	}

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
	* 设置 年月
	* @param value 
	*/
	public void setYear_month(String value) {
		this.year_month = value;
	}
	
	/**
	* 获取 年月
	* @return String
	*/
	public String getYear_month() {
		return this.year_month;
	}
	/**
	* 设置 处置单序号
	* @param value 
	*/
	public void setDis_id(Integer value) {
		this.dis_id = value;
	}
	
	/**
	* 获取 处置单序号
	* @return Integer
	*/
	public Integer getDis_id() {
		return this.dis_id;
	}
	/**
	* 设置 处置单号
	* @param value 
	*/
	public void setAss_dis_no(String value) {
		this.ass_dis_no = value;
	}
	
	/**
	* 获取 处置单号
	* @return String
	*/
	public String getAss_dis_no() {
		return this.ass_dis_no;
	}
	/**
	* 设置 业务类型
	* @param value 
	*/
	public void setBus_type_code(String value) {
		this.bus_type_code = value;
	}
	
	/**
	* 获取 业务类型
	* @return String
	*/
	public String getBus_type_code() {
		return this.bus_type_code;
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
	* 设置 卡片位置
	* @param value 
	*/
	public void setCard(String value) {
		this.card = value;
	}
	
	/**
	* 获取 卡片位置
	* @return String
	*/
	public String getCard() {
		return this.card;
	}
	/**
	* 设置 仓库编码ID
	* @param value 
	*/
	public void setStore_id(Long value) {
		this.store_id = value;
	}
	
	/**
	* 获取 仓库编码ID
	* @return Long
	*/
	public Long getStore_id() {
		return this.store_id;
	}
	/**
	* 设置 仓库编码NO
	* @param value 
	*/
	public void setStore_no(Long value) {
		this.store_no = value;
	}
	
	/**
	* 获取 仓库编码NO
	* @return Long
	*/
	public Long getStore_no() {
		return this.store_no;
	}
	/**
	* 设置 科室编码ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 科室编码ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 科室编码NO
	* @param value 
	*/
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	
	/**
	* 获取 科室编码NO
	* @return Long
	*/
	public Long getDept_no() {
		return this.dept_no;
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

	public String getBus_type_name() {
		return bus_type_name;
	}

	public void setBus_type_name(String bus_type_name) {
		this.bus_type_name = bus_type_name;
	}


	public String getStore_code() {
		return store_code;
	}

	public void setStore_code(String store_code) {
		this.store_code = store_code;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
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

	public String getNaturs_name() {
		return naturs_name;
	}

	public void setNaturs_name(String naturs_name) {
		this.naturs_name = naturs_name;
	}
}