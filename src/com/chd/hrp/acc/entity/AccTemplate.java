/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 凭证模板主表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccTemplate implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 凭证ID
	 */
	private double vouch_id;
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
	 * 会计年度
	 */
	private String acc_year;
	/**
	 * 凭证类型
	 */
	private String vouch_type_code;
	/**
	 * 凭证日期
	 */
	private Date vouch_date;
	/**
	 * 附件数量
	 */
	private double vouch_att_num;
	/**
	 * 模板排序号
	 */
	private Long template_sort;
	/**
	 * 模板id
	 */
	private Long template_id;
	/**
	 * 模版名称
	 */
	private String template_name;
	/**
	 * 说明
	 */
	private String note;
	/**
	 * 操作员
	 */
	private String user_name;
	
	private String vouch_type_name;

	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	/**
	 * 设置 凭证ID
	 */
	public void setVouch_id(double value) {
		this.vouch_id = value;
	}
	/**
	 * 获取 凭证ID
	 */	
	public double getVouch_id() {
		return this.vouch_id;
	}
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
	 * 设置 会计年度
	 */
	public void setAcc_year(String value) {
		this.acc_year = value;
	}
	/**
	 * 获取 会计年度
	 */	
	public String getAcc_year() {
		return this.acc_year;
	}
	/**
	 * 设置 凭证类型
	 */
	public void setVouch_type_code(String value) {
		this.vouch_type_code = value;
	}
	/**
	 * 获取 凭证类型
	 */	
	public String getVouch_type_code() {
		return this.vouch_type_code;
	}
	/**
	 * 设置 凭证日期
	 */
	public void setVouch_date(Date value) {
		this.vouch_date = value;
	}
	/**
	 * 获取 凭证日期
	 */	
	public Date getVouch_date() {
		return this.vouch_date;
	}
	/**
	 * 设置 附件数量
	 */
	public void setVouch_att_num(double value) {
		this.vouch_att_num = value;
	}
	/**
	 * 获取 附件数量
	 */	
	public double getVouch_att_num() {
		return this.vouch_att_num;
	}
	/**
	 * 设置 模板排序号
	 */
	public void setTemplate_sort(Long value) {
		this.template_sort = value;
	}
	/**
	 * 获取 模板排序号
	 */	
	public Long getTemplate_sort() {
		return this.template_sort;
	}
	/**
	 * 设置 说明
	 */
	public void setNote(String value) {
		this.note = value;
	}
	/**
	 * 获取 说明
	 */	
	public String getNote() {
		return this.note;
	}
	
	/**
	 * 获取 模版
	 * @return template_name
	 */
	public String getTemplate_name() {
		return template_name;
	}
	
	/**
	 * 设置 模版
	 * @param template_name 
	 */
	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}
	
	/**
	 * 获取 template_id
	 * @return template_id
	 */
	public Long getTemplate_id() {
		return template_id;
	}
	
	/**
	 * 设置 template_id
	 * @param template_id 
	 */
	public void setTemplate_id(Long template_id) {
		this.template_id = template_id;
	}
	public String getVouch_type_name() {
		return vouch_type_name;
	}
	public void setVouch_type_name(String vouch_type_name) {
		this.vouch_type_name = vouch_type_name;
	}
	
	
}