/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.entity;

import java.io.Serializable;

/**
* @Title. @Description.
* 币种期间汇率<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public class AccCurRate implements Serializable {
	
	private static final long serialVersionUID = 5454155825314635342L;
	
	/**
	 * 银行对账单ID
	 */
	private Long bank_id;
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
	 * 会计期间
	 */
	private String acc_month;
	/**
	 * 币种编码
	 */
	private String cur_code;
	/**
	 * 币种名称
	 */
	private String cur_name;
	/**
	 * 期初汇率
	 */
	private double start_rate;
	/**
	 * 平均汇率
	 */
	private double avg_rate;
	/**
	 * 期末汇率
	 */
	private double end_rate;
	
	public Long getBank_id() {
		return bank_id;
	}
	public void setBank_id(Long bank_id) {
		this.bank_id = bank_id;
	}
	public Long getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Long group_id) {
		this.group_id = group_id;
	}
	public Long getHos_id() {
		return hos_id;
	}
	public void setHos_id(Long hos_id) {
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
	public String getAcc_month() {
		return acc_month;
	}
	public void setAcc_month(String acc_month) {
		this.acc_month = acc_month;
	}
	public String getCur_code() {
		return cur_code;
	}
	public void setCur_code(String cur_code) {
		this.cur_code = cur_code;
	}
	public String getCur_name() {
		return cur_name;
	}
	public void setCur_name(String cur_name) {
		this.cur_name = cur_name;
	}
	public double getStart_rate() {
		return start_rate;
	}
	public void setStart_rate(double start_rate) {
		this.start_rate = start_rate;
	}
	public double getAvg_rate() {
		return avg_rate;
	}
	public void setAvg_rate(double avg_rate) {
		this.avg_rate = avg_rate;
	}
	public double getEnd_rate() {
		return end_rate;
	}
	public void setEnd_rate(double end_rate) {
		this.end_rate = end_rate;
	}
}
