
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hpm.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 9910 绩效函数页面配置表
 * @Table:
 * PRM_FUN_LINK
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AphiFunLink implements Serializable {

	
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
	 * 函数编码
	 */
	private String fun_code;
	
	/**
	 * 用于方案汉字显示
	 */
	private String source_name;
	
	/**
	 * 链接页面
	 */
	private String page_code;
	
	/**
	 * 链接数据页面
	 */
	private String page_data;
	
	/**
	 * 0:否 1:是
	 */
	private Integer is_group;
	

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
	* 设置 函数编码
	* @param value 
	*/
	public void setFun_code(String value) {
		this.fun_code = value;
	}
	
	/**
	* 获取 函数编码
	* @return String
	*/
	public String getFun_code() {
		return this.fun_code;
	}
	/**
	* 设置 用于方案汉字显示
	* @param value 
	*/
	public void setSource_name(String value) {
		this.source_name = value;
	}
	
	/**
	* 获取 用于方案汉字显示
	* @return String
	*/
	public String getSource_name() {
		return this.source_name;
	}
	/**
	* 设置 链接页面
	* @param value 
	*/
	public void setPage_code(String value) {
		this.page_code = value;
	}
	
	/**
	* 获取 链接页面
	* @return String
	*/
	public String getPage_code() {
		return this.page_code;
	}
	/**
	* 设置 链接数据页面
	* @param value 
	*/
	public void setPage_data(String value) {
		this.page_data = value;
	}
	
	/**
	* 获取 链接数据页面
	* @return String
	*/
	public String getPage_data() {
		return this.page_data;
	}
	/**
	* 设置 0:否 1:是
	* @param value 
	*/
	public void setIs_group(Integer value) {
		this.is_group = value;
	}
	
	/**
	* 获取 0:否 1:是
	* @return Integer
	*/
	public Integer getIs_group() {
		return this.is_group;
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