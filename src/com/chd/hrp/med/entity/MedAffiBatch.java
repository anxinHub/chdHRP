package com.chd.hrp.med.entity;

import java.io.Serializable;
/**
 * 
 * @Description:
 * 08807 药品批次结余表
 * @Table:
 * MED_AFFI_BATCH
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public class MedAffiBatch implements Serializable {
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
	 * 年
	 */
	private String year;
	
	/**
	 * 月
	 */
	private String month;
	
	/**
	 * 材料ID
	 */
	private Long inv_id;
	
	/**
	 * 仓库ID
	 */
	private Long store_id;
	
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
	 * 价格
	 */
	private Double price;
	private Double sale_price;
	
	/**
	 * 年初库存数量
	 */
	private Double y_begin_amount;
	
	/**
	 * 年初金额
	 */
	private Double y_begin_money;
	private Double y_begin_sale_money;
	/**
	 * 期初库存数量
	 */
	private Double begin_amount;
	
	/**
	 * 期初金额
	 */
	private Double begin_money;
	private Double begin_sale_money;
	/**
	 * 本期收入数量
	 */
	private Double in_amount;
	
	/**
	 * 本期收入金额
	 */
	private Double in_money;
	private Double in_sale_money;
	/**
	 * 本期支出数量
	 */
	private Double out_amount;
	
	/**
	 * 本期支出金额
	 */
	private Double out_money;
	private Double out_sale_money;
	/**
	 * 累计收入数量
	 */
	private Double y_in_amount;
	
	/**
	 * 累计收入金额
	 */
	private Double y_in_money;
	private Double y_in_sale_money;
	/**
	 * 累计支出数量
	 */
	private Double y_out_amount;
	
	/**
	 * 累计支出金额
	 */
	private Double y_out_money;
	private Double y_out_sale_money;
	/**
	 * 拆零误差
	 */
	private Double remove_zero_error;
	private Double sale_zero_error;
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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getY_begin_amount() {
		return y_begin_amount;
	}

	public void setY_begin_amount(Double y_begin_amount) {
		this.y_begin_amount = y_begin_amount;
	}

	public Double getY_begin_money() {
		return y_begin_money;
	}

	public void setY_begin_money(Double y_begin_money) {
		this.y_begin_money = y_begin_money;
	}

	public Double getBegin_amount() {
		return begin_amount;
	}

	public void setBegin_amount(Double begin_amount) {
		this.begin_amount = begin_amount;
	}

	public Double getBegin_money() {
		return begin_money;
	}

	public void setBegin_money(Double begin_money) {
		this.begin_money = begin_money;
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

	public Double getY_in_amount() {
		return y_in_amount;
	}

	public void setY_in_amount(Double y_in_amount) {
		this.y_in_amount = y_in_amount;
	}

	public Double getY_in_money() {
		return y_in_money;
	}

	public void setY_in_money(Double y_in_money) {
		this.y_in_money = y_in_money;
	}

	public Double getY_out_amount() {
		return y_out_amount;
	}

	public void setY_out_amount(Double y_out_amount) {
		this.y_out_amount = y_out_amount;
	}

	public Double getY_out_money() {
		return y_out_money;
	}

	public void setY_out_money(Double y_out_money) {
		this.y_out_money = y_out_money;
	}

	public Double getRemove_zero_error() {
		return remove_zero_error;
	}

	public void setRemove_zero_error(Double remove_zero_error) {
		this.remove_zero_error = remove_zero_error;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	public Double getSale_price() {
		return sale_price;
	}

	public void setSale_price(Double sale_price) {
		this.sale_price = sale_price;
	}

	public Double getY_begin_sale_money() {
		return y_begin_sale_money;
	}

	public void setY_begin_sale_money(Double y_begin_sale_money) {
		this.y_begin_sale_money = y_begin_sale_money;
	}

	public Double getBegin_sale_money() {
		return begin_sale_money;
	}

	public void setBegin_sale_money(Double begin_sale_money) {
		this.begin_sale_money = begin_sale_money;
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

	public Double getY_in_sale_money() {
		return y_in_sale_money;
	}

	public void setY_in_sale_money(Double y_in_sale_money) {
		this.y_in_sale_money = y_in_sale_money;
	}

	public Double getY_out_sale_money() {
		return y_out_sale_money;
	}

	public void setY_out_sale_money(Double y_out_sale_money) {
		this.y_out_sale_money = y_out_sale_money;
	}

	public Double getSale_zero_error() {
		return sale_zero_error;
	}

	public void setSale_zero_error(Double sale_zero_error) {
		this.sale_zero_error = sale_zero_error;
	}
	
	
}
