/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.check.general;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 051101 科室盘点单明细_医用设备
 * @Table:
 * ASS_CHECK_D_DETAIL_General
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssCheckDdetailGeneral implements Serializable {

	
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
	 * 任务单号
	 */
	private String check_plan_no;
	
	/**
	 * 盘点单号
	 */
	private String check_no;
	
	/**
	 * 科室编码ID
	 */
	private Long dept_id;
	
	/**
	 * 科室编码NO
	 */
	private Long dept_no;
	
	/**
	 * 卡片编码
	 */
	private String ass_card_no;
	
	/**
	 * 资产ID
	 */
	private Long ass_id;
	
	/**
	 * 资产变更ID
	 */
	private Long ass_no;
	
	/**
	 * 账面数量
	 */
	private Integer acc_amount;
	
	/**
	 * 盘点数量
	 */
	private Integer check_amount;
	
	/**
	 * 盈亏数量
	 */
	private Integer pl_amount;
	
	/**
	 * 盈亏原因
	 */
	private String pl_reason;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	private String ass_type_name;
	private String ass_name;
	private String ass_ori_card_no;
	private String bar_code;
	private String dept_name;
	
	private String ass_spec;
	
	private Double price;
	
	private String ass_seq_no;
	
	private Date in_date;
	
	
	
    public String getAss_spec() {
		return ass_spec;
	}


	public void setAss_spec(String ass_spec) {
		this.ass_spec = ass_spec;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public String getAss_seq_no() {
		return ass_seq_no;
	}


	public void setAss_seq_no(String ass_seq_no) {
		this.ass_seq_no = ass_seq_no;
	}


	public Date getIn_date() {
		return in_date;
	}


	public void setIn_date(Date in_date) {
		this.in_date = in_date;
	}


	public String getAss_type_name() {
    	return ass_type_name;
    }

	
    public void setAss_type_name(String ass_type_name) {
    	this.ass_type_name = ass_type_name;
    }

	
    public String getAss_name() {
    	return ass_name;
    }

	
    public void setAss_name(String ass_name) {
    	this.ass_name = ass_name;
    }

	
    public String getAss_ori_card_no() {
    	return ass_ori_card_no;
    }

	
    public void setAss_ori_card_no(String ass_ori_card_no) {
    	this.ass_ori_card_no = ass_ori_card_no;
    }

	
    public String getDept_name() {
    	return dept_name;
    }

	
    public void setDept_name(String dept_name) {
    	this.dept_name = dept_name;
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
	* 设置 任务单号
	* @param value 
	*/
	public void setCheck_plan_no(String value) {
		this.check_plan_no = value;
	}
	
	/**
	* 获取 任务单号
	* @return String
	*/
	public String getCheck_plan_no() {
		return this.check_plan_no;
	}
	/**
	* 设置 盘点单号
	* @param value 
	*/
	public void setCheck_no(String value) {
		this.check_no = value;
	}
	
	/**
	* 获取 盘点单号
	* @return String
	*/
	public String getCheck_no() {
		return this.check_no;
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
	* 设置 卡片编码
	* @param value 
	*/
	public void setAss_card_no(String value) {
		this.ass_card_no = value;
	}
	
	/**
	* 获取 卡片编码
	* @return String
	*/
	public String getAss_card_no() {
		return this.ass_card_no;
	}
	/**
	* 设置 资产ID
	* @param value 
	*/
	public void setAss_id(Long value) {
		this.ass_id = value;
	}
	
	/**
	* 获取 资产ID
	* @return Long
	*/
	public Long getAss_id() {
		return this.ass_id;
	}
	/**
	* 设置 资产变更ID
	* @param value 
	*/
	public void setAss_no(Long value) {
		this.ass_no = value;
	}
	
	/**
	* 获取 资产变更ID
	* @return Long
	*/
	public Long getAss_no() {
		return this.ass_no;
	}
	/**
	* 设置 账面数量
	* @param value 
	*/
	public void setAcc_amount(Integer value) {
		this.acc_amount = value;
	}
	
	/**
	* 获取 账面数量
	* @return Integer
	*/
	public Integer getAcc_amount() {
		return this.acc_amount;
	}
	/**
	* 设置 盘点数量
	* @param value 
	*/
	public void setCheck_amount(Integer value) {
		this.check_amount = value;
	}
	
	/**
	* 获取 盘点数量
	* @return Integer
	*/
	public Integer getCheck_amount() {
		return this.check_amount;
	}
	/**
	* 设置 盈亏数量
	* @param value 
	*/
	public void setPl_amount(Integer value) {
		this.pl_amount = value;
	}
	
	/**
	* 获取 盈亏数量
	* @return Integer
	*/
	public Integer getPl_amount() {
		return this.pl_amount;
	}
	/**
	* 设置 盈亏原因
	* @param value 
	*/
	public void setPl_reason(String value) {
		this.pl_reason = value;
	}
	
	/**
	* 获取 盈亏原因
	* @return String
	*/
	public String getPl_reason() {
		return this.pl_reason;
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


	
    public String getBar_code() {
    	return bar_code;
    }


	
    public void setBar_code(String bar_code) {
    	this.bar_code = bar_code;
    }
	
}