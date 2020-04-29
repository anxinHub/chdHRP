/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.bill;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 051601 发票卡片分期付款
 * @Table:
 * ASS_BILL_STAGE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


public class AssBillStage implements Serializable {

	
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
	 * 发票流水号
	 */
	private String bill_no;
	
	/**
	 * 资产性质
	 */
	private String naturs_code;
	
	/**
	 * 卡片号
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
	 * 付款金额
	 */
	private Double pay_plan_money;
	
	
	private Double plan_money;
	private Double pay_money;
	private Double unpay_money;
	private String ass_spec;
	private String ass_model;
	private String naturs_name;
	
	public String getNaturs_name() {
		return naturs_name;
	}

	public void setNaturs_name(String naturs_name) {
		this.naturs_name = naturs_name;
	}

	public String getAss_spec() {
		return ass_spec;
	}

	public void setAss_spec(String ass_spec) {
		this.ass_spec = ass_spec;
	}

	public String getAss_model() {
		return ass_model;
	}

	public void setAss_model(String ass_model) {
		this.ass_model = ass_model;
	}
	private String ass_name;
	
	private Double bill_money;
	private Double unbill_money;
	

	public Double getBill_money() {
		return bill_money;
	}

	public void setBill_money(Double bill_money) {
		this.bill_money = bill_money;
	}

	public Double getUnbill_money() {
		return unbill_money;
	}

	public void setUnbill_money(Double unbill_money) {
		this.unbill_money = unbill_money;
	}

	public String getAss_name() {
		return ass_name;
	}

	public void setAss_name(String ass_name) {
		this.ass_name = ass_name;
	}

public Double getPlan_money() {
		return plan_money;
	}

	public void setPlan_money(Double plan_money) {
		this.plan_money = plan_money;
	}

	public Double getPay_money() {
		return pay_money;
	}

	public void setPay_money(Double pay_money) {
		this.pay_money = pay_money;
	}

	public Double getUnpay_money() {
		return unpay_money;
	}

	public void setUnpay_money(Double unpay_money) {
		this.unpay_money = unpay_money;
	}
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
	* 设置 发票流水号
	* @param value 
	*/
	public void setBill_no(String value) {
		this.bill_no = value;
	}
	
	/**
	* 获取 发票流水号
	* @return String
	*/
	public String getBill_no() {
		return this.bill_no;
	}
	/**
	* 设置 资产性质
	* @param value 
	*/
	public void setNaturs_code(String value) {
		this.naturs_code = value;
	}
	
	/**
	* 获取 资产性质
	* @return String
	*/
	public String getNaturs_code() {
		return this.naturs_code;
	}
	/**
	* 设置 卡片号
	* @param value 
	*/
	public void setAss_card_no(String value) {
		this.ass_card_no = value;
	}
	
	/**
	* 获取 卡片号
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
	/**
	* 设置 摘要
	* @param value 
	*/
	public void setStage_name(String value) {
		this.stage_name = value;
	}
	
	/**
	* 获取 摘要
	* @return String
	*/
	public String getStage_name() {
		return this.stage_name;
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