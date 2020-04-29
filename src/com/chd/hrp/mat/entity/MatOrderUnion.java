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
 * 04211 订单合并关系表
 * @Table:
 * MAT_ORDER_UNION
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MatOrderUnion implements Serializable {

	
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
	 * 合并订单ID
	 */
	private Long order_id_new;
	
	/**
	 * 原订单ID
	 */
	private Long order_id_old;
	

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
	* 设置 合并订单ID
	* @param value 
	*/
	public void setOrder_id_new(Long value) {
		this.order_id_new = value;
	}
	
	/**
	* 获取 合并订单ID
	* @return Long
	*/
	public Long getOrder_id_new() {
		return this.order_id_new;
	}
	/**
	* 设置 原订单ID
	* @param value 
	*/
	public void setOrder_id_old(Long value) {
		this.order_id_old = value;
	}
	
	/**
	* 获取 原订单ID
	* @return Long
	*/
	public Long getOrder_id_old() {
		return this.order_id_old;
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