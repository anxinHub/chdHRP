/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 
 * @Table:
 * MAT_TRAN_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MatTranDetail implements Serializable {

	
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
	private String year;
	
	/**
	 * 
	 */
	private String month;
	
	/**
	 * 
	 */
	private Long tran_id;
	
	/**
	 * 
	 */
	private String tran_no;
	
	/**
	 * 
	 */
	private Long tran_detail_id;
	
	/**
	 * 
	 */
	private Long out_store_id;
	
	/**
	 * 
	 */
	private Long in_store_id;
	
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
	private Double amount;
	
	/**
	 * 
	 */
	private Double price;
	
	/**
	 * 
	 */
	private Double amount_money;
	
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
	private Double sell_price;
	
	/**
	 * 
	 */
	private Double sell_money;
	
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
	private Object pack_code;
	
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
	private Long location_out_id;
	
	/**
	 * 
	 */
	private Long location_in_id;
	
	/**
	 * 
	 */
	private Object note;
	

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
	public void setTran_id(Long value) {
		this.tran_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getTran_id() {
		return this.tran_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setTran_no(String value) {
		this.tran_no = value;
	}
	
	/**
	* 获取 
	* @return String
	*/
	public String getTran_no() {
		return this.tran_no;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setTran_detail_id(Long value) {
		this.tran_detail_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getTran_detail_id() {
		return this.tran_detail_id;
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
	public void setOut_store_id(Long value) {
		this.out_store_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getOut_store_id() {
		return this.out_store_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setIn_store_id(Long value) {
		this.in_store_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getIn_store_id() {
		return this.in_store_id;
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
	* @return String
	*/
	public String getInv_code() {
		return this.inv_code;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setInv_code(String value) {
		this.inv_code = value;
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
	public void setLocation_out_id(Long value) {
		this.location_out_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getLocation_out_id() {
		return this.location_out_id;
	}
	/**
	* 设置 
	* @param value 
	*/
	public void setLocation_in_id(Long value) {
		this.location_in_id = value;
	}
	
	/**
	* 获取 
	* @return Long
	*/
	public Long getLocation_in_id() {
		return this.location_in_id;
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
}