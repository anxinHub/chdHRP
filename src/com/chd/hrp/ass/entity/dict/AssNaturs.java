
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
 * 050103 资产性质
 * @Table:
 * ASS_NATURS
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssNaturs implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 性质编码
	 */
	private String naturs_code;
	
	/**
	 * 性质名称
	 */
	private String naturs_name;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 性质编码
	* @param value 
	*/
	public void setNaturs_code(String value) {
		this.naturs_code = value;
	}
	
	/**
	* 获取 性质编码
	* @return String
	*/
	public String getNaturs_code() {
		return this.naturs_code;
	}
	/**
	* 设置 性质名称
	* @param value 
	*/
	public void setNaturs_name(String value) {
		this.naturs_name = value;
	}
	
	/**
	* 获取 性质名称
	* @return String
	*/
	public String getNaturs_name() {
		return this.naturs_name;
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