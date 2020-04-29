package com.chd.hrp.mat.entity;

import java.io.Serializable;
/**
 * 
 * @Description:
 * 04805 代销出入库结存表
 * @Table:
 * MAT_AFFI_FIFO
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public class MatAffiFifo implements Serializable {
	
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
	 * 仓库ID
	 */
	private Long store_id;
	/**
	 * 材料ID
	 */
	private Long inv_id;
	/**
	 * 供应商ID
	 */
	private Long sup_id;
	/**
	 * 批次
	 */
	private Long batch_sn;
	
	/**
	 * 批号
	 */
	private String batch_no;
	/**
	 * 条形码
	 */
	private String bar_code;
	/**
	 * 单价
	 */
	private Float price;
	/**
	 * 批发单价
	 */
	private Float sale_price;
	/**
	 * 入库数量
	 */
	private Double in_amount;
	
	/**
	 * 入库金额
	 */
	private Double in_money;
	/**
	 * 入库批发金额
	 */
	private Double in_sale_money;
	/**
	 * 出库数量
	 */
	private Double out_amount;
	
	/**
	 * 出库金额
	 */
	private Double out_money;
	/**
	 * 出库批发金额
	 */
	private Double out_sale_money;
	
	/**
	 * 剩余数量
	 */
	private Double left_amount;
	
	/**
	 * 剩余金额
	 */
	private Double left_money;
	/**
	 * 剩余批发金额
	 */
	private Double left_sale_money;
	/**
	 * 折零误差
	 */
	private Double remove_zero_error;
	/**
	 * 批发价拆零误差
	 */
	private Double sale_zero_error;
	
	/**
	 * 货位
	 */
	private Long location_id;
	
	/**
	 * 即时库存
	 */
	private Double imme_amount;
	
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

	public Long getBatch_sn() {
		return batch_sn;
	}

	public void setBatch_sn(Long batch_sn) {
		this.batch_sn = batch_sn;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public String getBar_code() {
		return bar_code;
	}

	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Double getIn_amount() {
		return in_amount;
	}

	public void setIn_amount(Double in_amount) {
		this.in_amount = in_amount;
	}

	public Double getIn_money() {
		return in_money;
	}

	public void setIn_money(Double in_money) {
		this.in_money = in_money;
	}

	public Double getOut_amount() {
		return out_amount;
	}

	public void setOut_amount(Double out_amount) {
		this.out_amount = out_amount;
	}

	public Double getOut_money() {
		return out_money;
	}

	public void setOut_money(Double out_money) {
		this.out_money = out_money;
	}

	public Double getLeft_amount() {
		return left_amount;
	}

	public void setLeft_amount(Double left_amount) {
		this.left_amount = left_amount;
	}

	public Double getLeft_money() {
		return left_money;
	}

	public void setLeft_money(Double left_money) {
		this.left_money = left_money;
	}

	public Double getRemove_zero_error() {
		return remove_zero_error;
	}

	public void setRemove_zero_error(Double remove_zero_error) {
		this.remove_zero_error = remove_zero_error;
	}

	public Long getLocation_id() {
		return location_id;
	}

	public void setLocation_id(Long location_id) {
		this.location_id = location_id;
	}

	public Double getImme_amount() {
		return imme_amount;
	}

	public void setImme_amount(Double imme_amount) {
		this.imme_amount = imme_amount;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	public Long getSup_id() {
		return sup_id;
	}

	public void setSup_id(Long sup_id) {
		this.sup_id = sup_id;
	}

	public Float getSale_price() {
		return sale_price;
	}

	public void setSale_price(Float sale_price) {
		this.sale_price = sale_price;
	}

	public Double getIn_sale_money() {
		return in_sale_money;
	}

	public void setIn_sale_money(Double in_sale_money) {
		this.in_sale_money = in_sale_money;
	}

	public Double getOut_sale_money() {
		return out_sale_money;
	}

	public void setOut_sale_money(Double out_sale_money) {
		this.out_sale_money = out_sale_money;
	}

	public Double getLeft_sale_money() {
		return left_sale_money;
	}

	public void setLeft_sale_money(Double left_sale_money) {
		this.left_sale_money = left_sale_money;
	}

	public Double getSale_zero_error() {
		return sale_zero_error;
	}

	public void setSale_zero_error(Double sale_zero_error) {
		this.sale_zero_error = sale_zero_error;
	}
	
	

}
