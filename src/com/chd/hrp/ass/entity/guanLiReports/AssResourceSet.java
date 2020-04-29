/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.guanLiReports;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050802 资产资金来源匹配表_一般
 * @Table:
 * ASS_RESOURCE_GENERAL_SET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssResourceSet implements Serializable {

	
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
	 * 资产卡片号
	 */
	private String ass_card_no;
	
	/**
	 * 资金来源编码
	 */
	private Long source_id;
	
	private String source_code ;
	
	private String source_name ;
	
	private Long ass_id;
	private Long ass_no;
	private String ass_code;
	private String ass_name;
	
	private Long ass_type_id;
	private String ass_type_code;
	private String ass_type_name;
	
	/**
	 * 卡片原值
	 */
	private Double price;
	private Date in_date;
	public Date getIn_date() {
		return in_date;
	}

	public void setIn_date(Date in_date) {
		this.in_date = in_date;
	}

	/**
	 * 累计折旧
	 */
	private Double depre_money;
	
	/**
	 * 卡片净值
	 */
	private Double cur_money;
	
	/**
	 * 卡片残值
	 */
	private Double fore_money;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	
	private String ven_name;
	
	private String dept_name;
	
	private String ass_spec;
	
	private String state_name;
	
	
	
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

	public String getAss_spec() {
		return ass_spec;
	}

	public void setAss_spec(String ass_spec) {
		this.ass_spec = ass_spec;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
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
	* 设置 资产卡片号
	* @param value 
	*/
	public void setAss_card_no(String value) {
		this.ass_card_no = value;
	}
	
	/**
	* 获取 资产卡片号
	* @return String
	*/
	public String getAss_card_no() {
		return this.ass_card_no;
	}
	/**
	* 设置 资金来源编码
	* @param value 
	*/
	public void setSource_id(Long value) {
		this.source_id = value;
	}
	
	/**
	* 获取 资金来源编码
	* @return Long
	*/
	public Long getSource_id() {
		return this.source_id;
	}
	
	public String getSource_code() {
		return source_code;
	}

	public void setSource_code(String source_code) {
		this.source_code = source_code;
	}

	public String getSource_name() {
		return source_name;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

	/**
	* 设置 卡片原值
	* @param value 
	*/
	public void setPrice(Double value) {
		this.price = value;
	}
	
	/**
	* 获取 卡片原值
	* @return Double
	*/
	public Double getPrice() {
		return this.price;
	}
	/**
	* 设置 累计折旧
	* @param value 
	*/
	public void setDepre_money(Double value) {
		this.depre_money = value;
	}
	
	/**
	* 获取 累计折旧
	* @return Double
	*/
	public Double getDepre_money() {
		return this.depre_money;
	}
	/**
	* 设置 卡片净值
	* @param value 
	*/
	public void setCur_money(Double value) {
		this.cur_money = value;
	}
	
	/**
	* 获取 卡片净值
	* @return Double
	*/
	public Double getCur_money() {
		return this.cur_money;
	}
	/**
	* 设置 卡片残值
	* @param value 
	*/
	public void setFore_money(Double value) {
		this.fore_money = value;
	}
	
	/**
	* 获取 卡片残值
	* @return Double
	*/
	public Double getFore_money() {
		return this.fore_money;
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

	public String getAss_code() {
		return ass_code;
	}

	public void setAss_code(String ass_code) {
		this.ass_code = ass_code;
	}

	public String getAss_name() {
		return ass_name;
	}

	public void setAss_name(String ass_name) {
		this.ass_name = ass_name;
	}

	public Long getAss_type_id() {
		return ass_type_id;
	}

	public void setAss_type_id(Long ass_type_id) {
		this.ass_type_id = ass_type_id;
	}

	public String getAss_type_code() {
		return ass_type_code;
	}

	public void setAss_type_code(String ass_type_code) {
		this.ass_type_code = ass_type_code;
	}

	public String getAss_type_name() {
		return ass_type_name;
	}

	public void setAss_type_name(String ass_type_name) {
		this.ass_type_name = ass_type_name;
	}
	
}