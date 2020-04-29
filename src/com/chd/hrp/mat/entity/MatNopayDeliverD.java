package com.chd.hrp.mat.entity;

import java.io.Serializable;
import java.util.Date;

public class MatNopayDeliverD implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long group_id ;
	
	private Long hos_id ;
	
	private String copy_code ;
	
	private Long deliver_id ;
	
	private String deliver_no ;
	
	private Long detail_id ;
	
	private Long inv_id ;
	
	private Long inv_no ;
	
	private String batch_no ;
	
	private Double price ;
	
	private int amount ;
	
	private Double amount_money ;
	
	private Double sale_price ;
	
	private Double sale_money ;
	
	private Double sell_price ;
	
	private Double sell_money ;
	
	private String  pack_code ;
	
	private Double num_exchange ;
	
	private Double pack_price ;
	
	private int  num ;
	
	private String bar_code ;
	
	private Date inva_date ;
	
	private Date disinfect_date ;
	
	private Long location_id ;
	
	private String note ;
	
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

	public Long getDeliver_id() {
		return deliver_id;
	}

	public void setDeliver_id(Long deliver_id) {
		this.deliver_id = deliver_id;
	}

	public String getDeliver_no() {
		return deliver_no;
	}

	public void setDeliver_no(String deliver_no) {
		this.deliver_no = deliver_no;
	}

	public Long getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(Long detail_id) {
		this.detail_id = detail_id;
	}

	public Long getInv_id() {
		return inv_id;
	}

	public void setInv_id(Long inv_id) {
		this.inv_id = inv_id;
	}

	public Long getInv_no() {
		return inv_no;
	}

	public void setInv_no(Long inv_no) {
		this.inv_no = inv_no;
	}

	public String getBatch_no() {
		return batch_no;
	}

	public void setBatch_no(String batch_no) {
		this.batch_no = batch_no;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Double getAmount_money() {
		return amount_money;
	}

	public void setAmount_money(Double amount_money) {
		this.amount_money = amount_money;
	}

	public Double getSale_price() {
		return sale_price;
	}

	public void setSale_price(Double sale_price) {
		this.sale_price = sale_price;
	}

	public Double getSale_money() {
		return sale_money;
	}

	public void setSale_money(Double sale_money) {
		this.sale_money = sale_money;
	}

	public Double getSell_price() {
		return sell_price;
	}

	public void setSell_price(Double sell_price) {
		this.sell_price = sell_price;
	}

	public Double getSell_money() {
		return sell_money;
	}

	public void setSell_money(Double sell_money) {
		this.sell_money = sell_money;
	}

	public String getPack_code() {
		return pack_code;
	}

	public void setPack_code(String pack_code) {
		this.pack_code = pack_code;
	}

	public Double getNum_exchange() {
		return num_exchange;
	}

	public void setNum_exchange(Double num_exchange) {
		this.num_exchange = num_exchange;
	}

	public Double getPack_price() {
		return pack_price;
	}

	public void setPack_price(Double pack_price) {
		this.pack_price = pack_price;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getBar_code() {
		return bar_code;
	}

	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
	}

	public Date getInva_date() {
		return inva_date;
	}

	public void setInva_date(Date inva_date) {
		this.inva_date = inva_date;
	}

	public Date getDisinfect_date() {
		return disinfect_date;
	}

	public void setDisinfect_date(Date disinfect_date) {
		this.disinfect_date = disinfect_date;
	}

	public Long getLocation_id() {
		return location_id;
	}

	public void setLocation_id(Long location_id) {
		this.location_id = location_id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}
	
}
