/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.entity;

import java.io.Serializable;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MED_CHECK_Rela
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MedPurOrderRela implements Serializable {

	
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
	private String copy_code;
	
	/**
	 * 
	 */
	private Long order_id;
	
	/**
	 * 
	 */
	private Long order_detail_id;
	
	/**
	 * 
	 */
	private Long pur_id;
	
	/**
	 * 
	 */
	private Long pur_detail_id;
	
	/**
	 * 
	 */
	private double pur_amount;
	
	/**
	 * 
	 */
	private double order_amount;

	public Long getHos_id() {
		return hos_id;
	}

	public void setHos_id(Long hos_id) {
		this.hos_id = hos_id;
	}

	public Long getGroup_id() {
		return group_id;
	}

	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}

	public String getCopy_code() {
		return copy_code;
	}

	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}

	public Long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(Long order_id) {
		this.order_id = order_id;
	}

	public Long getOrder_detail_id() {
		return order_detail_id;
	}

	public void setOrder_detail_id(Long order_detail_id) {
		this.order_detail_id = order_detail_id;
	}

	public Long getPur_id() {
		return pur_id;
	}

	public void setPur_id(Long pur_id) {
		this.pur_id = pur_id;
	}

	public Long getPur_detail_id() {
		return pur_detail_id;
	}

	public void setPur_detail_id(Long pur_detail_id) {
		this.pur_detail_id = pur_detail_id;
	}

	public double getPur_amount() {
		return pur_amount;
	}

	public void setPur_amount(double pur_amount) {
		this.pur_amount = pur_amount;
	}

	public double getOrder_amount() {
		return order_amount;
	}

	public void setOrder_amount(double order_amount) {
		this.order_amount = order_amount;
	}
}