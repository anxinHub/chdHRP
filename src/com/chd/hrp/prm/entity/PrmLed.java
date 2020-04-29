
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 0503 指示灯
 * @Table:
 * PRM_LED
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class PrmLed implements Serializable {

	
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
	 * 等级代码
	 */
	private String sec_code;
	
	/**
	 * 等级名称
	 */
	private String sec_name;
	
	/**
	 * 拼音码
	 */
	private String spell_code;
	
	/**
	 * 五笔码
	 */
	private String wbx_code;
	
	/**
	 * KPI起始分
	 */
	private Double kpi_beg_score;
	
	/**
	 * KPI结束分
	 */
	private Double kpi_end_score;
	
	/**
	 * 指示灯路径
	 */
	private String led_path;
	

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
	* 设置 等级代码
	* @param value 
	*/
	public void setSec_code(String value) {
		this.sec_code = value;
	}
	
	/**
	* 获取 等级代码
	* @return String
	*/
	public String getSec_code() {
		return this.sec_code;
	}
	/**
	* 设置 等级名称
	* @param value 
	*/
	public void setSec_name(String value) {
		this.sec_name = value;
	}
	
	/**
	* 获取 等级名称
	* @return String
	*/
	public String getSec_name() {
		return this.sec_name;
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
	* 设置 五笔码
	* @param value 
	*/
	public void setWbx_code(String value) {
		this.wbx_code = value;
	}
	
	/**
	* 获取 五笔码
	* @return String
	*/
	public String getWbx_code() {
		return this.wbx_code;
	}
	/**
	* 设置 KPI起始分
	* @param value 
	*/
	public void setKpi_beg_score(Double value) {
		this.kpi_beg_score = value;
	}
	
	/**
	* 获取 KPI起始分
	* @return Double
	*/
	public Double getKpi_beg_score() {
		return this.kpi_beg_score;
	}
	/**
	* 设置 KPI结束分
	* @param value 
	*/
	public void setKpi_end_score(Double value) {
		this.kpi_end_score = value;
	}
	
	/**
	* 获取 KPI结束分
	* @return Double
	*/
	public Double getKpi_end_score() {
		return this.kpi_end_score;
	}
	/**
	* 设置 指示灯路径
	* @param value 
	*/
	public void setLed_path(String value) {
		this.led_path = value;
	}
	
	/**
	* 获取 指示灯路径
	* @return String
	*/
	public String getLed_path() {
		return this.led_path;
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