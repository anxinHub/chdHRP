/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 科室成本明细数据表_平级分摊<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostDriDetail implements Serializable {

	
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
	 * 统计年月
	 */
	private String acc_year;
	/**
	 * 统计年月
	 */
	private String acc_month;
	/**
	 * 服务科室ID
	 */
	private Long dept_id;
	/**
	 * 服务科室变更ID
	 */
	private Long dept_no;
	/**
	 * 受益科室ID
	 */
	private Long server_dept_id;
	/**
	 * 受益科室变更ID
	 */
	private Long server_dept_no;
	/**
	 * 成本项目ID
	 */
	private Long cost_item_id;
	/**
	 * 成本项目变更ID
	 */
	private Long cost_item_no;
	/**
	 * 资金来源
	 */
	private String source;
	/**
	 * 平级分摊成本
	 */
	private double dri_amount;
	/**
	 * 导入验证信息
	 */
	private String error_type;
	/**
	 * 设置 集团ID
	 */
	public void setGroup_id(Long value) {
		this.group_id = value;
	}
	/**
	 * 获取 集团ID
	 */	
	public Long getGroup_id() {
		return this.group_id;
	}
	/**
	 * 设置 医院ID
	 */
	public void setHos_id(Long value) {
		this.hos_id = value;
	}
	/**
	 * 获取 医院ID
	 */	
	public Long getHos_id() {
		return this.hos_id;
	}
	/**
	 * 设置 账套编码
	 */
	public void setCopy_code(String value) {
		this.copy_code = value;
	}
	/**
	 * 获取 账套编码
	 */	
	public String getCopy_code() {
		return this.copy_code;
	}
	
	public String getAcc_year() {
		return acc_year;
	}
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	public String getAcc_month() {
		return acc_month;
	}
	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
	}
	/**
	 * 设置 服务科室ID
	 */
	public void setDept_id(Long value) {
		this.dept_id = value;
	}
	/**
	 * 获取 服务科室ID
	 */	
	public Long getDept_id() {
		return this.dept_id;
	}
	/**
	 * 设置 服务科室变更ID
	 */
	public void setDept_no(Long value) {
		this.dept_no = value;
	}
	/**
	 * 获取 服务科室变更ID
	 */	
	public Long getDept_no() {
		return this.dept_no;
	}
	/**
	 * 设置 受益科室ID
	 */
	public void setServer_dept_id(Long value) {
		this.server_dept_id = value;
	}
	/**
	 * 获取 受益科室ID
	 */	
	public Long getServer_dept_id() {
		return this.server_dept_id;
	}
	/**
	 * 设置 受益科室变更ID
	 */
	public void setServer_dept_no(Long value) {
		this.server_dept_no = value;
	}
	/**
	 * 获取 受益科室变更ID
	 */	
	public Long getServer_dept_no() {
		return this.server_dept_no;
	}
	/**
	 * 设置 成本项目ID
	 */
	public void setCost_item_id(Long value) {
		this.cost_item_id = value;
	}
	/**
	 * 获取 成本项目ID
	 */	
	public Long getCost_item_id() {
		return this.cost_item_id;
	}
	/**
	 * 设置 成本项目变更ID
	 */
	public void setCost_item_no(Long value) {
		this.cost_item_no = value;
	}
	/**
	 * 获取 成本项目变更ID
	 */	
	public Long getCost_item_no() {
		return this.cost_item_no;
	}
	/**
	 * 设置 资金来源
	 */
	public void setSource(String value) {
		this.source = value;
	}
	/**
	 * 获取 资金来源
	 */	
	public String getSource() {
		return this.source;
	}
	/**
	 * 设置 平级分摊成本
	 */
	public void setDri_amount(double value) {
		this.dri_amount = value;
	}
	/**
	 * 获取 平级分摊成本
	 */	
	public double getDri_amount() {
		return this.dri_amount;
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