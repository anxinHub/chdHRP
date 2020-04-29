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
 * state：
0：中止
1：新建
2：审核
3：执行完毕
4：已合并

  
 * @Table:
 * MAT_ORDER_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MatOrderMain implements Serializable {

	
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
	 * 订单ID
	 */
	private Long order_id;
	
	/**
	 * 订单编号
	 */
	private String order_code;
	
	/**
	 * 订单日期
	 */
	private Date order_date;
	
	/**
	 * 采购方式
	 */
	private Long pur_type;
	
	/**
	 * 单据类型
	 */
	private Long order_type;
	
	
	/**
	 * 摘要
	 */
	private String brif;
	
	/**
	 * 供应商ID
	 */
	private Long sup_id;
	
	/**
	 * 供应商变更ID
	 */
	private Long sup_no;
	
	/**
	 * 采购部门ID
	 */
	private Long dept_id;
	
	/**
	 * 采购部门变更ID
	 */
	private Long dept_no;
	
	/**
	 * 采购员
	 */
	private Long stocker;
	
	/**
	 * 采购单位
	 */
	private Long pur_hos_id;
	
	/**
	 * 收货单位ID
	 */
	private Long take_hos_id;
	
	/**
	 * 付款单位ID
	 */
	private Long pay_hos_id;
	
	/**
	 * 采购类型
	 */
	private String stock_type_code;
	
	/**
	 * 付款条件
	 */
	private String pay_code;
	
	/**
	 * 到货地址
	 */
	private String arr_address;
	
	/**
	 * 制单人
	 */
	private Long maker;
	
	/**
	 * 制单日期
	 */
	private Date make_date;
	
	/**
	 * 审核人
	 */
	private Long checker;
	
	/**
	 * 审核日期
	 */
	private Date check_date;
	
	/**
	 * 状态
	 */
	private Integer state;
	
	/**
	 * 通知状态
	 */
	private Integer is_notice;
	
	/**
	 * 通知日期
	 */
	private Date notice_date;
	
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
	* 设置 订单ID
	* @param value 
	*/
	public void setOrder_id(Long value) {
		this.order_id = value;
	}
	
	/**
	* 获取 订单ID
	* @return Long
	*/
	public Long getOrder_id() {
		return this.order_id;
	}
	/**
	* 设置 订单编号
	* @param value 
	*/
	public void setOrder_code(String value) {
		this.order_code = value;
	}
	
	/**
	* 获取 订单编号
	* @return String
	*/
	public String getOrder_code() {
		return this.order_code;
	}
	/**
	* 设置 订单日期
	* @param value 
	*/
	public void setOrder_date(Date value) {
		this.order_date = value;
	}
	
	/**
	* 获取 订单日期
	* @return Date
	*/
	public Date getOrder_date() {
		return this.order_date;
	}
	
	/**
	* 设置 摘要
	* @param value 
	*/
	public void setBrif(String value) {
		this.brif = value;
	}
	
	/**
	* 获取 摘要
	* @return String
	*/
	public String getBrif() {
		return this.brif;
	}
	/**
	* 设置 供应商ID
	* @param value 
	*/
	public void setSup_id(Long value) {
		this.sup_id = value;
	}
	
	/**
	* 获取 供应商ID
	* @return Long
	*/
	public Long getSup_id() {
		return this.sup_id;
	}
	/**
	* 设置 供应商变更ID
	* @param value 
	*/
	public void setSup_no(Long value) {
		this.sup_no = value;
	}
	
	/**
	* 获取 供应商变更ID
	* @return Long
	*/
	public Long getSup_no() {
		return this.sup_no;
	}
	/**
	* 设置 采购部门ID
	* @param value 
	*/
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	
	/**
	* 获取 采购部门ID
	* @return Long
	*/
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	* 设置 采购部门变更ID
	* @param value 
	*/
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	
	/**
	* 获取 采购部门变更ID
	* @return Long
	*/
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	* 设置 采购员
	* @param value 
	*/
	public void setStocker(Long value) {
		this.stocker = value;
	}
	
	/**
	* 获取 采购员
	* @return Long
	*/
	public Long getStocker() {
		return this.stocker;
	}
	/**
	* 设置 采购单位
	* @param value 
	*/
	public void setPur_hos_id(Long value) {
		this.pur_hos_id = value;
	}
	
	/**
	* 获取 采购单位
	* @return Long
	*/
	public Long getPur_hos_id() {
		return this.pur_hos_id;
	}
	/**
	* 设置 收货单位ID
	* @param value 
	*/
	public void setTake_hos_id(Long value) {
		this.take_hos_id = value;
	}
	
	/**
	* 获取 收货单位ID
	* @return Long
	*/
	public Long getTake_hos_id() {
		return this.take_hos_id;
	}
	/**
	* 设置 付款单位ID
	* @param value 
	*/
	public void setPay_hos_id(Long value) {
		this.pay_hos_id = value;
	}
	
	/**
	* 获取 付款单位ID
	* @return Long
	*/
	public Long getPay_hos_id() {
		return this.pay_hos_id;
	}
	/**
	* 设置 采购类型
	* @param value 
	*/
	public void setStock_type_code(String value) {
		this.stock_type_code = value;
	}
	
	/**
	* 获取 采购类型
	* @return String
	*/
	public String getStock_type_code() {
		return this.stock_type_code;
	}
	/**
	* 设置 付款条件
	* @param value 
	*/
	public void setPay_code(String value) {
		this.pay_code = value;
	}
	
	/**
	* 获取 付款条件
	* @return String
	*/
	public String getPay_code() {
		return this.pay_code;
	}
	
	/**
	* 设置 到货地址
	* @param value 
	*/
	public void setArr_address(String value) {
		this.arr_address = value;
	}
	
	/**
	* 获取 到货地址
	* @return String
	*/
	public String getArr_address() {
		return this.arr_address;
	}
	
	/**
	* 设置 制单人
	* @param value 
	*/
	public void setMaker(Long value) {
		this.maker = value;
	}
	
	/**
	* 获取 制单人
	* @return Long
	*/
	public Long getMaker() {
		return this.maker;
	}
	/**
	* 设置 制单日期
	* @param value 
	*/
	public void setMake_date(Date value) {
		this.make_date = value;
	}
	
	/**
	* 获取 制单日期
	* @return Date
	*/
	public Date getMake_date() {
		return this.make_date;
	}
	/**
	* 设置 审核人
	* @param value 
	*/
	public void setChecker(Long value) {
		this.checker = value;
	}
	
	/**
	* 获取 审核人
	* @return Long
	*/
	public Long getChecker() {
		return this.checker;
	}
	/**
	* 设置 审核日期
	* @param value 
	*/
	public void setCheck_date(Date value) {
		this.check_date = value;
	}
	
	/**
	* 获取 审核日期
	* @return Date
	*/
	public Date getCheck_date() {
		return this.check_date;
	}
	/**
	* 设置 状态
	* @param value 
	*/
	public void setState(Integer value) {
		this.state = value;
	}
	
	/**
	* 获取 状态
	* @return Integer
	*/
	public Integer getState() {
		return this.state;
	}
	/**
	* 设置 通知状态
	* @param value 
	*/
	public void setIs_notice(Integer value) {
		this.is_notice = value;
	}
	
	/**
	* 获取 通知状态
	* @return Integer
	*/
	public Integer getIs_notice() {
		return this.is_notice;
	}
	/**
	* 设置 通知日期
	* @param value 
	*/
	public void setNotice_date(Date value) {
		this.notice_date = value;
	}
	
	/**
	* 获取 通知日期
	* @return Date
	*/
	public Date getNotice_date() {
		return this.notice_date;
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
	
	
	
	public Long getPur_type() {
		return pur_type;
	}

	public void setPur_type(Long pur_type) {
		this.pur_type = pur_type;
	}

	public Long getOrder_type() {
		return order_type;
	}

	public void setOrder_type(Long order_type) {
		this.order_type = order_type;
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