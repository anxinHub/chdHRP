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
 * 04202 科室需求计划明细表
 * @Table:
 * MAT_REQUIRE_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MatRequireDetail implements Serializable {

	
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
	 * 计划单ID
	 */
	private Long req_id;
	
	/**
	 * 明细ID
	 */
	private Long req_detail_id;
	
	/**
	 * 材料变更ID
	 */
	private Long inv_no;
	
	/**
	 * 材料ID
	 */
	private Long inv_id;
	
	/**
	 * 数量
	 */
	private Double amount;
	
	/**
	 * 计划单价
	 */
	private Double price;
	
	/**
	 * 包装数量
	 */
	private Double num;
	
	/**
	 * 包装单位
	 */
	private String pack_code;
	
	/**
	 * 需求日期
	 */
	private Date rdate;
	
	/**
	 * 供应商变更ID
	 */
	private Long sup_no;
	
	/**
	 * 供应商ID
	 */
	private Long sup_id;
	
	/**
	 * 执行数量
	 */
	private Double exec_amount;
	
	/**
	 * 备注
	 */
	private String memo;
	

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
	* 设置 计划单ID
	* @param value 
	*/
	public void setReq_id(Long value) {
		this.req_id = value;
	}
	
	/**
	* 获取 计划单ID
	* @return Long
	*/
	public Long getReq_id() {
		return this.req_id;
	}
	/**
	* 设置 明细ID
	* @param value 
	*/
	public void setReq_detail_id(Long value) {
		this.req_detail_id = value;
	}
	
	/**
	* 获取 明细ID
	* @return Long
	*/
	public Long getReq_detail_id() {
		return this.req_detail_id;
	}
	/**
	* 设置 材料变更ID
	* @param value 
	*/
	public void setInv_no(Long value) {
		this.inv_no = value;
	}
	
	/**
	* 获取 材料变更ID
	* @return Long
	*/
	public Long getInv_no() {
		return this.inv_no;
	}
	/**
	* 设置 材料ID
	* @param value 
	*/
	public void setInv_id(Long value) {
		this.inv_id = value;
	}
	
	/**
	* 获取 材料ID
	* @return Long
	*/
	public Long getInv_id() {
		return this.inv_id;
	}
	/**
	* 设置 数量
	* @param value 
	*/
	public void setAmount(Double value) {
		this.amount = value;
	}
	
	/**
	* 获取 数量
	* @return Double
	*/
	public Double getAmount() {
		return this.amount;
	}
	/**
	* 设置 计划单价
	* @param value 
	*/
	public void setPrice(Double value) {
		this.price = value;
	}
	
	/**
	* 获取 计划单价
	* @return Double
	*/
	public Double getPrice() {
		return this.price;
	}
	/**
	* 设置 包装数量
	* @param value 
	*/
	public void setNum(Double value) {
		this.num = value;
	}
	
	/**
	* 获取 包装数量
	* @return Double
	*/
	public Double getNum() {
		return this.num;
	}
	/**
	* 设置 包装单位
	* @param value 
	*/
	public void setPack_code(String value) {
		this.pack_code = value;
	}
	
	/**
	* 获取 包装单位
	* @return String
	*/
	public String getPack_code() {
		return this.pack_code;
	}
	/**
	* 设置 需求日期
	* @param value 
	*/
	public void setRdate(Date value) {
		this.rdate = value;
	}
	
	/**
	* 获取 需求日期
	* @return Date
	*/
	public Date getRdate() {
		return this.rdate;
	}
	/**
	* 设置 供应商变更ID
	* @param value 
	*/
	public void setSup_no(Long value) {
		this.sup_no = value;
	}
	
	/**
	* 获取 供应商变更ID
	* @return Long
	*/
	public Long getSup_no() {
		return this.sup_no;
	}
	/**
	* 设置 供应商ID
	* @param value 
	*/
	public void setSup_id(Long value) {
		this.sup_id = value;
	}
	
	/**
	* 获取 供应商ID
	* @return Long
	*/
	public Long getSup_id() {
		return this.sup_id;
	}
	/**
	* 设置 执行数量
	* @param value 
	*/
	public void setExec_amount(Double value) {
		this.exec_amount = value;
	}
	
	/**
	* 获取 执行数量
	* @return Double
	*/
	public Double getExec_amount() {
		return this.exec_amount;
	}
	/**
	* 设置 备注
	* @param value 
	*/
	public void setMemo(String value) {
		this.memo = value;
	}
	
	/**
	* 获取 备注
	* @return String
	*/
	public String getMemo() {
		return this.memo;
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