/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 04604 供应商证件类型
 * @Table:
 * MAT_VEN_CERT_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MatVenCertType implements Serializable {

	
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
	 * 证件类型ID
	 */
	private Long type_id;
	
	/**
	 * 证件类型编码
	 */
	private String type_code;
	
	/**
	 * 证件类型名称
	 */
	private String type_name;
	
	/**
	 * 拼音码
	 */
	private String spell_code;
	
	/**
	 * 五笔码
	 */
	private String wbx_code;
	
	/**
	 * 是否预警
	 */
	private Integer is_alarm;
	
	/**
	 * 预警天数
	 */
	private Integer war_days;
	

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
	* 设置 证件类型ID
	* @param value 
	*/
	public void setType_id(Long value) {
		this.type_id = value;
	}
	
	/**
	* 获取 证件类型ID
	* @return Long
	*/
	public Long getType_id() {
		return this.type_id;
	}
	/**
	* 设置 证件类型编码
	* @param value 
	*/
	public void setType_code(String value) {
		this.type_code = value;
	}
	
	/**
	* 获取 证件类型编码
	* @return String
	*/
	public String getType_code() {
		return this.type_code;
	}
	/**
	* 设置 证件类型名称
	* @param value 
	*/
	public void setType_name(String value) {
		this.type_name = value;
	}
	
	/**
	* 获取 证件类型名称
	* @return String
	*/
	public String getType_name() {
		return this.type_name;
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
	* 设置 是否预警
	* @param value 
	*/
	public void setIs_alarm(Integer value) {
		this.is_alarm = value;
	}
	
	/**
	* 获取 是否预警
	* @return Integer
	*/
	public Integer getIs_alarm() {
		return this.is_alarm;
	}
	/**
	* 设置 预警天数
	* @param value 
	*/
	public void setWar_days(Integer value) {
		this.war_days = value;
	}
	
	/**
	* 获取 预警天数
	* @return Integer
	*/
	public Integer getWar_days() {
		return this.war_days;
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