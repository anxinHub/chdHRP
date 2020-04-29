/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.sup.entity;

import java.io.Serializable;
import java.util.*;

/**
 * @Description: 100102 送货单明细表
 * @Table: SUP_DELIVERY_DETAIL
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public class SupDeliveryDetail implements Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 送货单明细ID
	 */
	private Long detail_id;

	/**
	 * 送货单号
	 */
	private String delivery_no;

	/**
	 * 集团
	 */
	private Long group_id;

	/**
	 * 医院
	 */
	private String hos_id;

	/**
	 * 账套
	 */
	private String copy_code;

	/**
	 * 统计年度
	 */
	private String acc_year;

	/**
	 * 统计月份
	 */
	private String acc_month;

	/**
	 * 材料D
	 */
	private Long inv_id;

	/**
	 * 材料NO
	 */
	private Long inv_no;

	/**
	 * 规格型号
	 */
	private Object inv_model;

	/**
	 * 计量单位
	 */
	private Object unit_code;

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
	 * 批号
	 */
	private Object batch_no;
	/**
	 * 生产日期
	 */
	private Date fac_date;
	/**
	 * 有效日期
	 */
	private Date inva_date;

	/**
	 * 灭菌效期
	 */
	private Date disinfect_date;

	/**
	 * 条形码
	 */
	private Object sn;

	/**
	 * 生产厂家
	 */
	private Long fac_id;

	/**
	 * 生产厂家
	 */
	private Long fac_no;

	/**
	 * 备注
	 */
	private Object note;
	
	/**
	 * 供应商ID
	 */
	private Long sup_id;
	
	/**
	 * 供应商NO
	 */
	private Long sup_no;
	/**
	 * 编制日期
	 */
	private Date create_date;

	/**
	 * 导入验证信息
	 */
	private String error_type;
	
	private Long cert_id;
	private String cert_code;
	private String bar_code;
	private String serial_no;

	public Long getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(Long detail_id) {
		this.detail_id = detail_id;
	}

	/**
	 * 设置 送货单号
	 * 
	 * @param value
	 */
	public void setDelivery_no(String value) {
		this.delivery_no = value;
	}

	/**
	 * 获取 送货单号
	 * 
	 * @return String
	 */
	public String getDelivery_no() {
		return this.delivery_no;
	}

	/**
	 * 设置 集团
	 * 
	 * @param value
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}

	/**
	 * 获取 集团
	 * 
	 * @return Long
	 */
	public Long getGroup_id() {
		return this.group_id;
	}

	/**
	 * 设置 医院
	 * 
	 * @param value
	 */
	public void setHos_id(String value) {
		this.hos_id = value;
	}

	/**
	 * 获取 医院
	 * 
	 * @return String
	 */
	public String getHos_id() {
		return this.hos_id;
	}

	/**
	 * 设置 账套
	 * 
	 * @param value
	 */
	public void setCopy_code(String value) {
		this.copy_code = value;
	}

	/**
	 * 获取 账套
	 * 
	 * @return String
	 */
	public String getCopy_code() {
		return this.copy_code;
	}

	/**
	 * 设置 统计年度
	 * 
	 * @param value
	 */
	public void setAcc_year(String value) {
		this.acc_year = value;
	}

	/**
	 * 获取 统计年度
	 * 
	 * @return String
	 */
	public String getAcc_year() {
		return this.acc_year;
	}

	/**
	 * 设置 统计月份
	 * 
	 * @param value
	 */
	public void setAcc_month(String value) {
		this.acc_month = value;
	}

	/**
	 * 获取 统计月份
	 * 
	 * @return String
	 */
	public String getAcc_month() {
		return this.acc_month;
	}

	/**
	 * 设置 材料D
	 * 
	 * @param value
	 */
	public void setInv_id(Long value) {
		this.inv_id = value;
	}

	/**
	 * 获取 材料D
	 * 
	 * @return Long
	 */
	public Long getInv_id() {
		return this.inv_id;
	}

	/**
	 * 设置 材料NO
	 * 
	 * @param value
	 */
	public void setInv_no(Long value) {
		this.inv_no = value;
	}

	/**
	 * 获取 材料NO
	 * 
	 * @return Long
	 */
	public Long getInv_no() {
		return this.inv_no;
	}

	/**
	 * 设置 规格型号
	 * 
	 * @param value
	 */
	public void setInv_model(Object value) {
		this.inv_model = value;
	}

	/**
	 * 获取 规格型号
	 * 
	 * @return Object
	 */
	public Object getInv_model() {
		return this.inv_model;
	}

	/**
	 * 设置 计量单位
	 * 
	 * @param value
	 */
	public void setUnit_code(Object value) {
		this.unit_code = value;
	}

	/**
	 * 获取 计量单位
	 * 
	 * @return Object
	 */
	public Object getUnit_code() {
		return this.unit_code;
	}

	/**
	 * 设置 单价
	 * 
	 * @param value
	 */
	public void setPrice(Double value) {
		this.price = value;
	}

	/**
	 * 获取 单价
	 * 
	 * @return Double
	 */
	public Double getPrice() {
		return this.price;
	}

	/**
	 * 设置 数量
	 * 
	 * @param value
	 */
	public void setAmount(Double value) {
		this.amount = value;
	}

	/**
	 * 获取 数量
	 * 
	 * @return Double
	 */
	public Double getAmount() {
		return this.amount;
	}

	/**
	 * 设置 金额
	 * 
	 * @param value
	 */
	public void setAmount_money(Double value) {
		this.amount_money = value;
	}

	/**
	 * 获取 金额
	 * 
	 * @return Double
	 */
	public Double getAmount_money() {
		return this.amount_money;
	}

	/**
	 * 设置 批号
	 * 
	 * @param value
	 */
	public void setBatch_no(Object value) {
		this.batch_no = value;
	}

	/**
	 * 获取 批号
	 * 
	 * @return Object
	 */
	public Object getBatch_no() {
		return this.batch_no;
	}

	/**
	 * 设置 有效日期
	 * 
	 * @param value
	 */
	public void setInva_date(Date value) {
		this.inva_date = value;
	}

	/**
	 * 获取 有效日期
	 * 
	 * @return Date
	 */
	public Date getInva_date() {
		return this.inva_date;
	}

	/**
	 * 设置 灭菌效期
	 * 
	 * @param value
	 */
	public void setDisinfect_date(Date value) {
		this.disinfect_date = value;
	}

	/**
	 * 获取 灭菌效期
	 * 
	 * @return Date
	 */
	public Date getDisinfect_date() {
		return this.disinfect_date;
	}

	/**
	 * 设置 条形码
	 * 
	 * @param value
	 */
	public void setSn(Object value) {
		this.sn = value;
	}

	/**
	 * 获取 条形码
	 * 
	 * @return Object
	 */
	public Object getSn() {
		return this.sn;
	}

	/**
	 * 设置 生产厂家
	 * 
	 * @param value
	 */
	public void setFac_id(Long value) {
		this.fac_id = value;
	}

	/**
	 * 获取 生产厂家
	 * 
	 * @return Long
	 */
	public Long getFac_id() {
		return this.fac_id;
	}

	/**
	 * 设置 备注
	 * 
	 * @param value
	 */
	public void setNote(Object value) {
		this.note = value;
	}

	/**
	 * 获取 备注
	 * 
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

	/**
	 * 获取 fac_no
	 * 
	 * @return fac_no
	 */
	public Long getFac_no() {
		return fac_no;
	}

	/**
	 * 设置 fac_no
	 * 
	 * @param fac_no
	 */
	public void setFac_no(Long fac_no) {
		this.fac_no = fac_no;
	}

	/**
	 * @return the sup_id
	 */
	public Long getSup_id() {
		return sup_id;
	}

	/**
	 * @param sup_id the sup_id to set
	 */
	public void setSup_id(Long sup_id) {
		this.sup_id = sup_id;
	}

	/**
	 * @return the sup_no
	 */
	public Long getSup_no() {
		return sup_no;
	}

	/**
	 * @param sup_no the sup_no to set
	 */
	public void setSup_no(Long sup_no) {
		this.sup_no = sup_no;
	}

	/**
	 * @return the create_date
	 */
	public Date getCreate_date() {
		return create_date;
	}

	/**
	 * @param create_date the create_date to set
	 */
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	/**
	 * @return the cert_id
	 */
	public Long getCert_id() {
		return cert_id;
	}

	/**
	 * @param cert_id the cert_id to set
	 */
	public void setCert_id(Long cert_id) {
		this.cert_id = cert_id;
	}

	/**
	 * @return the bar_code
	 */
	public String getBar_code() {
		return bar_code;
	}

	/**
	 * @param bar_code the bar_code to set
	 */
	public void setBar_code(String bar_code) {
		this.bar_code = bar_code;
	}

	/**
	 * @return the serial_no
	 */
	public String getSerial_no() {
		return serial_no;
	}

	/**
	 * @param serial_no the serial_no to set
	 */
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}

	/**
	 * @return the cert_code
	 */
	public String getCert_code() {
		return cert_code;
	}

	/**
	 * @param cert_code the cert_code to set
	 */
	public void setCert_code(String cert_code) {
		this.cert_code = cert_code;
	}

	/**
	 * @return the fac_date
	 */
	public Date getFac_date() {
		return fac_date;
	}

	/**
	 * @param fac_date the fac_date to set
	 */
	public void setFac_date(Date fac_date) {
		this.fac_date = fac_date;
	}
	
	

}