/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.pay;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 资产分期付款表_专用设备
 * @Table:
 * ASS_PAY_STAGE_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssPayStageHouse implements Serializable {

	
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
	 * 卡片编号
	 */
	private String ass_card_no;
	
	/**
	 * 付款期号
	 */
	private String stage_no;
	
	/**
	 * 摘要
	 */
	private String stage_name;
	
	/**
	 * 付款条件
	 */
	private Long pay_term_id;
	
	/**
	 * 预计付款时间
	 */
	private Date pay_plan_date;
	
	/**
	 * 付款比例
	 */
	private Double pay_plan_percent;
	
	/**
	 * 付款金额
	 */
	private Double pay_plan_money;
	
	/**
	 * 已付金额
	 */
	private Double pay_money;
	
	/**
	 * 未付金额
	 */
	private Double unpay_money;
	
	/**
	 * 供应商ID
	 */
	private String ven_id;
	
	/**
	 * 供应商变更ID
	 */
	private Long ven_no;
	
	/**
	 * 备注
	 */
	private String note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	private String pay_term_name;
	
	private String ven_code;
	
	private String ven_name;
	
	private Double bill_money;
	
	private Double unbill_money;
	
	
	

	public Double getUnbill_money() {
		return unbill_money;
	}

	public void setUnbill_money(Double unbill_money) {
		this.unbill_money = unbill_money;
	}
	
	public Double getBill_money() {
		return bill_money;
	}

	public void setBill_money(Double bill_money) {
		this.bill_money = bill_money;
	}
	
	
	
	public String getPay_term_name() {
		return pay_term_name;
	}

	public void setPay_term_name(String pay_term_name) {
		this.pay_term_name = pay_term_name;
	}

	public String getVen_code() {
		return ven_code;
	}

	public void setVen_code(String ven_code) {
		this.ven_code = ven_code;
	}

	public String getVen_name() {
		return ven_name;
	}

	public void setVen_name(String ven_name) {
		this.ven_name = ven_name;
	}
	
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
	* 设置 卡片编号
	* @param value 
	*/
	public void setAss_card_no(String value) {
		this.ass_card_no = value;
	}
	
	/**
	* 获取 卡片编号
	* @return String
	*/
	public String getAss_card_no() {
		return this.ass_card_no;
	}
	/**
	* 设置 付款期号
	* @param value 
	*/
	public void setStage_no(String value) {
		this.stage_no = value;
	}
	
	/**
	* 获取 付款期号
	* @return String
	*/
	public String getStage_no() {
		return this.stage_no;
	}
	
	public String getStage_name() {
		return stage_name;
	}

	public void setStage_name(String stage_name) {
		this.stage_name = stage_name;
	}

	/**
	* 设置 付款条件
	* @param value 
	*/
	public void setPay_term_id(Long value) {
		this.pay_term_id = value;
	}
	
	/**
	* 获取 付款条件
	* @return Long
	*/
	public Long getPay_term_id() {
		return this.pay_term_id;
	}
	/**
	* 设置 预计付款时间
	* @param value 
	*/
	public void setPay_plan_date(Date value) {
		this.pay_plan_date = value;
	}
	
	/**
	* 获取 预计付款时间
	* @return Date
	*/
	public Date getPay_plan_date() {
		return this.pay_plan_date;
	}
	/**
	* 设置 付款比例
	* @param value 
	*/
	public void setPay_plan_percent(Double value) {
		this.pay_plan_percent = value;
	}
	
	/**
	* 获取 付款比例
	* @return Double
	*/
	public Double getPay_plan_percent() {
		return this.pay_plan_percent;
	}
	/**
	* 设置 付款金额
	* @param value 
	*/
	public void setPay_plan_money(Double value) {
		this.pay_plan_money = value;
	}
	
	/**
	* 获取 付款金额
	* @return Double
	*/
	public Double getPay_plan_money() {
		return this.pay_plan_money;
	}
	/**
	* 设置 已付金额
	* @param value 
	*/
	public void setPay_money(Double value) {
		this.pay_money = value;
	}
	
	/**
	* 获取 已付金额
	* @return Double
	*/
	public Double getPay_money() {
		return this.pay_money;
	}
	/**
	* 设置 未付金额
	* @param value 
	*/
	public void setUnpay_money(Double value) {
		this.unpay_money = value;
	}
	
	/**
	* 获取 未付金额
	* @return Double
	*/
	public Double getUnpay_money() {
		return this.unpay_money;
	}
	/**
	* 设置 供应商ID
	* @param value 
	*/
	public void setVen_id(String value) {
		this.ven_id = value;
	}
	
	/**
	* 获取 供应商ID
	* @return Long
	*/
	public String getVen_id() {
		return this.ven_id;
	}
	/**
	* 设置 供应商变更ID
	* @param value 
	*/
	public void setVen_no(Long value) {
		this.ven_no = value;
	}
	
	/**
	* 获取 供应商变更ID
	* @return Long
	*/
	public Long getVen_no() {
		return this.ven_no;
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