/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.ass.entity.prepay;

import java.io.Serializable;

/**
 * 
 * @Description: 预退款支付方式表
 * @Table: ASS_BACK_PRE_PAY_STAGE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
public class AssBackPrePayStage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6163247016627253144L;
	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String pay_no;// 付款单号
	private String bill_no;// 发票流水号
	private String pay_code;// 支付方式
	private String pay_name;//支付方式名称
	private Double pay_money;// 支付金额
	private String note;// 说明

	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public Long getHos_id() {
		return hos_id;
	}

	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public String getPay_no() {
		return pay_no;
	}

	public void setPay_no(String pay_no) {
		this.pay_no = pay_no;
	}

	public String getBill_no() {
		return bill_no;
	}

	public void setBill_no(String bill_no) {
		this.bill_no = bill_no;
	}

	public String getPay_code() {
		return pay_code;
	}

	public void setPay_code(String pay_code) {
		this.pay_code = pay_code;
	}

	public Double getPay_money() {
		return pay_money;
	}

	public void setPay_money(Double pay_money) {
		this.pay_money = pay_money;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPay_name() {
		return pay_name;
	}

	public void setPay_name(String pay_name) {
		this.pay_name = pay_name;
	}
	
	

}
