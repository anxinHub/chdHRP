package com.chd.hrp.mat.entity;

import java.io.Serializable;

/**
 * 
 * @Description:
 * 04806 代销材料结存表
 * @Table:
 * MAT_AFFI_IN_HOLD
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public class MatAffiInvHold implements Serializable {
	
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
	 * 材料ID
	 */
	private Long inv_id;
	
	/**
	 * 仓库ID
	 */
	private Long store_id;
	
	/**
	 * 当前库存数量
	 */
	private Float cur_amount;
	
	/**
	 * 当前库存金额
	 */
	private Float cur_money;
	
	/**
	 * 货位ID
	 */
	private Long location_id;
	
	/**
	 * 即时库存
	 */
	private Float imme_amount;
	
	/**
	 * 导入验证信息
	 */
	private String error_type;

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

	public Long getInv_id() {
		return inv_id;
	}

	public void setInv_id(Long inv_id) {
		this.inv_id = inv_id;
	}

	public Long getStore_id() {
		return store_id;
	}

	public void setStore_id(Long store_id) {
		this.store_id = store_id;
	}

	public Float getCur_amount() {
		return cur_amount;
	}

	public void setCur_amount(Float cur_amount) {
		this.cur_amount = cur_amount;
	}

	public Float getCur_money() {
		return cur_money;
	}

	public void setCur_money(Float cur_money) {
		this.cur_money = cur_money;
	}

	public Long getLocation_id() {
		return location_id;
	}

	public void setLocation_id(Long location_id) {
		this.location_id = location_id;
	}

	public Float getImme_amount() {
		return imme_amount;
	}

	public void setImme_amount(Float imme_amount) {
		this.imme_amount = imme_amount;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
	
}
