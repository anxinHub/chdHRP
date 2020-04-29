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
 * 
 * @Table:
 * MAT_CHECK_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MatAffiCheckDetail implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;
	/**
	 * 
	 */
	private Long hos_id;
	
	/**
	 * 
	 */
	private Long group_id;
	
	/**
	 * 
	 */
	private Object copy_code;
	
	/**
	 * 
	 */
	private Long check_id;
	
	/**
	 * 
	 */
	private String check_code;
	
	/**
	 * 
	 */
	private Long detail_id;
	
	/**
	 * 
	 */
	private Long inv_id;
	
	/**
	 * 
	 */
	private Long inv_no;
	
	/**
	 * 
	 */
	private Object batch_no;
	
	/**
	 * 
	 */
	private Object bar_code;
	
	/**
	 * 
	 */
	private Long location_id;
	
	/**
	 * 
	 */
	private Double cur_amount;
	
	/**
	 * 
	 */
	private Double chk_amount;
	
	/**
	 * 
	 */
	private Double price;
	
	/**
	 * 
	 */
	private Date inva_date;
	
	/**
	 * 
	 */
	private Date disinfect_date;
	
	/**
	 * 
	 */
	private Object note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCopy_code(Object value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCheck_id(Long value) {
		this.check_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getCheck_id() {
		return this.check_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCheck_code(String value) {
		this.check_code = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public String getCheck_code() {
		return this.check_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setDetail_id(Long value) {
		this.detail_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getDetail_id() {
		return this.detail_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setInv_id(Long value) {
		this.inv_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getInv_id() {
		return this.inv_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setInv_no(Long value) {
		this.inv_no = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getInv_no() {
		return this.inv_no;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setBatch_no(Object value) {
		this.batch_no = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getBatch_no() {
		return this.batch_no;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setBar_code(Object value) {
		this.bar_code = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getBar_code() {
		return this.bar_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setLocation_id(Long value) {
		this.location_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getLocation_id() {
		return this.location_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCur_amount(Double value) {
		this.cur_amount = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getCur_amount() {
		return this.cur_amount;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setChk_amount(Double value) {
		this.chk_amount = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getChk_amount() {
		return this.chk_amount;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setPrice(Double value) {
		this.price = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getPrice() {
		return this.price;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setInva_date(Date value) {
		this.inva_date = value;
	}
	
	/**
	* 获取 
	* @return Date
	*/
	public Date getInva_date() {
		return this.inva_date;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setDisinfect_date(Date value) {
		this.disinfect_date = value;
	}
	
	/**
	* 获取 
	* @return Date
	*/
	public Date getDisinfect_date() {
		return this.disinfect_date;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setNote(Object value) {
		this.note = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getNote() {
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