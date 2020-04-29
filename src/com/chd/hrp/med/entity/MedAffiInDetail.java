package com.chd.hrp.med.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @Description:
 * 08802 代销入库明细 
 * @Table:
 * MED_IN_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public class MedAffiInDetail implements Serializable {

	
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
	 * 单据ID
	 */
	private Long in_id;
	
	/**
	 * 明细ID
	 */
	private Long detail_id;
	
	/**
	 * 材料ID
	 */
	private Long inv_id;
	
	/**
	 * 材料变更ID
	 */
	private Long inv_no;
	
	/**
	 * 批号
	 */
	private String batch_no;
	
	/**
	 * 单价
	 */
	private Double price;
	
	/**
	 * 数量
	 */
	private Double amount;
	
	/**
	 * 金额
	 */
	private Double amount_money;
	
	/**
	 * 零售价格
	 */
	private Double sell_price;
	
	/**
	 * 零售金额
	 */
	private Double sell_money;
	
	/**
	 * 包装单位
	 */
	private String pack_unit;
	
	/**
	 * 包装换算率
	 */
	private Double num_exchange;
	
	/**
	 * 包装单价
	 */
	private Double pack_price;
	
	/**
	 * 包装数量
	 */
	private Double num;
	
	/**
	 * 条形码
	 */
	private String bar_code;
	
	/**
	 * 是否个体条码
	 */
	private Integer is_per_bar;
	
	/**
	 * 个体码
	 */
	private String sn;
	
	/**
	 * 有效日期
	 */
	private Date inva_date;
	
	/**
	 * 灭菌日期
	 */
	private Date disinfect_date;
	
	
	
	/**
	 * 货位
	 */
	private Long location_id;
	
	
	
	/**
	 * 备注
	 */
	private String note;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	/**
	* 设置 集团ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集团ID
	* @return Long
	*/
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	* 设置 医院ID
	* @param value 
	*/
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	
	/**
	* 获取 医院ID
	* @return Long
	*/
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	* 设置 账套编码
	* @param value 
	*/
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	
	/**
	* 获取 账套编码
	* @return String
	*/
	public String getCopy_code() {
		return this.copy_code;
	}
	/**
	* 设置 单据ID
	* @param value 
	*/
	public void setIn_id(Long value) {
		this.in_id = value;
	}
	
	/**
	* 获取 单据ID
	* @return Long
	*/
	public Long getIn_id() {
		return this.in_id;
	}
	/**
	* 设置 明细ID
	* @param value 
	*/
	public void setDetail_id(Long value) {
		this.detail_id = value;
	}
	
	/**
	* 获取 明细ID
	* @return Long
	*/
	public Long getDetail_id() {
		return this.detail_id;
	}
	/**
	* 设置 材料ID
	* @param value 
	*/
	public void setInv_id(Long value) {
		this.inv_id = value;
	}
	
	/**
	* 获取 材料ID
	* @return Long
	*/
	public Long getInv_id() {
		return this.inv_id;
	}
	/**
	* 设置 材料变更ID
	* @param value 
	*/
	public void setInv_no(Long value) {
		this.inv_no = value;
	}
	
	/**
	* 获取 材料变更ID
	* @return Long
	*/
	public Long getInv_no() {
		return this.inv_no;
	}
	/**
	* 设置 批号
	* @param value 
	*/
	public void setBatch_no(String value) {
		this.batch_no = value;
	}
	
	/**
	* 获取 批号
	* @return String
	*/
	public String getBatch_no() {
		return this.batch_no;
	}
	/**
	* 设置 单价
	* @param value 
	*/
	public void setPrice(Double value) {
		this.price = value;
	}
	
	/**
	* 获取 单价
	* @return Double
	*/
	public Double getPrice() {
		return this.price;
	}
	/**
	* 设置 数量
	* @param value 
	*/
	public void setAmount(Double value) {
		this.amount = value;
	}
	
	/**
	* 获取 数量
	* @return Double
	*/
	public Double getAmount() {
		return this.amount;
	}
	/**
	* 设置 金额
	* @param value 
	*/
	public void setAmount_money(Double value) {
		this.amount_money = value;
	}
	
	/**
	* 获取 金额
	* @return Double
	*/
	public Double getAmount_money() {
		return this.amount_money;
	}
	/**
	* 设置 零售价格
	* @param value 
	*/
	public void setSell_price(Double value) {
		this.sell_price = value;
	}
	
	/**
	* 获取 零售价格
	* @return Double
	*/
	public Double getSell_price() {
		return this.sell_price;
	}
	/**
	* 设置 零售金额
	* @param value 
	*/
	public void setSell_money(Double value) {
		this.sell_money = value;
	}
	
	/**
	* 获取 零售金额
	* @return Double
	*/
	public Double getSell_money() {
		return this.sell_money;
	}
	/**
	* 设置 包装单位
	* @param value 
	*/
	public void setPack_unit(String value) {
		this.pack_unit = value;
	}
	
	/**
	* 获取 包装单位
	* @return String
	*/
	public String getPack_unit() {
		return this.pack_unit;
	}
	/**
	* 设置 包装换算率
	* @param value 
	*/
	public void setNum_exchange(Double value) {
		this.num_exchange= value;
	}
	
	/**
	* 获取 包装换算率
	* @return Double
	*/
	public Double getNum_exchange() {
		return this.num_exchange;
	}
	/**
	* 设置 包装单价
	* @param value 
	*/
	public void setPack_price(Double value) {
		this.pack_price = value;
	}
	
	/**
	* 获取 包装单价
	* @return Double
	*/
	public Double getPack_price() {
		return this.pack_price;
	}
	/**
	* 设置 包装数量
	* @param value 
	*/
	public void setNum(Double value) {
		this.num = value;
	}
	
	/**
	* 获取 包装数量
	* @return Double
	*/
	public Double getNum() {
		return this.num;
	}
	/**
	* 设置 条形码
	* @param value 
	*/
	public void setBar_code(String value) {
		this.bar_code = value;
	}
	
	/**
	* 获取 条形码
	* @return String
	*/
	public String getBar_code() {
		return this.bar_code;
	}
	/**
	* 设置 是否个体条码
	* @param value 
	*/
	public void setIs_per_bar(Integer value) {
		this.is_per_bar = value;
	}
	
	/**
	* 获取 是否个体条码
	* @return Integer
	*/
	public Integer getIs_per_bar() {
		return this.is_per_bar;
	}
	/**
	* 设置 个体码
	* @param value 
	*/
	public void setSn(String value) {
		this.sn = value;
	}
	
	/**
	* 获取 个体码
	* @return String
	*/
	public String getSn() {
		return this.sn;
	}
	/**
	* 设置 有效日期
	* @param value 
	*/
	public void setInva_date(Date value) {
		this.inva_date = value;
	}
	
	/**
	* 获取 有效日期
	* @return Date
	*/
	public Date getInva_date() {
		return this.inva_date;
	}
	/**
	* 设置 灭菌日期
	* @param value 
	*/
	public void setDisinfect_date(Date value) {
		this.disinfect_date = value;
	}
	
	/**
	* 获取 灭菌日期
	* @return Date
	*/
	public Date getDisinfect_date() {
		return this.disinfect_date;
	}
	
	/**
	* 设置 货位
	* @param value 
	*/
	public void setLocation_id(Long value) {
		this.location_id = value;
	}
	
	/**
	* 获取 货位
	* @return Long
	*/
	public Long getLocation_id() {
		return this.location_id;
	}
	
	
	/**
	* 设置 备注
	* @param value 
	*/
	public void setNote(String value) {
		this.note = value;
	}
	
	/**
	* 获取 备注
	* @return String
	*/
	public String getNote() {
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
}
