package com.chd.hrp.mat.entity;

import java.io.Serializable;
import java.util.Date;

public class MatAffiOutDetail implements Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 
	 */
	private Long group_id;
	
	/**
	 * 
	 */
	private Long hos_id;
	
	/**
	 * 
	 */
	private Object copy_code;
	
	/**
	 * 
	 */
	private Long out_id;
	
	/**
	 * 
	 */
	private String out_no;
	
	/**
	 * 
	 */
	private Long store_id;
	
	/**
	 * 
	 */
	private Long out_detail_id;
	
	/**
	 * 
	 */
	private Long inv_id;
	
	/**
	 * 
	 */
	private Long inv_no;
	
	/**
	 * 
	 */
	private String inv_code;
	
	/**
	 * 
	 */
	private String inv_name;
	
	/**
	 * 
	 */
	private Long batch_sn;
	
	/**
	 * 
	 */
	private Object batch_no;
	
	/**
	 * 
	 */
	private Double price;
	
	/**
	 * 
	 */
	private Double amount;
	
	/**
	 * 
	 */
	private Double left_amount;
	
	/**
	 * 
	 */
	private Double imme_amount;
	
	/**
	 * 
	 */
	private Double sell_price;
	
	/**
	 * 
	 */
	private Double sell_money;
	
	/**
	 * 
	 */
	private Double sale_price;
	
	/**
	 * 
	 */
	private Double sale_money;
	
	/**
	 * 
	 */
	private Double allot_price;
	
	/**
	 * 
	 */
	private Double allot_money;
	
	/**
	 * 
	 */
	private Double amount_money;
	
	/**
	 * 
	 */
	private Double num_exchange;
	
	/**
	 * 
	 */
	private Double pack_price;
	
	/**
	 * 
	 */
	private Double num;
	
	/**
	 * 
	 */
	private Object bar_code;
	
	/**
	 * 
	 */
	private Date inva_date;
	
	/**
	 * 
	 */
	private Date disinfect_date;
	
	/**
	 * 
	 */
	private Long location_id;
	
	/**
	 * 
	 */
	private Object note;
	
	/**
	 * 
	 */
	private Object pack_code;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setCopy_code(Object value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setOut_id(Long value) {
		this.out_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getOut_id() {
		return this.out_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setOut_no(String value) {
		this.out_no = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getOut_no() {
		return this.out_no;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setStore_id(Long value) {
		this.store_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getStore_id() {
		return this.store_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setOut_detail_id(Long value) {
		this.out_detail_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getOut_detail_id() {
		return this.out_detail_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setInv_id(Long value) {
		this.inv_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getInv_id() {
		return this.inv_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setInv_no(Long value) {
		this.inv_no = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getInv_no() {
		return this.inv_no;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setInv_code(String value) {
		this.inv_code = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getInv_code() {
		return this.inv_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setInv_name(String value) {
		this.inv_name = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getInv_name() {
		return this.inv_name;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setBatch_sn(Long value) {
		this.batch_sn = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getBatch_sn() {
		return this.batch_sn;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setBatch_no(Object value) {
		this.batch_no = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getBatch_no() {
		return this.batch_no;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setPrice(Double value) {
		this.price = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getPrice() {
		return this.price;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setAmount(Double value) {
		this.amount = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getAmount() {
		return this.amount;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setSell_price(Double value) {
		this.sell_price = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getSell_price() {
		return this.sell_price;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setSell_money(Double value) {
		this.sell_money = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getSell_money() {
		return this.sell_money;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setSale_price(Double value) {
		this.sale_price = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getSale_price() {
		return this.sale_price;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setSale_money(Double value) {
		this.sale_money = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getSale_money() {
		return this.sale_money;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setAllot_price(Double value) {
		this.allot_price = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getAllot_price() {
		return this.allot_price;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setAllot_money(Double value) {
		this.allot_money = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getAllot_money() {
		return this.allot_money;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setAmount_money(Double value) {
		this.amount_money = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getAmount_money() {
		return this.amount_money;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setNum_exchange(Double value) {
		this.num_exchange = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getNum_exchange() {
		return this.num_exchange;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setPack_price(Double value) {
		this.pack_price = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getPack_price() {
		return this.pack_price;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setNum(Double value) {
		this.num = value;
	}
	
	/**
	* 获取 
	* @return Double
	*/
	public Double getNum() {
		return this.num;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setBar_code(Object value) {
		this.bar_code = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getBar_code() {
		return this.bar_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setInva_date(Date value) {
		this.inva_date = value;
	}
	
	/**
	* 获取 
	* @return Date
	*/
	public Date getInva_date() {
		return this.inva_date;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setDisinfect_date(Date value) {
		this.disinfect_date = value;
	}
	
	/**
	* 获取 
	* @return Date
	*/
	public Date getDisinfect_date() {
		return this.disinfect_date;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setLocation_id(Long value) {
		this.location_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getLocation_id() {
		return this.location_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setNote(Object value) {
		this.note = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getNote() {
		return this.note;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setPack_code(Object value) {
		this.pack_code = value;
	}
	
	/**
	* 获取 
	* @return Object
	*/
	public Object getPack_code() {
		return this.pack_code;
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

	public Double getLeft_amount() {
		return left_amount;
	}

	public void setLeft_amount(Double left_amount) {
		this.left_amount = left_amount;
	}

	public Double getImme_amount() {
		return imme_amount;
	}

	public void setImme_amount(Double imme_amount) {
		this.imme_amount = imme_amount;
	}

	
}
