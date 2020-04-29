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
 * @Description: 预付款明细表
 * @Table: ASS_PRE_PAY_DETAIL
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
public class AssPrePayDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2295807780272062879L;
	private Long group_id;
	private Long hos_id;
	private String copy_code;
	private String pay_no;// 付款单号
	private String bill_no;// 发票流水号
	private Double bill_money;// 发票金额
	private Double pay_money;
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

	public Double getBill_money() {
		return bill_money;
	}

	public void setBill_money(Double bill_money) {
		this.bill_money = bill_money;
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

}
