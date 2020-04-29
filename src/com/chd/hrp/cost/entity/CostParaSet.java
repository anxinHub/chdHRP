/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 成本_分摊参数设置
 * @Table:
 * COST_PARA_SET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class CostParaSet implements Serializable {

	
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
	 * 统计年份
	 */
	private String acc_year;
	
	/**
	 * 统计月份
	 */
	private String acc_month;
	
	/**
	 * 科室性质
	 */
	private String natur_code;
	/**
	 * 科室性质
	 */
	private String natur_name;
	
	/**
	 * 分摊类型
	 */
	private String type_code;
	/**
	 * 分摊类型
	 */
	private String type_name;
	
	/**
	 * 分摊参数
	 */
	private String para_value;
	/**
	 * 分摊参数
	 */
	private String para_name;
	/**
	 * 单据编码
	 */
	private String bill_type;
	/**
	 * 单据名称
	 */
	private String bill_name;
	/**
	 * 内置编码
	 */
	private String p_code;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
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
	* 设置 统计年份
	* @param value 
	*/
	public void setAcc_year(String value) {
		this.acc_year = value;
	}
	
	/**
	* 获取 统计年份
	* @return String
	*/
	public String getAcc_year() {
		return this.acc_year;
	}
	/**
	* 设置 统计月份
	* @param value 
	*/
	public void setAcc_month(String value) {
		this.acc_month = value;
	}
	
	/**
	* 获取 统计月份
	* @return String
	*/
	public String getAcc_month() {
		return this.acc_month;
	}
	/**
	* 设置 科室性质
	* @param value 
	*/
	public void setNatur_code(String value) {
		this.natur_code = value;
	}
	
	/**
	* 获取 科室性质
	* @return Integer
	*/
	public String getNatur_code() {
		return this.natur_code;
	}
	/**
	* 设置 分摊类型
	* @param value 
	*/
	public void setType_code(String value) {
		this.type_code = value;
	}
	
	/**
	* 获取 分摊类型
	* @return String
	*/
	public String getType_code() {
		return this.type_code;
	}
	/**
	* 设置 分摊参数
	* @param value 
	*/
	public void setPara_value(String value) {
		this.para_value = value;
	}
	
	/**
	* 获取 分摊参数
	* @return String
	*/
	public String getPara_value() {
		return this.para_value;
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

	
    public String getNatur_name() {
    	return natur_name;
    }

	
    public void setNatur_name(String natur_name) {
    	this.natur_name = natur_name;
    }

	
    public String getType_name() {
    	return type_name;
    }

	
    public void setType_name(String type_name) {
    	this.type_name = type_name;
    }

	
    public String getPara_name() {
    	return para_name;
    }

	
    public void setPara_name(String para_name) {
    	this.para_name = para_name;
    }

	
    public String getBill_type() {
    	return bill_type;
    }

	
    public void setBill_type(String bill_type) {
    	this.bill_type = bill_type;
    }

	
    public String getBill_name() {
    	return bill_name;
    }

	
    public void setBill_name(String bill_name) {
    	this.bill_name = bill_name;
    }

	
    public String getP_code() {
    	return p_code;
    }

	
    public void setP_code(String p_code) {
    	this.p_code = p_code;
    }

}