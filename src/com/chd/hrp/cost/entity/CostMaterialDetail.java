/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
 * @Title. @Description. 科室材料支出明细表<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class CostMaterialDetail implements Serializable {
 
	private static final long serialVersionUID = 5454155825314635342L;

	private String source_code;

	private String source_name;

	private String dept_code;

	private String dept_name;

	private String mate_code;

	private String mate_name;

	private String mate_type_code;

	private String mate_type_name;
	
	private String dept_no;

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

	private String year_month;

	private String acc_year;
	
	private String acc_month;

	/**
	 * 科室ID
	 */
	private Long dept_id;

	/**
	 * 材料分类ID
	 */
	private Long mate_type_id;

	/**
	 * 材料ID
	 */
	private Long mate_id;

	/**
	 * 资金来源
	 */
	private Long source_id;

	/**
	 * 收费标志
	 */
	private Integer is_charge;

	/**
	 * 数量
	 */
	private double sum_money;


	/**
	 * 导入验证信息
	 */
	private String error_type;

	/**
	 * 设置 集团ID
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}

	/**
	 * 获取 集团ID
	 */
	public Long getGroup_id() {
		return this.group_id;
	}

	public String getYear_month() {
		return year_month;
	}

	public void setYear_month(String year_month) {
		this.year_month = year_month;
	}

	/**
	 * 设置 医院ID
	 */
	public void setHos_id(Long value) {
		this.hos_id = value;
	}

	/**
	 * 获取 医院ID
	 */
	public Long getHos_id() {
		return this.hos_id;
	}

	/**
	 * 设置 账套编码
	 */
	public void setCopy_code(String value) {
		this.copy_code = value;
	}

	/**
	 * 获取 账套编码
	 */
	public String getCopy_code() {
		return this.copy_code;
	}



	/**
	 * 设置 科室ID
	 */
	public void setDept_id(Long value) {
		this.dept_id = value;
	}

	/**
	 * 获取 科室ID
	 */
	public Long getDept_id() {
		return this.dept_id;
	}

	/**
	 * 设置 材料分类ID
	 */
	public void setMate_type_id(Long value) {
		this.mate_type_id = value;
	}

	/**
	 * 获取 材料分类ID
	 */
	public Long getMate_type_id() {
		return this.mate_type_id;
	}

	/**
	 * 设置 材料ID
	 */
	public void setMate_id(Long value) {
		this.mate_id = value;
	}

	/**
	 * 获取 材料ID
	 */
	public Long getMate_id() {
		return this.mate_id;
	}

	/**
	 * 设置 资金来源
	 */
	public void setSource_id(Long value) {
		this.source_id = value;
	}

	/**
	 * 获取 资金来源
	 */
	public Long getSource_id() {
		return this.source_id;
	}

	/**
	 * 设置 收费标志
	 */
	public void setIs_charge(Integer value) {
		this.is_charge = value;
	}

	/**
	 * 获取 收费标志
	 */
	public Integer getIs_charge() {
		return this.is_charge;
	}

	

	public double getSum_money() {
		return sum_money;
	}

	public void setSum_money(double sum_money) {
		this.sum_money = sum_money;
	}

	/**
	 * 设置 导入验证信息
	 */
	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	
	public String getAcc_year() {
		return acc_year;
	}

	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}

	public String getAcc_month() {
		return acc_month;
	}

	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
	}
	
	/**
	 * 获取 导入验证信息
	 */
	public String getError_type() {
		return error_type;
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

	public String getMate_code() {
		return mate_code;
	}

	public void setMate_code(String mate_code) {
		this.mate_code = mate_code;
	}

	public String getMate_name() {
		return mate_name;
	}

	public void setMate_name(String mate_name) {
		this.mate_name = mate_name;
	}

	public String getMate_type_code() {
		return mate_type_code;
	}

	public void setMate_type_code(String mate_type_code) {
		this.mate_type_code = mate_type_code;
	}

	public String getMate_type_name() {
		return mate_type_name;
	}

	public void setMate_type_name(String mate_type_name) {
		this.mate_type_name = mate_type_name;
	}

	
    public String getDept_no() {
    	return dept_no;
    }

	
    public void setDept_no(String dept_no) {
    	this.dept_no = dept_no;
    }

}