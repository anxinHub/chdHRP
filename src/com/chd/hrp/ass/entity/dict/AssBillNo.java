
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.entity.dict;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050110 资产单据号规则设置
 * @Table:
 * ASS_BILL_NO
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssBillNo implements Serializable {

	
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
	 * 单据名称
	 */
	private String bill_table;
	/**
	 * 单据名称
	 */
	private String bill_name;
	
	
	/**
	 * 标准前缀
	 */
	private String pref;
	
	/**
	 * 是否使用年前缀
	 */
	private Integer seq_no;
	/**
	 * 是否使用年前缀
	 */
	private Integer max_no;
	
	/**
	 * 是否使用年前缀
	 */
	private String is_year;
	/**
	 * 是否使用月前缀
	 */
	private String is_month;
	/**
	 * 是否使用日前缀
	 */
	private String is_day;
	/**
	 * 是否使用仓库前缀
	 */
	private String is_store;
	
	/**
	 * 连接符
	 */
	private String pref_point;
	/**
	 * 连接符
	 */
	private String is_store_point;
	/**
	 * 连接符
	 */
	private String is_year_point;
	/**
	 * 连接符
	 */
	private String is_month_point;
	/**
	 * 连接符
	 */
	private String is_day_point;
	
	
	/**
	 * 五笔码
	 */
	private String wxb_code;
	
	/**
	 * 拼音码
	 */
	private String spell_code;
	

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
	* 设置 单据名称
	* @param value 
	*/
	public void setBill_name(String value) {
		this.bill_name = value;
	}
	
	/**
	* 获取 单据名称
	* @return String
	*/
	public String getBill_name() {
		return this.bill_name;
	}
	/**
	* 设置 标准前缀
	* @param value 
	*/
	public void setPref(String value) {
		this.pref = value;
	}
	
	/**
	* 获取 标准前缀
	* @return String
	*/
	public String getPref() {
		return this.pref;
	}
	/**
	* 设置 五笔码
	* @param value 
	*/
	public void setWxb_code(String value) {
		this.wxb_code = value;
	}
	
	/**
	* 获取 五笔码
	* @return String
	*/
	public String getWxb_code() {
		return this.wxb_code;
	}
	/**
	* 设置 拼音码
	* @param value 
	*/
	public void setSpell_code(String value) {
		this.spell_code = value;
	}
	
	/**
	* 获取 拼音码
	* @return String
	*/
	public String getSpell_code() {
		return this.spell_code;
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
     * 获取 seq_no
     * @return seq_no
     */
    public Integer getSeq_no() {
    	return seq_no;
    }

	
    /**
     * 设置 seq_no
     * @param seq_no 
     */
    public void setSeq_no(Integer seq_no) {
    	this.seq_no = seq_no;
    }

	
    /**
     * 获取 max_no
     * @return max_no
     */
    public Integer getMax_no() {
    	return max_no;
    }

	
    /**
     * 设置 max_no
     * @param max_no 
     */
    public void setMax_no(Integer max_no) {
    	this.max_no = max_no;
    }

	
    /**
     * 获取 业务单据表名
     * @return bill_table
     */
    public String getBill_table() {
    	return bill_table;
    }

	
    /**
     * 设置 业务单据表名
     * @param bill_table 
     */
    public void setBill_table(String bill_table) {
    	this.bill_table = bill_table;
    }

	public String getIs_year() {
		return is_year;
	}

	public void setIs_year(String is_year) {
		this.is_year = is_year;
	}

	public String getIs_month() {
		return is_month;
	}

	public void setIs_month(String is_month) {
		this.is_month = is_month;
	}

	public String getIs_day() {
		return is_day;
	}

	public void setIs_day(String is_day) {
		this.is_day = is_day;
	}

	public String getIs_store() {
		return is_store;
	}

	public void setIs_store(String is_store) {
		this.is_store = is_store;
	}

	public String getIs_store_point() {
		return is_store_point;
	}

	public void setIs_store_point(String is_store_point) {
		this.is_store_point = is_store_point;
	}

	public String getIs_year_point() {
		return is_year_point;
	}

	public void setIs_year_point(String is_year_point) {
		this.is_year_point = is_year_point;
	}

	public String getIs_month_point() {
		return is_month_point;
	}

	public void setIs_month_point(String is_month_point) {
		this.is_month_point = is_month_point;
	}

	public String getIs_day_point() {
		return is_day_point;
	}

	public void setIs_day_point(String is_day_point) {
		this.is_day_point = is_day_point;
	}

	public String getPref_point() {
		return pref_point;
	}

	public void setPref_point(String pref_point) {
		this.pref_point = pref_point;
	}

	
	
}