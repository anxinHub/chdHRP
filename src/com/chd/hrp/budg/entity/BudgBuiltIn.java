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
 * 内置数据
01自上而下
01医院年度，1，NULL，NULL，NULL
02医院月份，0，02汇总，NULL，04科室月份
03科室年度，0，01分解，01医院年度，NULL
04科室月份，0，01分解，03科室年度，NULL


02自下而上
01医院年度，0，02汇总，NULL，03科室年度
02医院月份，0，02汇总，NULL，04科室月份
03科室年度，1，NULL，NULL，NULL
04科室月份，0，01分解，03科室年度，NULL

 * @Table:
 * BUDG_BUILT_IN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class BudgBuiltIn implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 业务预算编制模式
	 */
	private String work_budg_edit_mode;
	
	/**
	 * 预算层次
	 */
	private String budg_level;
	
	/**
	 * 是否独立核算
	 */
	private Integer is_single_count;
	
	/**
	 * 分解或汇总
	 */
	private String resolve_or_sum;
	
	/**
	 * 分解层次
	 */
	private String resolve_level;
	
	/**
	 * 汇总层次
	 */
	private String sum_level;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 业务预算编制模式
	* @param value 
	*/
	public void setWork_budg_edit_mode(String value) {
		this.work_budg_edit_mode = value;
	}
	
	/**
	* 获取 业务预算编制模式
	* @return String
	*/
	public String getWork_budg_edit_mode() {
		return this.work_budg_edit_mode;
	}
	/**
	* 设置 预算层次
	* @param value 
	*/
	public void setBudg_level(String value) {
		this.budg_level = value;
	}
	
	/**
	* 获取 预算层次
	* @return String
	*/
	public String getBudg_level() {
		return this.budg_level;
	}
	/**
	* 设置 是否独立核算
	* @param value 
	*/
	public void setIs_single_count(Integer value) {
		this.is_single_count = value;
	}
	
	/**
	* 获取 是否独立核算
	* @return Integer
	*/
	public Integer getIs_single_count() {
		return this.is_single_count;
	}
	/**
	* 设置 分解或汇总
	* @param value 
	*/
	public void setResolve_or_sum(String value) {
		this.resolve_or_sum = value;
	}
	
	/**
	* 获取 分解或汇总
	* @return String
	*/
	public String getResolve_or_sum() {
		return this.resolve_or_sum;
	}
	/**
	* 设置 分解层次
	* @param value 
	*/
	public void setResolve_level(String value) {
		this.resolve_level = value;
	}
	
	/**
	* 获取 分解层次
	* @return String
	*/
	public String getResolve_level() {
		return this.resolve_level;
	}
	/**
	* 设置 汇总层次
	* @param value 
	*/
	public void setSum_level(String value) {
		this.sum_level = value;
	}
	
	/**
	* 获取 汇总层次
	* @return String
	*/
	public String getSum_level() {
		return this.sum_level;
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