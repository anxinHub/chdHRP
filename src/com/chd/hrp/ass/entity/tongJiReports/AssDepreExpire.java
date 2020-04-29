/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.entity.tongJiReports;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @Description: 050802 资产资金来源匹配表_一般
 * @Table: ASS_RESOURCE_GENERAL_SET
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class AssDepreExpire implements Serializable {

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

	private String ass_id;

	private String ass_no;

	private String ass_code;

	private String ass_name;

	private String ass_spec;

	private String ass_mondl;

	private String ass_brand;

	private String fac_name;

	private String store_name;

	private String dept_name;

	private String ass_amount;

	private String is_depre;

	private String acc_depre_amount;

	private String add_depre_month;

	private String depre_year;

	private String depre_month;

	/**
	 * 卡片原值
	 */
	private Double price;

	private Double depre_money;

	private Date in_date;

	public Double getDepre_money() {
		return depre_money;
	}

	public void setDepre_money(Double depre_money) {
		this.depre_money = depre_money;
	}

	public Date getIn_date() {
		return in_date;
	}

	public void setIn_date(Date in_date) {
		this.in_date = in_date;
	}

	/**
	 * 导入验证信息
	 */
	private String error_type;

	/**
	 * 设置 集体ID
	 * 
	 * @param value
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}

	/**
	 * 获取 集体ID
	 * 
	 * @return Long
	 */
	public Long getGroup_id() {
		return this.group_id;
	}

	/**
	 * 设置 医院ID
	 * 
	 * @param value
	 */
	public void setHos_id(Long value) {
		this.hos_id = value;
	}

	/**
	 * 获取 医院ID
	 * 
	 * @return Long
	 */
	public Long getHos_id() {
		return this.hos_id;
	}

	/**
	 * 设置 账套编码
	 * 
	 * @param value
	 */
	public void setCopy_code(String value) {
		this.copy_code = value;
	}

	/**
	 * 获取 账套编码
	 * 
	 * @return String
	 */
	public String getCopy_code() {
		return this.copy_code;
	}

	/**
	 * 设置 资产卡片号
	 * 
	 * @param value
	 */
	public void setAss_card_no(String value) {
		this.ass_card_no = value;
	}

	/**
	 * 获取 资产卡片号
	 * 
	 * @return String
	 */
	public String getAss_card_no() {
		return this.ass_card_no;
	}

	public String getAss_id() {
		return ass_id;
	}

	public void setAss_id(String ass_id) {
		this.ass_id = ass_id;
	}

	public String getAss_no() {
		return ass_no;
	}

	public void setAss_no(String ass_no) {
		this.ass_no = ass_no;
	}

	public String getAss_amount() {
		return ass_amount;
	}

	public void setAss_amount(String ass_amount) {
		this.ass_amount = ass_amount;
	}

	public String getIs_depre() {
		return is_depre;
	}

	public void setIs_depre(String is_depre) {
		this.is_depre = is_depre;
	}

	public String getAcc_depre_amount() {
		return acc_depre_amount;
	}

	public void setAcc_depre_amount(String acc_depre_amount) {
		this.acc_depre_amount = acc_depre_amount;
	}

	public String getAdd_depre_month() {
		return add_depre_month;
	}

	public void setAdd_depre_month(String add_depre_month) {
		this.add_depre_month = add_depre_month;
	}

	public String getDepre_year() {
		return depre_year;
	}

	public void setDepre_year(String depre_year) {
		this.depre_year = depre_year;
	}

	public String getDepre_month() {
		return depre_month;
	}

	public void setDepre_month(String depre_month) {
		this.depre_month = depre_month;
	}

	/**
	 * 设置 卡片原值
	 * 
	 * @param value
	 */
	public void setPrice(Double value) {
		this.price = value;
	}

	/**
	 * 获取 卡片原值
	 * 
	 * @return Double
	 */
	public Double getPrice() {
		return this.price;
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

	public String getAss_spec() {
		return ass_spec;
	}

	public void setAss_spec(String ass_spec) {
		this.ass_spec = ass_spec;
	}

	public String getAss_mondl() {
		return ass_mondl;
	}

	public void setAss_mondl(String ass_mondl) {
		this.ass_mondl = ass_mondl;
	}

	public String getFac_name() {
		return fac_name;
	}

	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
	}

	public String getStore_name() {
		return store_name;
	}

	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getAss_brand() {
		return ass_brand;
	}

	public void setAss_brand(String ass_brand) {
		this.ass_brand = ass_brand;
	}

}