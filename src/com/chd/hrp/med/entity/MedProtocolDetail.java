/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 08503 付款协议明细表
 * @Table:
 * MED_PROTOCOL_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MedProtocolDetail implements Serializable {

	
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
	 * 协议ID
	 */
	private Long protocol_id;
	
	/**
	 * 协议明细ID
	 */
	private Long detail_id;
	
	/**
	 * 药品材料变更ID
	 */
	private Long inv_no;
	
	/**
	 * 药品材料ID
	 */
	private Long inv_id;
	
	/**
	 * 单价
	 */
	private Double price;
	
	/**
	 * 证件
	 */
	private String cert_no;
	
	/**
	 * 备注
	 */
	private String note;
	

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
	* 设置 协议ID
	* @param value 
	*/
	public void setProtocol_id(Long value) {
		this.protocol_id = value;
	}
	
	/**
	* 获取 协议ID
	* @return Long
	*/
	public Long getProtocol_id() {
		return this.protocol_id;
	}
	/**
	* 设置 协议明细ID
	* @param value 
	*/
	public void setDetail_id(Long value) {
		this.detail_id = value;
	}
	
	/**
	* 获取 协议明细ID
	* @return Long
	*/
	public Long getDetail_id() {
		return this.detail_id;
	}
	/**
	* 设置 药品材料变更ID
	* @param value 
	*/
	public void setInv_no(Long value) {
		this.inv_no = value;
	}
	
	/**
	* 获取 药品材料变更ID
	* @return Long
	*/
	public Long getInv_no() {
		return this.inv_no;
	}
	/**
	* 设置 药品材料ID
	* @param value 
	*/
	public void setInv_id(Long value) {
		this.inv_id = value;
	}
	
	/**
	* 获取 药品材料ID
	* @return Long
	*/
	public Long getInv_id() {
		return this.inv_id;
	}
	/**
	* 设置 单价
	* @param value 
	*/
	public void setPrice(Double value) {
		this.price = value;
	}
	
	/**
	* 获取 单价
	* @return Double
	*/
	public Double getPrice() {
		return this.price;
	}
	/**
	* 设置 证件
	* @param value 
	*/
	public void setCert_no(String value) {
		this.cert_no = value;
	}
	
	/**
	* 获取 证件
	* @return String
	*/
	public String getCert_no() {
		return this.cert_no;
	}
	/**
	* 设置 备注
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 备注
	* @return String
	*/
	public String getNote() {
		return this.note;
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