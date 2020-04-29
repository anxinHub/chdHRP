/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.entity;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * MED_IN_MAIN
 * @Table:
 * MED_IN_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class MedInMain implements Serializable {

	
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
	 * 入库单号
	 */
	private String in_no;
	
	/**
	 * 年份
	 */
	private String year;
	
	/**
	 * 月份
	 */
	private String month;
	
	/**
	 * 业务类型
	 */
	private String bus_type_code;
	
	/**
	 * 供应商变更ID
	 */
	private Long sup_no;
	
	/**
	 * 供应商ID
	 */
	private Long sup_id;
	
	/**
	 * 仓库ID
	 */
	private Long store_id;
	
	/**
	 * 仓库变更ID
	 */
	private Long store_no;
	
	/**
	 * 货位
	 */
	private String location_code;
	
	/**
	 * 摘要
	 */
	private String brief;
	
	/**
	 * 采购员
	 */
	private Long stocker;
	
	/**
	 * 采购类型编码
	 */
	private String stock_type_code;
	
	/**
	 * 项目
	 */
	private Long proj_id;
	
	/**
	 * 制单人
	 */
	private Long maker;
	
	/**
	 * 入库日期
	 */
	private Date in_date;
	
	/**
	 * 审核人
	 */
	private Long checker;
	
	/**
	 * 审核日期
	 */
	private Date check_date;
	
	/**
	 * 入库确认人
	 */
	private Long confirmer;
	
	/**
	 * 入库确认日期
	 */
	private Date confirm_date;
	
	/**
	 * 状态
	 */
	private Integer state;
	
	/**
	 * 盘点单号
	 */
	private Integer check_code;
	
	/**
	 * 协议编号
	 */
	private String protocol_code;
	
	/**
	 * 申请科室
	 */
	private Long apply_dept;
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
	* 设置 入库单号
	* @param value 
	*/
	public void setIn_no(String value) {
		this.in_no = value;
	}
	
	/**
	* 获取 入库单号
	* @return String
	*/
	public String getIn_no() {
		return this.in_no;
	}
	/**
	* 设置 年份
	* @param value 
	*/
	public void setYear(String value) {
		this.year = value;
	}
	
	/**
	* 获取 年份
	* @return String
	*/
	public String getYear() {
		return this.year;
	}
	/**
	* 设置 月份
	* @param value 
	*/
	public void setMonth(String value) {
		this.month = value;
	}
	
	/**
	* 获取 月份
	* @return String
	*/
	public String getMonth() {
		return this.month;
	}
	/**
	* 设置 业务类型
	* @param value 
	*/
	public void setBus_type_code(String value) {
		this.bus_type_code = value;
	}
	
	/**
	* 获取 业务类型
	* @return String
	*/
	public String getBus_type_code() {
		return this.bus_type_code;
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
	* 设置 仓库ID
	* @param value 
	*/
	public void setStore_id(Long value) {
		this.store_id = value;
	}
	
	/**
	* 获取 仓库ID
	* @return Long
	*/
	public Long getStore_id() {
		return this.store_id;
	}
	/**
	* 设置 仓库变更ID
	* @param value 
	*/
	public void setStore_no(Long value) {
		this.store_no = value;
	}
	
	/**
	* 获取 仓库变更ID
	* @return Long
	*/
	public Long getStore_no() {
		return this.store_no;
	}
	/**
	* 设置 货位
	* @param value 
	*/
	public void setLocation_code(String value) {
		this.location_code = value;
	}
	
	/**
	* 获取 货位
	* @return String
	*/
	public String getLocation_code() {
		return this.location_code;
	}
	/**
	* 设置 摘要
	* @param value 
	*/
	public void setBrief(String value) {
		this.brief = value;
	}
	
	/**
	* 获取 摘要
	* @return String
	*/
	public String getBrief() {
		return this.brief;
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
	* 设置 采购类型编码
	* @param value 
	*/
	public void setStock_type_code(String value) {
		this.stock_type_code = value;
	}
	
	/**
	* 获取 采购类型编码
	* @return String
	*/
	public String getStock_type_code() {
		return this.stock_type_code;
	}
	/**
	* 设置 项目ID
	* @param value 
	*/
	public void setProj_id(Long value) {
		this.proj_id = value;
	}
	
	/**
	* 获取 项目ID
	* @return Long
	*/
	public Long getProj_id() {
		return this.proj_id;
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
	* 设置 入库日期
	* @param value 
	*/
	public void setIn_date(Date value) {
		this.in_date = value;
	}
	
	/**
	* 获取 入库日期
	* @return Date
	*/
	public Date getIn_date() {
		return this.in_date;
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
	* 设置 入库确认人
	* @param value 
	*/
	public void setConfirmer(Long value) {
		this.confirmer = value;
	}
	
	/**
	* 获取 入库确认人
	* @return Long
	*/
	public Long getConfirmer() {
		return this.confirmer;
	}
	/**
	* 设置 入库确认日期
	* @param value 
	*/
	public void setConfirm_date(Date value) {
		this.confirm_date = value;
	}
	
	/**
	* 获取 入库确认日期
	* @return Date
	*/
	public Date getConfirm_date() {
		return this.confirm_date;
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
	* 设置 盘点单号
	* @param value 
	*/
	public void setCheck_code(Integer value) {
		this.check_code = value;
	}
	
	/**
	* 获取 盘点单号
	* @return Integer
	*/
	public Integer getCheck_code() {
		return this.check_code;
	}
	/**
	* 设置 协议编号
	* @param value 
	*/
	public void setProtocol_code(String value) {
		this.protocol_code = value;
	}
	
	/**
	* 获取 协议编号
	* @return String
	*/
	public String getProtocol_code() {
		return this.protocol_code;
	}
	/**
	* 设置 申请科室
	* @param value 
	*/
	public void setApply_dept(Long value) {
		this.apply_dept = value;
	}
	
	/**
	* 获取 申请科室
	* @return Long
	*/
	public Long getApply_dept() {
		return this.apply_dept;
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