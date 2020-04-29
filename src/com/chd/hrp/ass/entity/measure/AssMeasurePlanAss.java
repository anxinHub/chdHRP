/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.entity.measure;

import java.io.Serializable;
import java.util.*;
/**
 * 
 * @Description:
 * 051204 检查计量计划资产明细
 * @Table:
 * ASS_MEASURE_PLAN_ASS
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public class AssMeasurePlanAss implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集体ID
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
	 * 计划ID
	 */
	private Long plan_id;
	
	private Long detail_id;
	
	/**
	 * 资产卡片号
	 */
	private String ass_card_no;
	
	private String ass_code ;
	
	private String ass_name ;
	
	private String ass_spec;
	
	private String ass_mondl;
	
	private String fac_name ;
	
	
	/**
	 * 是否内部检测
	 */
	private Integer is_inner;
	
	/**
	 * 外部计量单位
	 */
	private String outer_measure_org;
	
	/**
	 * 计量周期
	 */
	private Integer measure_cycle;
	
	/**
	 * 计量类别
	 */
	private Integer measure_kind;
	
	/**
	 * 计划计量日期
	 */
	private Date plan_exec_date;
	
	/**
	 * 计量说明
	 */
	private String measure_desc;
	

  /**
	 * 导入验证信息
	 */
	private String error_type;
	
	private String dept_name;
	
	
	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	/**
	* 设置 集体ID
	* @param value 
	*/
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	
	/**
	* 获取 集体ID
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
	* 设置 计划ID
	* @param value 
	*/
	public void setPlan_id(Long value) {
		this.plan_id = value;
	}
	
	/**
	* 获取 计划ID
	* @return Long
	*/
	public Long getPlan_id() {
		return this.plan_id;
	}
	
	public Long getDetail_id() {
		return detail_id;
	}

	public void setDetail_id(Long detail_id) {
		this.detail_id = detail_id;
	}

	/**
	* 设置 资产卡片号
	* @param value 
	*/
	public void setAss_card_no(String value) {
		this.ass_card_no = value;
	}
	
	/**
	* 获取 资产卡片号
	* @return String
	*/
	public String getAss_card_no() {
		return this.ass_card_no;
	}
	/**
	* 设置 是否内部检测
	* @param value 
	*/
	public void setIs_inner(Integer value) {
		this.is_inner = value;
	}
	
	/**
	* 获取 是否内部检测
	* @return Integer
	*/
	public Integer getIs_inner() {
		return this.is_inner;
	}
	/**
	* 设置 外部计量单位
	* @param value 
	*/
	public void setOuter_measure_org(String value) {
		this.outer_measure_org = value;
	}
	
	/**
	* 获取 外部计量单位
	* @return String
	*/
	public String getOuter_measure_org() {
		return this.outer_measure_org;
	}
	/**
	* 设置 计量周期
	* @param value 
	*/
	public void setMeasure_cycle(Integer value) {
		this.measure_cycle = value;
	}
	
	/**
	* 获取 计量周期
	* @return Integer
	*/
	public Integer getMeasure_cycle() {
		return this.measure_cycle;
	}
	/**
	* 设置 计量类别
	* @param value 
	*/
	public void setMeasure_kind(Integer value) {
		this.measure_kind = value;
	}
	
	/**
	* 获取 计量类别
	* @return Integer
	*/
	public Integer getMeasure_kind() {
		return this.measure_kind;
	}
	/**
	* 设置 计划计量日期
	* @param value 
	*/
	public void setPlan_exec_date(Date value) {
		this.plan_exec_date = value;
	}
	
	/**
	* 获取 计划计量日期
	* @return Date
	*/
	public Date getPlan_exec_date() {
		return this.plan_exec_date;
	}
	/**
	* 设置 计量说明
	* @param value 
	*/
	public void setMeasure_desc(String value) {
		this.measure_desc = value;
	}
	
	/**
	* 获取 计量说明
	* @return String
	*/
	public String getMeasure_desc() {
		return this.measure_desc;
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

	public String getAss_code() {
		return ass_code;
	}

	public void setAss_code(String ass_code) {
		this.ass_code = ass_code;
	}

	public String getAss_name() {
		return ass_name;
	}

	public void setAss_name(String ass_name) {
		this.ass_name = ass_name;
	}

	public String getAss_spec() {
		return ass_spec;
	}

	public void setAss_spec(String ass_spec) {
		this.ass_spec = ass_spec;
	}


	public String getAss_mondl() {
		return ass_mondl;
	}

	public void setAss_mondl(String ass_mondl) {
		this.ass_mondl = ass_mondl;
	}

	public String getFac_name() {
		return fac_name;
	}

	public void setFac_name(String fac_name) {
		this.fac_name = fac_name;
	}
	
	
	
}