/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.*;

/**
* @Title. @Description.
* 凭证明细表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccVouchDetail implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 凭证明细ID
	 */
	private Long vouch_detail_id;
	/**
	 * 凭证ID
	 */
	private Long vouch_id;
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
	 * 会计年度
	 */
	private String acc_year;
	/**
	 * 科目编码
	 */
	private String subj_id;
	/**
	 * 分录行号
	 */
	private Long vouch_row;
	/**
	 * 摘要
	 */
	private String summary;
	/**
	 * 借方金额
	 */
	private double debit;
	/**
	 * 贷方金额
	 */
	private double credit;
	/**
	 * 借方金额外币
	 */
	private double debit_w;
	/**
	 * 贷方金额外币
	 */
	private double credit_w;

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
	 * 设置 凭证ID
	 */
	public void setVouch_id(Long value) {
		this.vouch_id = value;
	}
	/**
	 * 获取 凭证ID
	 */	
	public Long getVouch_id() {
		return this.vouch_id;
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
	 * 设置 科目编码
	 */
	public void setSubj_id(String value) {
		this.subj_id = value;
	}
	/**
	 * 获取 科目编码
	 */	
	public String getSubj_id() {
		return this.subj_id;
	}
	/**
	 * 设置 分录行号
	 */
	public void setVouch_row(Long value) {
		this.vouch_row = value;
	}
	/**
	 * 获取 分录行号
	 */	
	public Long getVouch_row() {
		return this.vouch_row;
	}
	/**
	 * 设置 摘要
	 */
	public void setSummary(String value) {
		this.summary = value;
	}
	/**
	 * 获取 摘要
	 */	
	public String getSummary() {
		return this.summary;
	}
	/**
	 * 设置 借方金额
	 */
	public void setDebit(double value) {
		this.debit = value;
	}
	/**
	 * 获取 借方金额
	 */	
	public double getDebit() {
		return this.debit;
	}
	/**
	 * 设置 贷方金额
	 */
	public void setCredit(double value) {
		this.credit = value;
	}
	/**
	 * 获取 贷方金额
	 */	
	public double getCredit() {
		return this.credit;
	}
	
	/**
	 * 设置 借方金额外币
	 */
	public void setDebit_w(double value) {
		this.debit_w = value;
	}
	/**
	 * 获取 借方金额外币
	 */	
	public double getDebit_w() {
		return this.debit_w;
	}
	/**
	 * 设置 贷方金额外币
	 */
	public void setCredit_w(double value) {
		this.credit_w = value;
	}
	/**
	 * 获取 贷方金额外币
	 */	
	public double getCredit_w() {
		return this.credit_w;
	}
}