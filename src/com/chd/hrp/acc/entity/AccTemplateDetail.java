/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 凭证模板明细表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccTemplateDetail implements Serializable {

	
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
	 * 会计年度
	 */
	private String acc_year;
	/**
	 * 模板ID
	 */
	private Long template_id;
	/**
	 * 模板明细ID
	 */
	private double template_detail_id;
	/**
	 * 科目ID
	 */
	private String subj_id;
	/**
	 * 分录行号
	 */
	private String vouch_row;
	/**
	 * 摘要
	 */
	private String summary;
	/**
	 * 借方金额
	 */
	private String debit;
	/**
	 * 贷方金额
	 */
	private String credit;
	/**
	 * 借方金额外币
	 */
	private String debit_w;
	/**
	 * 贷方金额外币
	 */
	private String crebit_w;
	
	public double getGroup_id() {
		return group_id;
	}
	public void setGroup_id(double group_id) {
		this.group_id = group_id;
	}
	public double getHos_id() {
		return hos_id;
	}
	public void setHos_id(double hos_id) {
		this.hos_id = hos_id;
	}
	public String getCopy_code() {
		return copy_code;
	}
	public void setCopy_code(String copy_code) {
		this.copy_code = copy_code;
	}
	public String getAcc_year() {
		return acc_year;
	}
	public void setAcc_year(String acc_year) {
		this.acc_year = acc_year;
	}
	public Long getTemplate_id() {
		return template_id;
	}
	public void setTemplate_id(Long template_id) {
		this.template_id = template_id;
	}
	public double getTemplate_detail_id() {
		return template_detail_id;
	}
	public void setTemplate_detail_id(double template_detail_id) {
		this.template_detail_id = template_detail_id;
	}
	public String getSubj_id() {
		return subj_id;
	}
	public void setSubj_id(String subj_id) {
		this.subj_id = subj_id;
	}
	public String getVouch_row() {
		return vouch_row;
	}
	public void setVouch_row(String vouch_row) {
		this.vouch_row = vouch_row;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDebit() {
		return debit;
	}
	public void setDebit(String debit) {
		this.debit = debit;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getDebit_w() {
		return debit_w;
	}
	public void setDebit_w(String debit_w) {
		this.debit_w = debit_w;
	}
	public String getCrebit_w() {
		return crebit_w;
	}
	public void setCrebit_w(String crebit_w) {
		this.crebit_w = crebit_w;
	}

	
}