/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 函数参数表
 * @Table:
 * BUDG_FUN_PARA
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgFunPara implements Serializable {

	
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
	 * 参数代码
	 */
	private String para_code;
	
	/**
	 * 参数名称
	 */
	private String para_name;
	
	/**
	 * 参数栈序列
	 */
	private Integer stack_seq_no;
	
	/**
	 * 部件类型
	 */
	private String com_type_code;
	
	
	private String com_type_nature;

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
	* 设置 参数代码
	* @param value 
	*/
	public void setPara_code(String value) {
		this.para_code = value;
	}
	
	/**
	* 获取 参数代码
	* @return String
	*/
	public String getPara_code() {
		return this.para_code;
	}
	/**
	* 设置 参数名称
	* @param value 
	*/
	public void setPara_name(String value) {
		this.para_name = value;
	}
	
	/**
	* 获取 参数名称
	* @return String
	*/
	public String getPara_name() {
		return this.para_name;
	}
	/**
	* 设置 参数栈序列
	* @param value 
	*/
	public void setStack_seq_no(Integer value) {
		this.stack_seq_no = value;
	}
	
	/**
	* 获取 参数栈序列
	* @return Integer
	*/
	public Integer getStack_seq_no() {
		return this.stack_seq_no;
	}
	/**
	* 设置 部件类型
	* @param value 
	*/
	public void setCom_type_code(String value) {
		this.com_type_code = value;
	}
	
	/**
	* 获取 部件类型
	* @return String
	*/
	public String getCom_type_code() {
		return this.com_type_code;
	}
	
	
	public String getCom_type_nature() {
		return com_type_nature;
	}

	public void setCom_type_nature(String com_type_nature) {
		this.com_type_nature = com_type_nature;
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