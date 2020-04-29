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
 


public class MedSpecialAffiOutRela implements Serializable {

	
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
	private Long affi_out_id;
	
	/**
	 * 
	 */
	private Long affi_detail_id;
	
	/**
	 * 
	 */
	private Long special_id;
	
	/**
	 * 
	 */
	private Long sp_detail_id;
	
	/**
	 * 
	 */
	private double out_amount;
	
	/**
	 * 
	 */
	private double special_amount;

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

	public Long getAffi_out_id() {
		return affi_out_id;
	}

	public void setAffi_out_id(Long affi_out_id) {
		this.affi_out_id = affi_out_id;
	}

	public Long getAffi_detail_id() {
		return affi_detail_id;
	}

	public void setAffi_detail_id(Long affi_detail_id) {
		this.affi_detail_id = affi_detail_id;
	}

	public Long getSpecial_id() {
		return special_id;
	}

	public void setSpecial_id(Long special_id) {
		this.special_id = special_id;
	}

	public Long getSp_detail_id() {
		return sp_detail_id;
	}

	public void setSp_detail_id(Long sp_detail_id) {
		this.sp_detail_id = sp_detail_id;
	}

	public double getOut_amount() {
		return out_amount;
	}

	public void setOut_amount(double out_amount) {
		this.out_amount = out_amount;
	}

	public double getSpecial_amount() {
		return special_amount;
	}

	public void setSpecial_amount(double special_amount) {
		this.special_amount = special_amount;
	}

}