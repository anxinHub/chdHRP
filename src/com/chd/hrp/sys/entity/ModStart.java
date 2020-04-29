/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 系统启用<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class ModStart implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 集团ID
	 */
	private double group_id;
	/**
	 * 医院ID
	 */
	private double hos_id;
	/**
	 * 账套编码
	 */
	private String copy_code;
	/**
	 * 模块编码
	 */
	private String mod_code;
	/**
	 * 启用年度
	 */
	private String start_year;
	/**
	 * 启用月度
	 */
	private String start_month;
	/**
	 * 启用人
	 */
	private String create_user;
	/**
	 * 操作时间
	 */
	private Date create_date;
	
	private String is_show;

	/**
	 * 设置 集团ID
	 */
	public void setGroup_id(double value) {
		this.group_id = value;
	}
	/**
	 * 获取 集团ID
	 */	
	public double getGroup_id() {
		return this.group_id;
	}
	/**
	 * 设置 医院ID
	 */
	public void setHos_id(double value) {
		this.hos_id = value;
	}
	/**
	 * 获取 医院ID
	 */	
	public double getHos_id() {
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
	 * 设置 模块编码
	 */
	public void setMod_code(String value) {
		this.mod_code = value;
	}
	/**
	 * 获取 模块编码
	 */	
	public String getMod_code() {
		return this.mod_code;
	}
	/**
	 * 设置 启用年度
	 */
	public void setStart_year(String value) {
		this.start_year = value;
	}
	/**
	 * 获取 启用年度
	 */	
	public String getStart_year() {
		return this.start_year;
	}
	/**
	 * 设置 启用月度
	 */
	public void setStart_month(String value) {
		this.start_month = value;
	}
	/**
	 * 获取 启用月度
	 */	
	public String getStart_month() {
		return this.start_month;
	}
	/**
	 * 设置 启用人
	 */
	public void setCreate_user(String value) {
		this.create_user = value;
	}
	/**
	 * 获取 启用人
	 */	
	public String getCreate_user() {
		return this.create_user;
	}
	/**
	 * 设置 操作时间
	 */
	public void setCreate_date(Date value) {
		this.create_date = value;
	}
	/**
	 * 获取 操作时间
	 */	
	public Date getCreate_date() {
		return this.create_date;
	}
	public String getIs_show() {
		return is_show;
	}
	public void setIs_show(String is_show) {
		this.is_show = is_show;
	}
	
	
}