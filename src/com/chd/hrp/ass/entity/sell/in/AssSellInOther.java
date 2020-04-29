/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.sell.in;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050901 资产有偿调拨入库单主表(其他固定资产)
 * @Table:
 * ASS_SELL_IN_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssSellInOther implements Serializable {

	
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
	 * 调拨单号
	 */
	private String sell_in_no;
	
	/**
	 * 调入仓库ID
	 */
	private Long in_store_id;
	
	/**
	 * 调入仓库变更NO
	 */
	private Long in_store_no;
	
	private String in_store_code;
	
	private String in_store_name;
	
	/**
	 * 调出集团
	 */
	private Long out_group_id;
	
	private String out_group_name;
	
	/**
	 * 调出单位
	 */
	private Long out_hos_id;
	
	private String out_hos_name;
	
	/**
	 * 调出仓库ID
	 */
	private Long out_store_id;
	
	/**
	 * 调出仓库变更NO
	 */
	private Long out_store_no;
	
	private String out_store_code;
	
	private String out_store_name;
	
	/**
	 * 资产原值
	 */
	private Double price;
	
	/**
	 * 累计折旧
	 */
	private Double add_depre;
	
	/**
	 * 资产净值
	 */
	private Double cur_money;
	
	/**
	 * 预留残值
	 */
	private Double fore_money;
	
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
	 * 备注
	 */
	private String note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	private String is_import;
	
	private String bus_type_code;
	
	private String bus_type_name;
	
	
	public String getBus_type_name() {
		return bus_type_name;
	}

	public void setBus_type_name(String bus_type_name) {
		this.bus_type_name = bus_type_name;
	}

	public String getBus_type_code() {
		return bus_type_code;
	}

	public void setBus_type_code(String bus_type_code) {
		this.bus_type_code = bus_type_code;
	}

	public String getIs_import() {
		return is_import;
	}

	public void setIs_import(String is_import) {
		this.is_import = is_import;
	}

	public String getIn_store_code() {
		return in_store_code;
	}
	
	public void setIn_store_code(String in_store_code) {
		this.in_store_code = in_store_code;
	}
	
	public String getIn_store_name() {
		return in_store_name;
	}
	
	public void setIn_store_name(String in_store_name) {
		this.in_store_name = in_store_name;
	}
	
	public String getOut_group_name() {
		return out_group_name;
	}
	
	public void setOut_group_name(String out_group_name) {
		this.out_group_name = out_group_name;
	}
	
	public String getOut_hos_name() {
		return out_hos_name;
	}
	
	public void setOut_hos_name(String out_hos_name) {
		this.out_hos_name = out_hos_name;
	}
	
	public String getOut_store_code() {
		return out_store_code;
	}
	
	public void setOut_store_code(String out_store_code) {
		this.out_store_code = out_store_code;
	}
	
	public String getOut_store_name() {
		return out_store_name;
	}
	
	public void setOut_store_name(String out_store_name) {
		this.out_store_name = out_store_name;
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
	
	public String getState_name() {
		return state_name;
	}
	
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

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
	* 设置 调拨单号
	* @param value 
	*/
	public void setSell_in_no(String value) {
		this.sell_in_no = value;
	}
	
	/**
	* 获取 调拨单号
	* @return String
	*/
	public String getSell_in_no() {
		return this.sell_in_no;
	}
	/**
	* 设置 调入仓库ID
	* @param value 
	*/
	public void setIn_store_id(Long value) {
		this.in_store_id = value;
	}
	
	/**
	* 获取 调入仓库ID
	* @return Long
	*/
	public Long getIn_store_id() {
		return this.in_store_id;
	}
	/**
	* 设置 调入仓库变更NO
	* @param value 
	*/
	public void setIn_store_no(Long value) {
		this.in_store_no = value;
	}
	
	/**
	* 获取 调入仓库变更NO
	* @return Long
	*/
	public Long getIn_store_no() {
		return this.in_store_no;
	}
	/**
	* 设置 调出集团
	* @param value 
	*/
	public void setOut_group_id(Long value) {
		this.out_group_id = value;
	}
	
	/**
	* 获取 调出集团
	* @return Long
	*/
	public Long getOut_group_id() {
		return this.out_group_id;
	}
	/**
	* 设置 调出单位
	* @param value 
	*/
	public void setOut_hos_id(Long value) {
		this.out_hos_id = value;
	}
	
	/**
	* 获取 调出单位
	* @return Long
	*/
	public Long getOut_hos_id() {
		return this.out_hos_id;
	}
	/**
	* 设置 调出仓库ID
	* @param value 
	*/
	public void setOut_store_id(Long value) {
		this.out_store_id = value;
	}
	
	/**
	* 获取 调出仓库ID
	* @return Long
	*/
	public Long getOut_store_id() {
		return this.out_store_id;
	}
	/**
	* 设置 调出仓库变更NO
	* @param value 
	*/
	public void setOut_store_no(Long value) {
		this.out_store_no = value;
	}
	
	/**
	* 获取 调出仓库变更NO
	* @return Long
	*/
	public Long getOut_store_no() {
		return this.out_store_no;
	}
	/**
	* 设置 资产原值
	* @param value 
	*/
	public void setPrice(Double value) {
		this.price = value;
	}
	
	/**
	* 获取 资产原值
	* @return Double
	*/
	public Double getPrice() {
		return this.price;
	}
	/**
	* 设置 累计折旧
	* @param value 
	*/
	public void setAdd_depre(Double value) {
		this.add_depre = value;
	}
	
	/**
	* 获取 累计折旧
	* @return Double
	*/
	public Double getAdd_depre() {
		return this.add_depre;
	}
	/**
	* 设置 资产净值
	* @param value 
	*/
	public void setCur_money(Double value) {
		this.cur_money = value;
	}
	
	/**
	* 获取 资产净值
	* @return Double
	*/
	public Double getCur_money() {
		return this.cur_money;
	}
	/**
	* 设置 预留残值
	* @param value 
	*/
	public void setFore_money(Double value) {
		this.fore_money = value;
	}
	
	/**
	* 获取 预留残值
	* @return Double
	*/
	public Double getFore_money() {
		return this.fore_money;
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
}