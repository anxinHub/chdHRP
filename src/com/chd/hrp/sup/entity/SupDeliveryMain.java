/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.sup.entity;

import java.io.Serializable;
import java.util.*;

/**
 * @Description: 100101 送货单主表
 * @Table: SUP_DELIVERY_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class SupDeliveryMain implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 送货ID
	 */
	private Long delivery_id;

	/**
	 * 送货单号
	 */
	private String delivery_no;

	public Long getCome_from() {
		return come_from;
	}

	public void setCome_from(Long come_from) {
		this.come_from = come_from;
	}

	/**
	 * 集团
	 */
	private Long group_id;

	
	private Long come_from;
	/**
	 * 医院
	 */
	private String hos_id;

	/**
	 * 账套
	 */
	private String copy_code;

	/**
	 * 统计年度
	 */
	private String acc_year;

	/**
	 * 统计月份
	 */
	private String acc_month;

	/**
	 * 供应商ID
	 */
	private Long sup_id;

	/**
	 * 供应商NO
	 */
	private Long sup_no;

	/**
	 * 供应商NO
	 */
	private String sup_code;

	/**
	 * 供应商NO
	 */
	private String sup_name;

	/**
	 * 编制日期
	 */
	private Date create_date;

	/**
	 * 确定日期
	 */
	private Date check_date;

	/**
	 * 单据类型
	 */
	private String bill_type;

	/**
	 * 医院名称
	 */
	private Long hos_id_in;

	/**
	 * 医院名称
	 */
	private String hos_name;

	/**
	 * 是否定向
	 */
	private Integer is_dir;

	/**
	 * 定向科室
	 */
	private Long dir_dept_id;

	/**
	 * 定向科室
	 */
	private Long dir_dept_no;

	/**
	 * 定向科室
	 */
	private String dir_dept_code; 

	/**
	 * 定向科室
	 */
	private String dir_dept_name;

	/**
	 * 送货地址
	 */
	private String delivery_address;
	private	String disinfect_no;
	
	

	public String getDisinfect_no() {
		return disinfect_no;
	}

	public void setDisinfect_no(String disinfect_no) {
		this.disinfect_no = disinfect_no;
	}

	private String note;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * 单据条码地址
	 */
	private String delivery_url;

	/**
	 * 入库状态
	 */
	private Integer in_state;

	/**
	 * 单据状态
	 */
	private Integer state;

	private Date bill_date;

	private String bill_no;

	public Date getBill_date() {
		return bill_date;
	}

	public void setBill_date(Date bill_date) {
		this.bill_date = bill_date;
	}

	public String getBill_no() {
		return bill_no;
	}

	public void setBill_no(String bill_no) {
		this.bill_no = bill_no;
	}

	/**
	 * 导入验证信息
	 */
	private String error_type;

	/**
	 * 设置 送货ID
	 * 
	 * @param value
	 */
	public void setDelivery_id(Long value) {
		this.delivery_id = value;
	}

	/**
	 * 获取 送货ID
	 * 
	 * @return Long
	 */
	public Long getDelivery_id() {
		return this.delivery_id;
	}

	/**
	 * 设置 送货单号
	 * 
	 * @param value
	 */
	public void setDelivery_no(String value) {
		this.delivery_no = value;
	}

	/**
	 * 获取 送货单号
	 * 
	 * @return String
	 */
	public String getDelivery_no() {
		return this.delivery_no;
	}

	/**
	 * 设置 集团
	 * 
	 * @param value
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}

	/**
	 * 获取 集团
	 * 
	 * @return Long
	 */
	public Long getGroup_id() {
		return this.group_id;
	}

	/**
	 * 设置 医院
	 * 
	 * @param value
	 */
	public void setHos_id(String value) {
		this.hos_id = value;
	}

	/**
	 * 获取 医院
	 * 
	 * @return String
	 */
	public String getHos_id() {
		return this.hos_id;
	}

	/**
	 * 设置 账套
	 * 
	 * @param value
	 */
	public void setCopy_code(String value) {
		this.copy_code = value;
	}

	/**
	 * 获取 账套
	 * 
	 * @return String
	 */
	public String getCopy_code() {
		return this.copy_code;
	}

	/**
	 * 设置 统计年度
	 * 
	 * @param value
	 */
	public void setAcc_year(String value) {
		this.acc_year = value;
	}

	/**
	 * 获取 统计年度
	 * 
	 * @return String
	 */
	public String getAcc_year() {
		return this.acc_year;
	}

	/**
	 * 设置 统计月份
	 * 
	 * @param value
	 */
	public void setAcc_month(String value) {
		this.acc_month = value;
	}

	/**
	 * 获取 统计月份
	 * 
	 * @return String
	 */
	public String getAcc_month() {
		return this.acc_month;
	}

	/**
	 * 设置 供应商ID
	 * 
	 * @param value
	 */
	public void setSup_id(Long value) {
		this.sup_id = value;
	}

	/**
	 * 获取 供应商ID
	 * 
	 * @return Long
	 */
	public Long getSup_id() {
		return this.sup_id;
	}

	/**
	 * 设置 供应商NO
	 * 
	 * @param value
	 */
	public void setSup_no(Long value) {
		this.sup_no = value;
	}

	/**
	 * 获取 供应商NO
	 * 
	 * @return Long
	 */
	public Long getSup_no() {
		return this.sup_no;
	}

	/**
	 * 设置 编制日期
	 * 
	 * @param value
	 */
	public void setCreate_date(Date value) {
		this.create_date = value;
	}

	/**
	 * 获取 编制日期
	 * 
	 * @return Date
	 */
	public Date getCreate_date() {
		return this.create_date;
	}

	/**
	 * 设置 确定日期
	 * 
	 * @param value
	 */
	public void setCheck_date(Date value) {
		this.check_date = value;
	}

	/**
	 * 获取 确定日期
	 * 
	 * @return Date
	 */
	public Date getCheck_date() {
		return this.check_date;
	}

	/**
	 * 设置 单据类型
	 * 
	 * @param value
	 */
	public void setBill_type(String value) {
		this.bill_type = value;
	}

	/**
	 * 获取 单据类型
	 * 
	 * @return String
	 */
	public String getBill_type() {
		return this.bill_type;
	}

	/**
	 * 设置 医院名称
	 * 
	 * @param value
	 */
	public void setHos_id_in(Long value) {
		this.hos_id_in = value;
	}

	/**
	 * 获取 医院名称
	 * 
	 * @return Long
	 */
	public Long getHos_id_in() {
		return this.hos_id_in;
	}

	/**
	 * 设置 是否定向
	 * 
	 * @param value
	 */
	public void setIs_dir(Integer value) {
		this.is_dir = value;
	}

	/**
	 * 获取 是否定向
	 * 
	 * @return Integer
	 */
	public Integer getIs_dir() {
		return this.is_dir;
	}

	public Long getDir_dept_id() {
		return dir_dept_id;
	}

	public void setDir_dept_id(Long dir_dept_id) {
		this.dir_dept_id = dir_dept_id;
	}

	public Long getDir_dept_no() {
		return dir_dept_no;
	}

	public void setDir_dept_no(Long dir_dept_no) {
		this.dir_dept_no = dir_dept_no;
	}

	/**
	 * 设置 送货地址
	 * 
	 * @param value
	 */
	public void setDelivery_address(String value) {
		this.delivery_address = value;
	}

	/**
	 * 获取 送货地址
	 * 
	 * @return String
	 */
	public String getDelivery_address() {
		return this.delivery_address;
	}

	/**
	 * 设置 单据条码地址
	 * 
	 * @param value
	 */
	public void setDelivery_url(String value) {
		this.delivery_url = value;
	}

	/**
	 * 获取 单据条码地址
	 * 
	 * @return String
	 */
	public String getDelivery_url() {
		return this.delivery_url;
	}

	/**
	 * 设置 入库状态
	 * 
	 * @param value
	 */
	public void setIn_state(Integer value) {
		this.in_state = value;
	}

	/**
	 * 获取 入库状态
	 * 
	 * @return Integer
	 */
	public Integer getIn_state() {
		return this.in_state;
	}

	/**
	 * 设置 单据状态
	 * 
	 * @param value
	 */
	public void setState(Integer value) {
		this.state = value;
	}

	/**
	 * 获取 单据状态
	 * 
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

	public String getSup_code() {
		return sup_code;
	}

	public void setSup_code(String sup_code) {
		this.sup_code = sup_code;
	}

	public String getSup_name() {
		return sup_name;
	}

	public void setSup_name(String sup_name) {
		this.sup_name = sup_name;
	}

	public String getHos_name() {
		return hos_name;
	}

	public void setHos_name(String hos_name) {
		this.hos_name = hos_name;
	}

	public String getDir_dept_code() {
		return dir_dept_code;
	}

	public void setDir_dept_code(String dir_dept_code) {
		this.dir_dept_code = dir_dept_code;
	}

	public String getDir_dept_name() {
		return dir_dept_name;
	}

	public void setDir_dept_name(String dir_dept_name) {
		this.dir_dept_name = dir_dept_name;
	}

}