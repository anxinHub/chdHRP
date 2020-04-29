/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.entity;

import java.io.Serializable;
import java.util.Date;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public class AccSubjLedger implements Serializable {

	
	private static final long serialVersionUID = 5454155825314635342L;
	/**
	 * 期间年度
	 * */
	private String acc_year;
	/**
	 * 期间月度
	 * */
	private String acc_month;
	/**
	 * 期间日
	 * */
	private String acc_day;
	
	private String fun_name;
	
	public String getFun_name() {
		return fun_name;
	}
	public void setFun_name(String fun_name) {
		this.fun_name = fun_name;
	}
	public String getEco_name() {
		return eco_name;
	}
	public void setEco_name(String eco_name) {
		this.eco_name = eco_name;
	}
	private String eco_name;
	
	private Long subj_id;
	
	private String subj_name;
	
	private String subj_code;
	/**
	 * 摘要
	 * */
	private String summary;
	/**
	 * 借方金额
	 * */
	private double debit;
	/**
	 * 贷方金额
	 * */
	private double credit;
	/**
	 * 方向
	 * */
	private int cash_dire;
	
	private int subj_level;
	
	public int getSubj_level() {
		return subj_level;
	}
	public void setSubj_level(int subj_level) {
		this.subj_level = subj_level;
	}
	/**
	 * 方向
	 * */
	private String subj_dire;
	
	
	/**
	 * 余额
	 * */
	private double end_os;
	
	
	/**
	 * 凭证id
	 * */
	private Long vouch_id;
	
	private int is_check;
	
	private String vouch_date;
	
	public Long getVouch_id() {
		return vouch_id;
	}
	public void setVouch_id(Long vouch_id) {
		this.vouch_id = vouch_id;
	}
	/**
	 * 凭证号
	 * */
	private String vouch_no;
	
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
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public double getDebit() {
		return debit;
	}
	public void setDebit(double debit) {
		this.debit = debit;
	}
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	public String getSubj_dire() {
		return subj_dire;
	}
	public void setSubj_dire(String subj_dire) {
		this.subj_dire = subj_dire;
	}
	
	
	public int getCash_dire() {
		return cash_dire;
	}
	public void setCash_dire(int cash_dire) {
		this.cash_dire = cash_dire;
	}
	
	public double getEnd_os() {
		return end_os;
	}
	public void setEnd_os(double end_os) {
		this.end_os = end_os;
	}
	public String getAcc_day() {
		return acc_day;
	}
	public void setAcc_day(String acc_day) {
		this.acc_day = acc_day;
	}
	public String getVouch_no() {
		return vouch_no;
	}
	public void setVouch_no(String vouch_no) {
		this.vouch_no = vouch_no;
	}
	public String getSubj_name() {
		return subj_name;
	}
	public void setSubj_name(String subj_name) {
		this.subj_name = subj_name;
	}
	public String getSubj_code() {
		return subj_code;
	}
	public void setSubj_code(String subj_code) {
		this.subj_code = subj_code;
	}
	/**
	 * 年初余额借方
	 */
	private double nc_debit;
	/**
	 * 年初余额贷方
	 */
	private double nc_credit;
	/**
	 * 期初余额借方
	 */
	private double qc_debit;
	/**
	 * 期初余额贷方
	 */
	private double qc_credit;
	/**
	 * 本期发生借方
	 */
	private double bq_debit;
	/**
	 * 本期发生贷方
	 */
	private double bq_credit;
	/**
	 * 累计发生借方
	 */
	private double lj_debit;
	/**
	 * 累计发生贷方
	 */
	private double lj_credit;
	/**
	 * 期末余额借方
	 */
	private double end_debit;
	/**
	 * 期末余额贷方
	 */
	private double end_credit;

	public double getNc_debit() {
		return nc_debit;
	}
	public void setNc_debit(double nc_debit) {
		this.nc_debit = nc_debit;
	}
	public double getNc_credit() {
		return nc_credit;
	}
	public void setNc_credit(double nq_credit) {
		this.nc_credit = nq_credit;
	}
	public double getQc_debit() {
		return qc_debit;
	}
	public void setQc_debit(double qc_debit) {
		this.qc_debit = qc_debit;
	}
	public double getQc_credit() {
		return qc_credit;
	}
	public void setQc_credit(double qc_credit) {
		this.qc_credit = qc_credit;
	}
	public double getBq_debit() {
		return bq_debit;
	}
	public void setBq_debit(double bq_debit) {
		this.bq_debit = bq_debit;
	}
	public double getBq_credit() {
		return bq_credit;
	}
	public void setBq_credit(double bq_credit) {
		this.bq_credit = bq_credit;
	}
	public double getLj_debit() {
		return lj_debit;
	}
	public void setLj_debit(double lj_debit) {
		this.lj_debit = lj_debit;
	}
	public double getLj_credit() {
		return lj_credit;
	}
	public void setLj_credit(double lj_credit) {
		this.lj_credit = lj_credit;
	}
	public double getEnd_debit() {
		return end_debit;
	}
	public void setEnd_debit(double end_debit) {
		this.end_debit = end_debit;
	}
	public double getEnd_credit() {
		return end_credit;
	}
	public void setEnd_credit(double end_credit) {
		this.end_credit = end_credit;
	}
	public Long getSubj_id() {
		return subj_id;
	}
	public void setSubj_id(Long subj_id) {
		this.subj_id = subj_id;
	}
	public int getIs_check() {
		return is_check;
	}
	public void setIs_check(int is_check) {
		this.is_check = is_check;
	}
	public String getVouch_date() {
		return vouch_date;
	}
	public void setVouch_date(String vouch_date) {
		this.vouch_date = vouch_date;
	}
	
}