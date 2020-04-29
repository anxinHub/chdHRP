/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 内部服务量表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class CostInnerServer implements Serializable {

	
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
	private String year_month;
	private String acc_year;
	private String acc_month;
	/**
	 * 服务科室ID
	 */
	private Long server_dept_id;
	/**
	 * 服务科室变更ID
	 */
	private Long server_dept_no;
	/**
	 * 服务科室编码
	 */
	private String server_dept_code;
	/**
	 * 服务科室名称
	 */
	private String server_dept_name;
	/**
	 * 被服务科室ID
	 */
	private Long server_by_dept_id;
	/**
	 * 被服务科室编码
	 */
	private String server_by_dept_code;
	/**
	 * 被服务科室编码
	 */
	private String server_by_dept_name;
	/**
	 * 被服务科室变更ID
	 */
	private Long server_by_dept_no;
	/**
	 * 服务项目编码
	 */
	private String server_item_code;
	/**
	 * 服务项目名称
	 */
	private String server_item_name;
	/**
	 * 服务量
	 */
	private double server_num;
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
	
	/**
	 * @return the acc_year
	 */
	public String getAcc_year() {
		return acc_year;
	}
	/**
	 * @param acc_year the acc_year to set
	 */
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	/**
	 * @return the acc_month
	 */
	public String getAcc_month() {
		return acc_month;
	}
	/**
	 * @param acc_month the acc_month to set
	 */
	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
	}
	/**
	 * 设置 服务科室ID
	 */
	public void setServer_dept_id(Long value) {
		this.server_dept_id = value;
	}
	/**
	 * 获取 服务科室ID
	 */	
	public Long getServer_dept_id() {
		return this.server_dept_id;
	}
	/**
	 * 设置 服务科室变更ID
	 */
	public void setServer_dept_no(Long value) {
		this.server_dept_no = value;
	}
	/**
	 * 获取 服务科室变更ID
	 */	
	public Long getServer_dept_no() {
		return this.server_dept_no;
	}
	/**
	 * 设置 被服务科室ID
	 */
	public void setServer_by_dept_id(Long value) {
		this.server_by_dept_id = value;
	}
	/**
	 * 获取 被服务科室ID
	 */	
	public Long getServer_by_dept_id() {
		return this.server_by_dept_id;
	}
	/**
	 * 设置 被服务科室变更ID
	 */
	public void setServer_by_dept_no(Long value) {
		this.server_by_dept_no = value;
	}
	/**
	 * 获取 被服务科室变更ID
	 */	
	public Long getServer_by_dept_no() {
		return this.server_by_dept_no;
	}
	/**
	 * 设置 服务项目编码
	 */
	public void setServer_item_code(String value) {
		this.server_item_code = value;
	}
	/**
	 * 获取 服务项目编码
	 */	
	public String getServer_item_code() {
		return this.server_item_code;
	}
	/**
	 * 设置 服务量
	 */
	public void setServer_num(double value) {
		this.server_num = value;
	}
	/**
	 * 获取 服务量
	 */	
	public double getServer_num() { 
		return this.server_num;
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
	public String getServer_dept_code() {
		return server_dept_code;
	}
	public void setServer_dept_code(String server_dept_code) {
		this.server_dept_code = server_dept_code;
	}
	public String getServer_dept_name() {
		return server_dept_name;
	}
	public void setServer_dept_name(String server_dept_name) {
		this.server_dept_name = server_dept_name;
	}
	public String getServer_by_dept_code() {
		return server_by_dept_code;
	}
	public void setServer_by_dept_code(String server_by_dept_code) {
		this.server_by_dept_code = server_by_dept_code;
	}
	public String getServer_by_dept_name() {
		return server_by_dept_name;
	}
	public void setServer_by_dept_name(String server_by_dept_name) {
		this.server_by_dept_name = server_by_dept_name;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getServer_item_name() {
		return server_item_name;
	}
	public void setServer_item_name(String server_item_name) {
		this.server_item_name = server_item_name;
	}
	/**
	 * @return the year_month
	 */
	public String getYear_month() {
		return year_month;
	}
	/**
	 * @param year_month the year_month to set
	 */
	public void setYear_month(String year_month) {
		this.year_month = year_month;
	}
	
}