
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.entity.apply;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 050201 购置申请与验收关系表
 * @Table:
 * ASS_APPLY_ACCEPT_MAP
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssApplyAcceptMap implements Serializable {

	
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
	 * 资产购置申请号
	 */
	private String apply_no;
	
	/**
	 * 资产购置申请明细号
	 */
	private Integer apply_detail_id;
	
	/**
	 * 资产购置验收单据号
	 */
	private String accept_no;
	
	/**
	 * 资产购置验收明细号
	 */
	private Integer accept_detail_id;
	

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
	* 设置 资产购置申请号
	* @param value 
	*/
	public void setApply_no(String value) {
		this.apply_no = value;
	}
	
	/**
	* 获取 资产购置申请号
	* @return String
	*/
	public String getApply_no() {
		return this.apply_no;
	}
	/**
	* 设置 资产购置申请明细号
	* @param value 
	*/
	public void setApply_detail_id(Integer value) {
		this.apply_detail_id = value;
	}
	
	/**
	* 获取 资产购置申请明细号
	* @return Integer
	*/
	public Integer getApply_detail_id() {
		return this.apply_detail_id;
	}
	/**
	* 设置 资产购置验收单据号
	* @param value 
	*/
	public void setAccept_no(String value) {
		this.accept_no = value;
	}
	
	/**
	* 获取 资产购置验收单据号
	* @return String
	*/
	public String getAccept_no() {
		return this.accept_no;
	}
	/**
	* 设置 资产购置验收明细号
	* @param value 
	*/
	public void setAccept_detail_id(Integer value) {
		this.accept_detail_id = value;
	}
	
	/**
	* 获取 资产购置验收明细号
	* @return Integer
	*/
	public Integer getAccept_detail_id() {
		return this.accept_detail_id;
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