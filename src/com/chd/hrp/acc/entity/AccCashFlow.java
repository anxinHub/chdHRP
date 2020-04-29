/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 现金流量标注<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccCashFlow implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 标注ID
	 */
	private Long cash_id;
	/**
	 * 凭证明细ID
	 */
	private Long vouch_detail_id;
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
	 * 流量项目ID
	 */
	private Long cash_item_id;
	/**
	 * 标注金额
	 */
	private double cash_money;
	/**
	 * 标注人
	 */
	private Long create_user;
	/**
	 * 标注时间
	 */
	private Date create_date;
	
	private Long subj_code;
	
	private String subj_name;
	
	private String cash_item_name;
	private String summary;
	/**
	 * 设置 标注ID
	 */
	public void setCash_id(Long value) {
		this.cash_id = value;
	}
	/**
	 * 获取 标注ID
	 */	
	public Long getCash_id() {
		return this.cash_id;
	}
	/**
	 * 设置 凭证明细ID
	 */
	public void setVouch_detail_id(Long value) {
		this.vouch_detail_id = value;
	}
	/**
	 * 获取 凭证明细ID
	 */	
	public Long getVouch_detail_id() {
		return this.vouch_detail_id;
	}
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
	 * 设置 流量项目ID
	 */
	public void setCash_item_id(Long value) {
		this.cash_item_id = value;
	}
	/**
	 * 获取 流量项目ID
	 */	
	public Long getCash_item_id() {
		return this.cash_item_id;
	}
	/**
	 * 设置 标注金额
	 */
	public void setCash_money(double value) {
		this.cash_money = value;
	}
	/**
	 * 获取 标注金额
	 */	
	public double getCash_money() {
		return this.cash_money;
	}
	/**
	 * 设置 标注人
	 */
	public void setCreate_user(Long value) {
		this.create_user = value;
	}
	/**
	 * 获取 标注人
	 */	
	public Long getCreate_user() {
		return this.create_user;
	}
	/**
	 * 设置 标注时间
	 */
	public void setCreate_date(Date value) {
		this.create_date = value;
	}
	/**
	 * 获取 标注时间
	 */	
	public Date getCreate_date() {
		return this.create_date;
	}
	public Long getSubj_code() {
		return subj_code;
	}
	public void setSubj_code(Long subj_code) {
		this.subj_code = subj_code;
	}
	public String getSubj_name() {
		return subj_name;
	}
	public void setSubj_name(String subj_name) {
		this.subj_name = subj_name;
	}
	public String getCash_item_name() {
		return cash_item_name;
	}
	public void setCash_item_name(String cash_item_name) {
		this.cash_item_name = cash_item_name;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	
}