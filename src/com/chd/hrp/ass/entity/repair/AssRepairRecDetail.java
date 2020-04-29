/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.repair;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 051201 资产维修记录明细
 * @Table:
 * ASS_REPAIR_REC_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssRepairRecDetail implements Serializable {

	
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
	 * 统计年度
	 */
	private String ass_year; 
	/**
	 * 统计月份
	 */
	private String ass_month; 
	/**
	 * 维修记录ID
	 */
	private String repair_rec_no ;
	/**
	 * 材料ID
	 */
	private Long inv_id;
	/**
	 * 材料编码
	 */
	private String inv_code;
	
	/**
	 * 材料变更ID
	 */
	private String inv_name;
	
	/**
	 * 材料单价
	 */
	private Double inv_price;
	
	/**
	 * 材料数量
	 */
	private Integer inv_num;
	
	/**
	 * 材料费
	 */
	private Double stuff_money;
	

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
	* 设置 材料ID
	* @param value 
	*/
	public void setInv_code(String value) {
		this.inv_code = value;
	}
	
	/**
	* 获取 材料ID
	* @return Long
	*/
	public String getInv_code() {
		return this.inv_code;
	}
	/**
	* 设置 材料变更ID
	* @param value 
	*/
	public void setInv_name(String value) {
		this.inv_name = value;
	}
	
	/**
	* 获取 材料变更ID
	* @return Long
	*/
	public String getInv_name() {
		return this.inv_name;
	}
	/**
	* 设置 材料单价
	* @param value 
	*/
	public void setInv_price(Double value) {
		this.inv_price = value;
	}
	
	/**
	* 获取 材料单价
	* @return Double
	*/
	public Double getInv_price() {
		return this.inv_price;
	}
	/**
	* 设置 材料数量
	* @param value 
	*/
	public void setInv_num(Integer value) {
		this.inv_num = value;
	}
	
	/**
	* 获取 材料数量
	* @return Integer
	*/
	public Integer getInv_num() {
		return this.inv_num;
	}
	/**
	* 设置 材料费
	* @param value 
	*/
	public void setStuff_money(Double value) {
		this.stuff_money = value;
	}
	
	/**
	* 获取 材料费
	* @return Double
	*/
	public Double getStuff_money() {
		return this.stuff_money;
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

	public String getAss_year() {
		return ass_year;
	}

	public void setAss_year(String ass_year) {
		this.ass_year = ass_year;
	}

	public String getAss_month() {
		return ass_month;
	}

	public void setAss_month(String ass_month) {
		this.ass_month = ass_month;
	}

	public Long getInv_id() {
		return inv_id;
	}

	public void setInv_id(Long inv_id) {
		this.inv_id = inv_id;
	}

	public void setRepair_rec_no(String repair_rec_no) {
		this.repair_rec_no = repair_rec_no;
	}

	public String getRepair_rec_no() {
		return repair_rec_no;
	}
}