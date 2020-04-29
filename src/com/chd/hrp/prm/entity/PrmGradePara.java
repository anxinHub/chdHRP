
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
 * 0204 指标评分方法参数表
 * @Table:
 * PRM_GRADE_PARA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class PrmGradePara implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 01:区间法
02:加分法
03:扣分法
04:参照法
05:比较法
	 */
	private String grade_meth_code;
	
	/**
	 * 评分方法名称
	 */
	private String grade_meth_name;
	
	/**
	 * 拼音码
	 */
	private String spell_code;
	
	/**
	 * 五笔码
	 */
	private String wbx_code;
	
	/**
	 * 0:不停用 1:停用
	 */
	private Integer is_stop;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 01:区间法
02:加分法
03:扣分法
04:参照法
05:比较法
	* @param value 
	*/
	public void setGrade_meth_code(String value) {
		this.grade_meth_code = value;
	}
	
	/**
	* 获取 01:区间法
02:加分法
03:扣分法
04:参照法
05:比较法
	* @return String
	*/
	public String getGrade_meth_code() {
		return this.grade_meth_code;
	}
	/**
	* 设置 评分方法名称
	* @param value 
	*/
	public void setGrade_meth_name(String value) {
		this.grade_meth_name = value;
	}
	
	/**
	* 获取 评分方法名称
	* @return String
	*/
	public String getGrade_meth_name() {
		return this.grade_meth_name;
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
	* 设置 0:不停用 1:停用
	* @param value 
	*/
	public void setIs_stop(Integer value) {
		this.is_stop = value;
	}
	
	/**
	* 获取 0:不停用 1:停用
	* @return Integer
	*/
	public Integer getIs_stop() {
		return this.is_stop;
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